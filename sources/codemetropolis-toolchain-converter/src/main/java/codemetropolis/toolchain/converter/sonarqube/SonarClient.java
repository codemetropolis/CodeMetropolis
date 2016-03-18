package codemetropolis.toolchain.converter.sonarqube;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import codemetropolis.toolchain.commons.util.Resources;
import codemetropolis.toolchain.converter.sonarqube.SonarMetric.MetricType;
import codemetropolis.toolchain.converter.sonarqube.SonarResource.Scope;

public class SonarClient {

	private static final String RESOURCES = "resources?";
	private static final String SCOPE = "scopes=";
	private static final String RESOURCE = "resource=";
	private static final String METRICS = "metrics=";
	private static final String DEPTH = "depth=";
	private static final String METRIC_SEARCH = "metrics/search?f=name";
	
	private String sonarUrl;
	private String username;
	private String password;
	private String metricNames;
	private Map<Integer, SonarResource> resources = new ConcurrentHashMap<Integer, SonarResource>();
	private Map<String, String> metricNamesAndTypes = new HashMap<String, String>();
	
	public SonarClient(String sonarUrl, String username, String password) {
		this.sonarUrl = sonarUrl;
		this.username = username;
		this.password = password;
	}
	
	public SonarClient(String sonarUrl) {
		this(sonarUrl, null, null);
	}
	
	public void init() throws SonarConnectException {
		metricNames = getMetricsParameterNames();
	}
	
	private void getResources(SonarResource resource) throws SonarConnectException {
		Scope scope = null;
		switch(resource.getScope()) {
			case PRJ:
				scope = Scope.DIR;
				break;
			case DIR:
				scope = Scope.FIL;
				break;
			case FIL:
				return;
		}
		
		String requestUrl = createRequestUrl(RESOURCES, RESOURCE, String.valueOf(resource.getId()), "&", DEPTH, String.valueOf(-1), "&", SCOPE, scope.toString(), "&", METRICS, metricNames);
		String responseStr = sendRequest(requestUrl);
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(responseStr);
		if(jsonElement.isJsonArray()){
			Iterator<JsonElement> iterator = ((JsonArray)jsonElement).iterator();
			while(iterator.hasNext()){
				JsonElement arrayElement = iterator.next();
				if(arrayElement.isJsonObject()){
					SonarResource childResource = createResource((JsonObject)arrayElement);
					resources.put(childResource.getId(), childResource);
					resources.get(resource.getId()).addChild(childResource.getId());
					getResources(childResource);
				}			
			}
		}
	}
	
	public String[] getProjectKeys() throws SonarConnectException {
		List<String> result = new ArrayList<>();
		String requestUrl = createRequestUrl(RESOURCES);
		String responseStr = sendRequest(requestUrl);
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(responseStr);
		if(jsonElement.isJsonArray()){
			Iterator<JsonElement> iterator = ((JsonArray)jsonElement).iterator();
			while(iterator.hasNext()){
				JsonObject projectJsonObject = iterator.next().getAsJsonObject();
				result.add(projectJsonObject.get("key").getAsString());	
			}
		}
		return result.toArray(new String[result.size()]);
	}
	
	public Map<Integer, SonarResource> getProject(String projectKey) throws SonarConnectException {	
		String requestUrl = createRequestUrl(RESOURCES, RESOURCE, projectKey, "&",SCOPE,Scope.PRJ.toString(), "&", METRICS, metricNames);
		String responseStr = sendRequest(requestUrl);
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(responseStr);
		if(jsonElement.isJsonArray()){
			Iterator<JsonElement> iterator = ((JsonArray)jsonElement).iterator();
			while(iterator.hasNext()){
				JsonElement arrayElement = iterator.next();
				if(arrayElement.isJsonObject()){
					SonarResource childResource = createResource((JsonObject)arrayElement);
					resources.put(childResource.getId(), childResource);
				}			
			}
		}
		Iterator<Integer> iterator = resources.keySet().iterator();
		while(iterator.hasNext()){
			SonarResource innerResources = resources.get(iterator.next());
			getResources(innerResources);
		}
		return resources;
	}
	
	private String sendRequest(String urlWithParams) throws SonarConnectException {
		try {
			String authStr = String.format("%s:%s", username, password);
			String encodedAuthStr = Base64.getEncoder().encodeToString(authStr.getBytes());
			URL url = new URL(urlWithParams);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Authorization", "Basic " + encodedAuthStr);
			connection.connect();
			if(connection.getResponseCode() == HttpURLConnection.HTTP_UNAUTHORIZED) {
				throw new SonarConnectException(Resources.get("sonar_unauthorized_error"));
			} else if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("HTTP request returned an error. HTTP error code : " + connection.getResponseCode());
			} 
			BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
			String line = br.readLine();
			connection.disconnect();	
			return line;
		} catch(IOException e) {
			throw new SonarConnectException(Resources.get("sonar_connect_error"));
		}
	}
	
	private String getMetricsParameterNames() throws SonarConnectException{
		StringBuilder metricNames = new StringBuilder();
		String requestUrl = createRequestUrl(METRIC_SEARCH);
		String responeStr = sendRequest(requestUrl);
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(responeStr);
		JsonArray domainJsonArray = jsonElement.getAsJsonObject().getAsJsonArray("metrics");
		Iterator<JsonElement> iterator = domainJsonArray.iterator();
		
		while(iterator.hasNext()){
			JsonElement element = iterator.next();
			if(element.isJsonObject()){
				JsonObject jsonArray = (JsonObject) element;
				metricNames.append(jsonArray.get("key").getAsString());
				metricNames.append(",");
				metricNamesAndTypes.put(jsonArray.get("key").getAsString(), jsonArray.get("type").getAsString());
			}
		}
		
		return metricNames.toString();
	}
	
	private String createRequestUrl(String... params){
		StringBuilder sb = new StringBuilder(sonarUrl);
		for(String p : params){
			sb.append(p);
		}
		return sb.toString();
	}
	
	private SonarResource createResource(JsonObject jsonObject){

		SonarResource resource = new SonarResource();
		String name = jsonObject.get("name").getAsString();
		String scope = jsonObject.get("scope").getAsString();
		String key = jsonObject.get("key").getAsString();
		int id = jsonObject.get("id").getAsInt();
		
		resource.setName(name);
		resource.setScope(Scope.valueOf(scope));
		resource.setKey(key);
		resource.setId(id);

		if(jsonObject.get("msr").isJsonArray()){
			JsonArray array = (JsonArray) jsonObject.get("msr");
			resource.addMetrics(getMetricNameAndValues(array));
		}
		
		return resource;
	}

	private List<SonarMetric> getMetricNameAndValues(JsonArray jsonArray){
		List<SonarMetric> metricList = new ArrayList<>();
		Iterator<JsonElement> iterator = jsonArray.iterator();
		while(iterator.hasNext()){
			JsonElement element = iterator.next();
			if(element.isJsonObject()){
				JsonObject jsonObject = (JsonObject) element;

				if(!jsonObject.has("key") || (!jsonObject.has("val") && !jsonObject.has("data"))){
					continue;			
				}
				String name = jsonObject.get("key").getAsString();
				String value = "";
				if(jsonObject.has("val")){
					value = jsonObject.get("val").getAsString();
				} else {
					value = jsonObject.get("data").getAsString();
				}
				String type = metricNamesAndTypes.get(name);
				SonarMetric metric = new SonarMetric(name, value, getMetricType(type));
				metricList.add(metric);
			}
		}
		return metricList;
	}
	
	private MetricType getMetricType(String type){	
		try {
			return MetricType.valueOf(type);
		} catch(Exception e) {
			return MetricType.DATA;
		}	
	}
	
}

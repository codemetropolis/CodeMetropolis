package codemetropolis.toolchain.converter.sonarqube;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import codemetropolis.toolchain.converter.sonarqube.SonarMetric.MetricType;
import codemetropolis.toolchain.converter.sonarqube.SonarResource.Scope;

public class SonarClient {

	private String sonarURL = "";
	
	private static final String SCOPE = "scopes=";
	private static final String RESOURCE = "resource=";
	private static final String METRICS = "metrics=";
	private static final String DEPTH = "depth=";
	private static final String RESOURCES= "resources?";
	
	private Map<String, String> metricNamesAndTypes = new HashMap<>();
	private String metricNames = "";
	
	private Map<Integer, SonarResource> resources = new ConcurrentHashMap<>();
	
	public SonarClient(String sonarUrl){
		this.sonarURL = sonarUrl;
	}
	
	private void getResources(SonarResource resource) throws IOException {
		Scope scope = Scope.DIR;
		if(Scope.PRJ.equals(resource.getScope()))
			scope = Scope.DIR;
		if(Scope.DIR.equals(resource.getScope()))
			scope = Scope.FIL;
		if(Scope.FIL.equals(resource.getScope()))
			return;
		String urlWithParams = createURL(RESOURCES, RESOURCE, String.valueOf(resource.getId()),"&", DEPTH, String.valueOf(-1),"&", SCOPE, scope.toString(),"&", METRICS, metricNames);
		String line = getDataFromUrl(urlWithParams);
		JsonParser jsonParser = new JsonParser();
		if(jsonParser.parse(line).isJsonArray()){
			JsonArray jsonArray = (JsonArray) jsonParser.parse(line);
			Iterator<JsonElement> iterator = jsonArray.iterator();
			while(iterator.hasNext()){
				JsonElement element = iterator.next();
				if(element.isJsonObject()){
					SonarResource res = createResource((JsonObject)element);
					resources.put(res.getId(), res);
					resources.get(resource.getId()).getChildrenResources().add(res.getId());
					getResources(res);
				}			
			}
		}
	}
	
	public String[] getProjectKeys() throws IOException {
		List<String> result = new ArrayList<>();
		String requestURL = createURL(RESOURCES);
		String responseData = getDataFromUrl(requestURL);
		JsonParser jsonParser = new JsonParser();
		if(jsonParser.parse(responseData).isJsonArray()){
			JsonArray jsonArray = (JsonArray) jsonParser.parse(responseData);
			Iterator<JsonElement> iterator = jsonArray.iterator();
			while(iterator.hasNext()){
				JsonObject projectJsonObject = iterator.next().getAsJsonObject();
				result.add(projectJsonObject.get("key").getAsString());	
			}
		}
		return result.toArray(new String[result.size()]);
	}
	
	public Map<Integer, SonarResource> getProject(String projectKey) throws MalformedURLException, IOException {	
		metricNames = getMetricsParameterNames();
		String urlWithParams = createURL(RESOURCES, RESOURCE, projectKey, "&",SCOPE,Scope.PRJ.toString(), "&", METRICS, metricNames);
		String line = getDataFromUrl(urlWithParams);
		JsonParser jsonParser = new JsonParser();
		if(jsonParser.parse(line).isJsonArray()){
			JsonArray jsonArray = (JsonArray) jsonParser.parse(line);
			Iterator<JsonElement> iterator = jsonArray.iterator();
			while(iterator.hasNext()){
				JsonElement element = iterator.next();
				if(element.isJsonObject()){
					SonarResource res = createResource((JsonObject)element);
					resources.put(res.getId(), res);
				}			
			}
		}
		Iterator<Integer> iterator = resources.keySet().iterator();
		while(iterator.hasNext()){
			SonarResource res = resources.get(iterator.next());
			getResources(res);
		}
		return resources;
	}
	
	private String getDataFromUrl(String urlWithParams) throws IOException{
		URL url = new URL(urlWithParams);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new RuntimeException("Failed : HTTP error code : "
				+ conn.getResponseCode());
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));
		String line = br.readLine();
		conn.disconnect();	
		return line;
	}
	
	private String getMetricsParameterNames() throws MalformedURLException, IOException{
		StringBuilder metricNames = new StringBuilder();
		String urlWithParams = createURL("metrics/search?f=name");
		String line = getDataFromUrl(urlWithParams);
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(line);

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
	
	private String createURL(String... params){

		StringBuilder sb = new StringBuilder(sonarURL);
		for(String p : params){
			sb.append(p);
		}
		return sb.toString();
	}
	
	private SonarResource createResource(JsonObject jsonObject){

		SonarResource resource = new SonarResource();
		String name = jsonObject.get("name").getAsString();
		String scope = jsonObject.get("scope").getAsString();
		int id = jsonObject.get("id").getAsInt();
		resource.setName(name);
		
		if(scope.equals(Scope.PRJ.toString())) resource.setScope(Scope.PRJ);
		else if(scope.equals(Scope.DIR.toString())) resource.setScope(Scope.DIR);
		else if(scope.equals(Scope.FIL.toString())) resource.setScope(Scope.FIL);
		
		resource.setId(id);

		if(jsonObject.get("msr").isJsonArray()){
			JsonArray array = (JsonArray) jsonObject.get("msr");
			resource.getMetricNamesAndValues().addAll(getMetricNameAndValues(array));;
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
		
		if(MetricType.INT.toString().equals(type)){
			return MetricType.INT;
		} else if(MetricType.FLOAT.toString().equals(type)){
			return MetricType.FLOAT;
		} else if(MetricType.PERCENT.toString().equals(type)) {
			return MetricType.PERCENT;
		} else if(MetricType.MILLISEC.toString().equals(type)) {
			return MetricType.MILLISEC;
		} else if (MetricType.BOOL.toString().equals(type)) {
			return MetricType.BOOL;
		} else if (MetricType.DATA.toString().equals(type)) {
			return MetricType.DATA;
		} else if (MetricType.DISTRIB.toString().equals(type)) {
			return MetricType.DISTRIB;
		} else if (MetricType.LEVEL.toString().equals(type)) {
			return MetricType.LEVEL;
		} else if (MetricType.RATING.toString().equals(type)) {
			return MetricType.RATING;
		} else if (MetricType.STRING.toString().equals(type)) {
			return MetricType.STRING;
		} else if (MetricType.WORK_DUR.toString().equals(type)){
			return MetricType.WORK_DUR;
		} else {
			return MetricType.DATA;
		}
		
	}
	
}

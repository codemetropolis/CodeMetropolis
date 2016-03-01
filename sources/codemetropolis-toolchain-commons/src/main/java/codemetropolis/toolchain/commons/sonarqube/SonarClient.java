package codemetropolis.toolchain.commons.sonarqube;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import codemetropolis.toolchain.commons.sonarqube.SonarResource.Scope;

public class SonarClient {

	private String sonarURL = "";
	
	private static final String SCOPE = "scopes=";
	private static final String RESOURCE = "resource=";
	private static final String METRICS = "metrics=";
	private static final String DEPTH = "depth=";
	private static final String RESOURCES= "resources?";
	
	private String metricNames;
	
	private Map<Integer, SonarResource> resources = new ConcurrentHashMap<>();
	
	public SonarClient(String sonarUrl){
		this.sonarURL = sonarUrl;
	}
	
	private void getResources(SonarResource resource) throws IOException{
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
	
	public Map<Integer, SonarResource> getProject(String projectId) throws MalformedURLException, IOException{
		metricNames = getMetricsParameterNames();
		String urlWithParams = createURL(RESOURCES, RESOURCE, projectId, "&",SCOPE,Scope.PRJ.toString(), "&", METRICS, metricNames);
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
	
	public Map<Integer, SonarResource> getAllProjects() throws IOException{
		metricNames = getMetricsParameterNames();
		String urlWithParams = createURL(RESOURCES,SCOPE,Scope.PRJ.toString(), "&", METRICS, metricNames);
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
			resource.getMetricNamesandValues().putAll(getMetricNameAndValues(array));;
		}
		
		return resource;
	}

	private Map<String, String> getMetricNameAndValues(JsonArray jsonArray){
		Map<String, String> map = new HashMap<>();
		
		Iterator<JsonElement> iterator = jsonArray.iterator();
		while(iterator.hasNext()){
			JsonElement element = iterator.next();
			if(element.isJsonObject()){
				JsonObject jsonObject = (JsonObject) element;
				if(!(jsonObject.get("key") != null) && !(jsonObject.get("val") != null)){
					map.put(jsonObject.get("key").getAsString(), jsonObject.get("val").getAsString());
				}
			}
		}
		
		
		return map;
	}
	
}

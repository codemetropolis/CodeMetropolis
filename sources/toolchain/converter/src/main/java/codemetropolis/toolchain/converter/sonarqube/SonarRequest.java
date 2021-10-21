package codemetropolis.toolchain.converter.sonarqube;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import codemetropolis.toolchain.commons.util.Resources;

public class SonarRequest {
	
	private String url;
	private String params;
	private String username;
	private String password;
	
	public SonarRequest(String url, String username, String password) {
		this.url = url;
		this.params = "";
		this.username = username;
		this.password = password;
	}
	
	public SonarRequest(String url) {
		this(url, null, null);
	}
	
	public String sendGet() throws SonarConnectException {
		return send("GET");
	}
	
	public String sendPost() throws SonarConnectException {
		return send("POST");
	}
	
	private String send(String method) throws SonarConnectException {
		try {
			URL url = new URL(method.equalsIgnoreCase("GET") ? getUrlWithParams() : this.url);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method);
			
			if(isLoginInfoSet()) {
				connection.setRequestProperty("Authorization", "Basic " + createAuthenticationString());
			}
			
			if(method.equalsIgnoreCase("POST")) {
				connection.setDoOutput(true);
				DataOutputStream os = new DataOutputStream(connection.getOutputStream());
				os.writeBytes(params);
				os.flush();
				os.close();
			}
			
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
	
	private String createAuthenticationString() {
		String authStr = String.format("%s:%s", username, password);
		return Base64.getEncoder().encodeToString(authStr.getBytes());
	}
	
	private boolean isLoginInfoSet() {
		return username != null && password != null;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParams() {
		return params;
	}

	public void setParameter(String key, String value) {
		params = String.format("%s%s%s=%s", params, params.equals("") ? "" : "&", key, value);
	}
	
	public String getUrlWithParams() {
		return String.format("%s?%s", url, params);
	}
	
	public void setLoginInfo(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
}

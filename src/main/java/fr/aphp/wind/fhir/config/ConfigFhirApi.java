package fr.aphp.wind.fhir.config;

public class ConfigFhirApi {

	private String host;
	private String proxyHost;
	private Integer proxyPort;
	private boolean hasProxy = false;

	public ConfigFhirApi(String host) {
		this.host = host;
	}

	public ConfigFhirApi(String host, String proxyHost, Integer proxyPort) {
		this.host = host;
		this.proxyHost = proxyHost;
		this.proxyPort = proxyPort;
		this.hasProxy = true;
	}

	public String getHost() {
		return host;
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public Integer getProxyPort() {
		return proxyPort;
	}

	public boolean hasProxy() {
		return this.hasProxy;
	}
}

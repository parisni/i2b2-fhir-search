package fr.aphp.wind.i2b2fhir.resource;

public class FhirProfiledResourceBuilder {
	private String resourceName;
	private String profileResourceName;
	private String fhirApiHost;
	private Integer fhirApiPagination = 500;
	private String fhirProxyHost;
	private int fhirProxyPort;
	private String i2b2SetType;
	private String fhirSearchQuery;
	private boolean hasProxy = false;
	private Integer fhirDstuVersion = 3;
	
	public FhirProfiledResourceBuilder withResourceName(String str){
		this.resourceName = str;  
		return this;
	}
	
	public FhirProfiledResourceBuilder withProfileResourceName(String str){
		this.profileResourceName = str;  
		return this;
	}
	
	public FhirProfiledResourceBuilder withFhirApiHost(String str){
		this.fhirApiHost = str;  
		return this;
	}
	
	public FhirProfiledResourceBuilder withFhirApiPagination(Integer i){
		this.fhirApiPagination = i;  
		return this;
	}
	
	public FhirProfiledResourceBuilder withFhirProxyHost(String str){
		this.fhirProxyHost = str;  
		this.hasProxy = true;
		return this;
	}
	
	public FhirProfiledResourceBuilder withFhirProxyPort(Integer i){
		this.fhirProxyPort = i;  
		this.hasProxy = true;
		return this;
	}
	
	public FhirProfiledResourceBuilder withI2b2SetType(String str){
		this.i2b2SetType = str;  
		return this;
	}
	
	public FhirProfiledResourceBuilder withFhirSearchQuery(String str){
		this.fhirSearchQuery = str;  
		return this;
	}
	
	public FhirProfiledResourceBuilder withFhirDstuVersion(Integer i){
		this.fhirDstuVersion = i;  
		return this;
	}
	
	public FhirProfiledResource build(){
		FhirProfiledResource fpr = new FhirProfiledResource();
		fpr.setResourceName(this.resourceName);
		if(this.profileResourceName==null){//optionnal profiled
			this.profileResourceName = this.resourceName;
		}
		fpr.setProfileResourceName(this.profileResourceName);
		fpr.setFhirApiHost(this.fhirApiHost);
		fpr.setFhirApiPagination(this.fhirApiPagination);
		fpr.setFhirProxyHost(this.fhirProxyHost);
		fpr.setFhirProxyPort(this.fhirProxyPort);
		fpr.setHasProxy(this.hasProxy);
		fpr.setI2b2SetType(this.i2b2SetType);
		fpr.setFhirSearchQuery(this.fhirSearchQuery);
		fpr.setFhirDstuVersion(this.fhirDstuVersion);
		
		return fpr;
		
	}

}

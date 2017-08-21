package fr.aphp.wind.fhir.config;

public class ConfigFhirQuery {
	private Integer paginationCount;
	private String query;
	private String resourceName;
	
	public ConfigFhirQuery(String resourceName, Integer paginationCount, String query){
		this.resourceName = resourceName;
		this.paginationCount = paginationCount;
		this.query = query;
		
	}

	public String getResourceName() {
		return resourceName;
	}

	public Integer getPaginationCount() {
		return paginationCount;
	}

	public String getQuery() {
		return query;
	}


}

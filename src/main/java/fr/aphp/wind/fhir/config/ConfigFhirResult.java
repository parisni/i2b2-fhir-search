package fr.aphp.wind.fhir.config;

public class ConfigFhirResult {
	private String listType;
	private String idFieldPatientJsonPath = "$.resource.patient";
	private String idFieldEncounterJsonPath = "$.resource.encounter";
	private String idFieldInstanceJsonPath = "$.resource.id";
	
	public ConfigFhirResult(String listType){
		this.listType = listType;
	}
	public ConfigFhirResult(String listType, String idFieldPatientJsonPath){
		this.listType = listType;
		this.idFieldPatientJsonPath = idFieldPatientJsonPath;
	}
	
	public ConfigFhirResult(String listType, String idFieldPatientJsonPath, String idFieldEncounterJsonPath){
		this.listType = listType;
		this.idFieldPatientJsonPath = idFieldPatientJsonPath;
		this.idFieldEncounterJsonPath = idFieldEncounterJsonPath;
	}
	
	public ConfigFhirResult(String listType, String idFieldPatientJsonPath, String idFieldEncounterJsonPath, String idFieldInstanceJsonPath){
		this.listType = listType;
		this.idFieldPatientJsonPath = idFieldPatientJsonPath;
		this.idFieldEncounterJsonPath = idFieldEncounterJsonPath;
		this.idFieldInstanceJsonPath = idFieldInstanceJsonPath;
	}

	public String getListType() {
		return listType;
	}

	public void setListType(String listType) {
		this.listType = listType;
	}

	public String getIdFieldPatientJsonPath() {
		return idFieldPatientJsonPath;
	}

	public void setIdFieldPatientJsonPath(String idFieldPatientJsonPath) {
		this.idFieldPatientJsonPath = idFieldPatientJsonPath;
	}

	public String getIdFieldEncounterJsonPath() {
		return idFieldEncounterJsonPath;
	}

	public void setIdFieldEncounterJsonPath(String idFieldEncounterJsonPath) {
		this.idFieldEncounterJsonPath = idFieldEncounterJsonPath;
	}

	public String getIdFieldInstanceJsonPath() {
		return idFieldInstanceJsonPath;
	}

	public void setIdFieldInstanceJsonPath(String idFieldInstanceJsonPath) {
		this.idFieldInstanceJsonPath = idFieldInstanceJsonPath;
	}
}

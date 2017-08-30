package fr.aphp.wind.i2b2fhir.config;

public class ConfigFhirResource {
	private String patientUriPath;
	private String encounterUriPath;
	private String instanceUriPath;
	private String datePath;
	private String patientUriField;
	private String encounterUriField;
	private String instanceUriField;
	private String dateField;
	
	public ConfigFhirResource(){}
	
	public String getPatientUriPath() {
		return this.patientUriPath;
	}

	public String getEncounterUriPath() {
		return this.encounterUriPath;
	};

	public String getInstanceUriPath() {
		return this.instanceUriPath;
	}
	
	public String getDatePath() {
		return this.datePath;
	}

	public void setPatientUriPath(String patientUriPath) {
		this.patientUriPath = patientUriPath;
	}

	public void setEncounterUriPath(String encounterUriPath) {
		this.encounterUriPath = encounterUriPath;
	}

	public void setInstanceUriPath(String instanceUriPath) {
		this.instanceUriPath = instanceUriPath;
	}

	public void setDatePath(String datePath) {
		this.datePath = datePath;
	}

	public String getPatientUriField() {
		return patientUriField;
	}

	public void setPatientUriField(String patientUriField) {
		this.patientUriField = patientUriField;
	}

	public String getEncounterUriField() {
		return encounterUriField;
	}

	public void setEncounterUriField(String encounterUriField) {
		this.encounterUriField = encounterUriField;
	}

	public String getInstanceUriField() {
		return instanceUriField;
	}

	public void setInstanceUriField(String instanceUriField) {
		this.instanceUriField = instanceUriField;
	}

	public String getDateField() {
		return dateField;
	}

	public void setDateField(String dateUriField) {
		this.dateField = dateUriField;
	}

}

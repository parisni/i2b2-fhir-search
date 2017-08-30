package fr.aphp.wind.i2b2fhir.i2b2set;

import java.util.Date;

public class InstanceSetImpl implements I2b2Set {
	private String patientUri;
	private String encounterUri;
	private String instanceUri;

	public String getPatientUri() {
		return patientUri;
	}

	public void setPatientUri(String patientUri) {
		this.patientUri = patientUri;
	}

	public String getEncounterUri() {
		return encounterUri;
	}

	public void setEncounterUri(String encounterUri) {
		this.encounterUri = encounterUri;
	}

	public String getInstanceUri() {
		return instanceUri;
	}

	public void setInstanceUri(String instanceUri) {
		this.instanceUri = instanceUri;
	}

	public String toCsv() {
		return String.format("%s;%s;%s", this.patientUri, this.encounterUri, this.instanceUri);
	}

	public String getDate() {
		// TODO Auto-generated method stub
		return null;
	}

}

package fr.aphp.wind.i2b2fhir.i2b2set;

import java.util.Date;

public class EncounterSetImpl implements I2b2Set {
	private String patientUri;
	private String encounterUri;

	public EncounterSetImpl() {
	}

	public EncounterSetImpl(String patientUri, String encounterUri) {
		this.patientUri = patientUri;
		this.encounterUri = encounterUri;
	}

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

	public String toCsv() {
		return String.format("%s;%s", this.patientUri, this.encounterUri);
	}

	public String getInstanceUri() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDate() {
		// TODO Auto-generated method stub
		return null;
	}
}

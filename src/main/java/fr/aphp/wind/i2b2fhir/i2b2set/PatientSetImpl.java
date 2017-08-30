package fr.aphp.wind.i2b2fhir.i2b2set;

import java.util.Date;

public class PatientSetImpl implements I2b2Set {
	private String patientUri;

	public PatientSetImpl() {
	}
	
	public PatientSetImpl(String patientUri) {
		this.patientUri = patientUri;
	}

	public String getPatientUri() {
		return patientUri;
	}

	public void setPatientUri(String patientUri) {
		this.patientUri = patientUri;
	}

	public String toCsv() {
		return String.format("%s", this.patientUri);
	}

	public String getEncounterUri() {
		// TODO Auto-generated method stub
		return null;
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

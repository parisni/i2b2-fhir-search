package fr.aphp.wind.fhir.ressource;

import java.util.ArrayList;

import fr.aphp.wind.fhir.config.ConfigFhirApi;
import fr.aphp.wind.fhir.config.ConfigFhirQuery;
import fr.aphp.wind.fhir.config.ConfigFhirResult;

public class PatientResourceFhir extends AbstractResourceFhir {

	public PatientResourceFhir(ConfigFhirApi configApi, ConfigFhirQuery configQuery, ConfigFhirResult configResult) {
		super(configApi, configQuery, configResult);
		this.hasEncounterId = false;
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<String> getPatientList() {
		// TODO Auto-generated method stub
		return this.patientList;
	}

	@Override
	public ArrayList<String> getEncounterList() throws Exception {
		// TODO Auto-generated method stub
		throw new Exception("A FHIR patient resource does not have any encounter field!");
	}

	@Override
	public ArrayList<String> getInstanceList() {
		// TODO Auto-generated method stub
		return null;
	}

}

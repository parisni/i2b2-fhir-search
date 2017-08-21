package fr.aphp.wind.fhir.ressource;

import java.util.ArrayList;

import fr.aphp.wind.fhir.config.ConfigFhirApi;
import fr.aphp.wind.fhir.config.ConfigFhirQuery;
import fr.aphp.wind.fhir.config.ConfigFhirResult;

public class EncounterResourceFhir extends AbstractResourceFhir {

	public EncounterResourceFhir(ConfigFhirApi configApi, ConfigFhirQuery configQuery, ConfigFhirResult configResult) {
		super(configApi, configQuery, configResult);
		this.hasEncounterId = true;
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<String> getPatientList() {
		// TODO Auto-generated method stub
		return this.patientList;
	}

	@Override
	public ArrayList<String> getEncounterList() {
		// TODO Auto-generated method stub
		return this.encounterList;
	}

	@Override
	public ArrayList<String> getInstanceList() {
		// TODO Auto-generated method stub
		return null;
	}

}

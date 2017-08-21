package fr.aphp.wind.fhir.ressource;

import java.util.ArrayList;

import org.apache.http.HttpHost;
import org.apache.log4j.Logger;

import com.jayway.jsonpath.JsonPath;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import fr.aphp.wind.fhir.config.*;
import net.minidev.json.JSONArray;

public abstract class AbstractResourceFhir {
	final static Logger logger = Logger.getLogger(AbstractResourceFhir.class);
	private ConfigFhirApi configApi;
	private ConfigFhirQuery configQuery;
	private ConfigFhirResult configResult;
	private Integer entriesNumber = 0;
	protected ArrayList<String> patientList = new ArrayList<String>();
	protected ArrayList<String> encounterList = new ArrayList<String>();
	protected ArrayList<String> instanceList = new ArrayList<String>();
	
	protected boolean hasEncounterId = true;
	
	abstract ArrayList<String> getPatientList()throws Exception ;
	abstract ArrayList<String> getEncounterList() throws Exception;
	abstract ArrayList<String> getInstanceList() throws Exception;
	
	public AbstractResourceFhir(ConfigFhirApi configApi, ConfigFhirQuery configQuery, ConfigFhirResult configResult){
		this.configApi = configApi;
		this.configQuery = configQuery;
		this.configResult = configResult;
	}
	
	public void collectResult() throws UnirestException{
		if(this.configApi.hasProxy()){
	        Unirest.setProxy(new HttpHost(this.configApi.getProxyHost(), this.configApi.getProxyPort()));
		}
		boolean hasResult = true;
		
		String query =  this.configApi.getHost() 
				+ this.configQuery.getResourceName() 
				+ "?" + this.configQuery.getQuery() 
				+ "&_count="+ this.configQuery.getPaginationCount()
				+"&_pretty=false";
		while(hasResult){
			logger.debug(String.format("FHIR query: %s", query));
			  String jsonResultString = Unirest
						.get(query)
						.asString()
						.getBody()
						.toString();
			  JSONArray entries = JsonPath.read(jsonResultString, "$.entry");
			  for(Object entry: entries){
				  entriesNumber++;
			
				 if(this.configResult.getListType().equals("patientSet")){
					 collectPatientList(entry);
				 }else if(this.configResult.getListType().equals("encounterSet")){
					 collectEncounterList(entry);
				 }else if (this.configResult.getListType().equals("instanceSet")){
					 collectInstanceList(entry);
				 }
			  }
				 query = getNextPagination(jsonResultString);
				 if(query == null){
					 hasResult = false;
				 }
		}
	}

	private void collectPatientList(Object entry) {
		try{
		String entryId = JsonPath.read(entry, this.configResult.getIdFieldPatientJsonPath());
		this.patientList.add(entryId.replaceAll("^.*/", ""));
	}catch(Exception e){
		logger.info(String.format("Does not have any Patient id: [%s]", entry));
	}
	}

	private void collectEncounterList(Object entry) {
		collectPatientList(entry);
		try{
		String encounterId = JsonPath.read(entry, this.configResult.getIdFieldEncounterJsonPath());
		this.encounterList.add(encounterId.replaceAll("^.*/", ""));
	}catch(Exception e){
		logger.info(String.format("Does not have any Encounter id: [%s]", entry));
	}
	}

	private void collectInstanceList(Object entry) {
		collectEncounterList(entry);
		try{
		String instanceId = JsonPath.read(entry, this.configResult.getIdFieldInstanceJsonPath());
		this.instanceList.add(instanceId.replaceAll("^.*/", ""));
		}catch(Exception e){
			logger.info(String.format("Does not have any Instance id: [%s]", entry));
		}
	}

	private String getNextPagination(String jsonResultString) {
		String result = null;
		JSONArray links = JsonPath.read(jsonResultString, "$.link");
		for (Object link : links) {
			String relType = JsonPath.read(link, "$.relation");
			if (relType.equals("next")) {
				result = JsonPath.read(link, "$.url");
				break;
			}
		}

		return result;
	}

	public Integer getEntriesNumber() {
		return this.entriesNumber;
	}

}

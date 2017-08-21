package fr.aphp.wind.fhir;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.apache.http.HttpHost;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.jayway.jsonpath.JsonPath;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.body.MultipartBody;

import fr.aphp.wind.fhir.config.ConfigFhirApi;
import fr.aphp.wind.fhir.config.ConfigFhirQuery;
import fr.aphp.wind.fhir.config.ConfigFhirResult;
import fr.aphp.wind.fhir.ressource.AbstractResourceFhir;
import fr.aphp.wind.fhir.ressource.EncounterResourceFhir;
import fr.aphp.wind.fhir.ressource.PatientResourceFhir;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import net.minidev.json.JSONArray;

/**
 * Unit test for simple App.
 */
public class PatientFhirTest 
    extends TestCase
{
	final static Logger logger = Logger.getLogger(TestCase.class);

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public PatientFhirTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( PatientFhirTest.class );
    }

  
    
	public void testPatient() throws UnirestException, FileNotFoundException, IOException {
		Properties p = new Properties();
		p.load(new FileReader(new File("config.properties")));
		ConfigFhirApi configFhirApi;
		if(p.getProperty("useProxy").equals("true")){
		 configFhirApi = new ConfigFhirApi("http://fhirtest.uhn.ca/baseDstu3/"
				, p.getProperty("proxyHost")
				, Integer.valueOf(p.getProperty("proxyPort")));
		}else{
			 configFhirApi = new ConfigFhirApi("http://fhirtest.uhn.ca/baseDstu3/");
		}
		
		ConfigFhirQuery configFhirQuery = new ConfigFhirQuery("Patient", 500, "birthdate=1974-12-24");
		ConfigFhirResult configFhirResult = new ConfigFhirResult("patientSet", "$.resource.id");
		
		PatientResourceFhir a = new PatientResourceFhir(configFhirApi, configFhirQuery, configFhirResult);
		a.collectResult();
		logger.debug(String.format("[PATIENT] %d PatientList: %s",a.getEntriesNumber(),  a.getPatientList()));
	}
	
}

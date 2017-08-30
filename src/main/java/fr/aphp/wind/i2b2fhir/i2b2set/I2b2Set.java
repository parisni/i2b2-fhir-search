package fr.aphp.wind.i2b2fhir.i2b2set;

import java.util.Date;

public interface I2b2Set  {
	public String toCsv();
	public String getEncounterUri();
	public String getPatientUri();
	public String getInstanceUri();
	public String getDate();

}

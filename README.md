# i2b2 fhir search

## Goal

This demo retrieves patientSet, encounterSet, instanceSet or dateSet from a remote FHIR API. Here the HAPI FHIR demo web service is used for testing. In the future, an hapi FHIR instance is supposed to be plugged on top of any other datawarehouse, live EMR and so on

## Feature

- can configure the fhir API HOST
- can configure the resource fields
- can configure the number of pagination
- loop over the pagination to get all results
- Fact represents Observation, Medication, ... and so on
- does not store the full result in memory; but each pagination instead
- the query needs to be pass as a string
- the way to retrieve the fields (patient/encounter/fact ids), is done by jsonPath, and is configurable by the client.
- warns when a field (eg: enconter in Observation) is not present in a resource
- compatible FHIR v3; easy to make it compatible with future version
- compatible with standard resources & profiled resources
- only retrieves fields for sets (not the full resource)

## Use

This uses the Builder design pattern :

```java
import fr.aphp.wind.i2b2fhir.*

FhirProfiledResource prf = new FhirProfiledResourceBuilder()
.withFhirApiHost("http://fhirtest.uhn.ca/baseDstu3/")//Required ; from i2b2 hive configuration?
.withFhirDstuVersion(3)//Optionnal, 3 is default
.withFhirProxyHost("my.proxy")//Optionnal
.withFhirProxyPort(myport)//Optionnal
.withResourceName("Observation")// Required
.withProfileResourceName("Observation")//Optionnal - if using a profiled resource
.withFhirSearchQuery("code=29463-7&value-quantity=21")//
.withI2b2SetType("dateSet")//one of [patientSet, encounterSet, instanceSet, dateSet]
.build();
prf.collectResult();

System.out.println(String.format("[Observation] %s", prf.toCsv()));//produce a csv to be loaded into i2b2 temporary table on the fly

I2b2SetList myResultSet = prf.getI2b2SetList(); //produce an array list
for( I2b2Set encSet : myResultSet){//iterate over the result set
	System.out.println(encSet.getPatientUri());
	System.out.println(encSet.getEncounterUri());

}
```


## Install

- mvn clean install
- import in eclipse as maven project
- run within eclipse

## test

- mvn test


## TODO

- Exceptions
- date formating

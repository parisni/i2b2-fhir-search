# i2b2 fhir search

## Goal

This demo retrieves patientSet, encounterSet or instanceSet from a remote FHIR API. Here the HAPI FHIR demo web service is used for testing. In the future, an hapi FHIR instance is supposed to be plugged on top of any other datawarehouse, live EMR and so on

## Feature

- can configure the fhir API HOST
- can configure the resource fields
- can configure the number of pagination
- loop over the pagination to get all results
- 3 kind of resources: Patient, Encounter, Fact
- Fact represents Observation, Medication, ... and so on
- does not store the full result in memory; but each pagination instead
- result can be a patientSet[Patient, Encounter, Fact], encounterSet[Encounter, Fact], or instanceSet[Fact only]
- the query needs to be pass as a string
- the way to retrieve the fields (patient/encounter/fact ids), is done by jsonPath, and is configurable by the client.
- warns when a field (eg: enconter in Observation) is not present in a resource

## Use

```java
//configurate the query (the resource type, the pagination, and the the query string)
ConfigFhirQuery configFhirQuery = new ConfigFhirQuery("Encounter", 500, "date=gt2014-05-28&date=lt2014-05-28");
//choose what kind of result you want (Here an encounterSet)
ConfigFhirResult configFhirResult = new ConfigFhirResult("encounterSet", "$.resource.subject.reference", "$.resource.id");
//get the result
EncounterResourceFhir a = new EncounterResourceFhir(configFhirApi, configFhirQuery, configFhirResult);
a.collectResult();
//returns the patient id list
System.out.println(a.getPatientList());
//returns the encounter id list
System.out.println(a.getEncounterList());
```

## install

- mvn clean install
- import in eclipse as maven project
- run within eclipse

## test

- configure the proxy in config.properties file (if no using just put useProxy to false)
- mvn test



package com.med.backend;

import org.springframework.stereotype.Service;
import org.hl7.fhir.r4.model.Patient;
import ca.uhn.fhir.context.FhirContext;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.UUID;
import org.hl7.fhir.r4.model.HumanName;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.hl7.fhir.r4.model.Annotation;
import org.hl7.fhir.r4.model.Enumerations;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.StringType;
@Service
public class FhirService {
    public String jsonFHIR(String patientJson) throws Exception {
        FhirContext ctx = FhirContext.forR4();
        Patient patient = new Patient();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(patientJson);
        String vorname = rootNode.get("firstName").asText();
        String nachname = rootNode.get("lastName").asText();

        if (rootNode.has("patientID") && !rootNode.get("patientID").asText().isEmpty()) {
            patient.setId(rootNode.get("patientID").asText());
        } else {
            patient.setId(UUID.randomUUID().toString());
        }
        if (rootNode.has("gender") && !rootNode.get("gender").asText().isEmpty()) {
        String genderStr = rootNode.get("gender").asText().toLowerCase();
        try {
            patient.setGender(Enumerations.AdministrativeGender.fromCode(genderStr));
        } catch (Exception e) {
            patient.setGender(Enumerations.AdministrativeGender.UNKNOWN);
        }
        }
        if(rootNode.has("birthDate") && !rootNode.get("birthDate").asText().isEmpty()) {
            String birthDateStr = rootNode.get("birthDate").asText();
            Date birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthDateStr);
            patient.setBirthDate(birthDate);
        }
        if(rootNode.has("managingClinic") && !rootNode.get("managingClinic").asText().isEmpty()) {
            String managingClinic = rootNode.get("managingClinic").asText();
            Reference orgRef = new Reference();
            orgRef.setDisplay(managingClinic);
            patient.setManagingOrganization(orgRef);
        }
        if(rootNode.has("Sonstiges") && !rootNode.get("Sonstiges").asText().isEmpty()) {
            String text = rootNode.get("Sonstiges").asText();
            patient.addExtension(
                "http://example.org/fhir/StructureDefinition/patient-sonstiges", 
        new StringType(text)
        );
        }
        if(rootNode.has("Puls") && !rootNode.get("Puls").isEmpty()) {
            String text = rootNode.get("Puls").asText();
            patient.addExtension (
                "http://example.org/fhir/StructureDefinition/patient-Puls",
                new StringType(text)
            );
            }
            if(rootNode.has("Notfallstufe") && !rootNode.get("Notfallstufe").isEmpty()) {
            String text = rootNode.get("Notfallstufe").asText();
            patient.addExtension (
                "http://example.org/fhir/StructureDefinition/patient-Notfallstufe",
                new StringType(text)
            );
            }
            if(rootNode.has("Blutdruck") && !rootNode.get("Blutdruck").isEmpty()) {
            String text = rootNode.get("Blutdruck").asText();
            patient.addExtension (
                "http://example.org/fhir/StructureDefinition/patient-Blutdruck",
                new StringType(text)
            );
            }
        HumanName name = patient.addName();
        name.addGiven(vorname);
        name.setFamily(nachname);
        return ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(patient);
    }
}

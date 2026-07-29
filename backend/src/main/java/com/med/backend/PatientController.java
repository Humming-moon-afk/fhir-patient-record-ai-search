package com.med.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "*")
public class PatientController {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private FhirService fhirService;
    private RestTemplate restTemplate = new RestTemplate();
    @PostMapping
    public String receivePatientData(@RequestBody String patientJson) throws Exception {
        System.out.println("=== PATIENTENDATEN IM BACKEND EMPFANGEN ===");
        System.out.println(patientJson);
        ObjectMapper mapper = new ObjectMapper();
        PatientEntity entity = mapper.readValue(patientJson, PatientEntity.class);
        System.out.println(entity.getBlutdruck() + ", " + entity.getPuls() + ", " + entity.getNotfallstufe());
        patientRepository.save(entity);
        String json = fhirService.jsonFHIR(patientJson);
        System.out.println(json);
        System.out.println("==========================================");
        return "Patientendaten erfolgreich empfangen und in Datenbank gespeichert!";
    }
   @GetMapping("/search")
    public List<PatientSearchResult> searchPatients(
            @RequestParam("q") String query,
            @RequestParam(value = "limit", defaultValue = "5") int limit) {

        String embedUrl = "http://localhost:11434/api/embeddings";
        Map<String, Object> embedRequest = new HashMap<>();
        embedRequest.put("model", "nomic-embed-text");
        embedRequest.put("prompt", query);

        Map embedResponse = restTemplate.postForObject(embedUrl, embedRequest, Map.class);

        if (embedResponse != null && embedResponse.containsKey("embedding")) {
            List<Double> doubleList = (List<Double>) embedResponse.get("embedding");
            String vectorString = doubleList.toString();

            return patientRepository.findSimilarPatients(vectorString, limit);
        }

        return List.of();
    }

}
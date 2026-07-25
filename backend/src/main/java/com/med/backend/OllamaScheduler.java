package com.med.backend;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class OllamaScheduler {
    @Autowired
    private PatientRepository patientRepository;
    private RestTemplate restTemplate = new RestTemplate();
    @Scheduled(fixedRate = 30000, initialDelay = 5000)
    public void generatePatient() {
        String url = "http://localhost:11434/api/generate";
        Map<String, Object> request = new HashMap<>();
        request.put("model", "llama3.2");
        request.put("stream", false);
        request.put("prompt", "Generiere einen zufälligen deutschen Patienten im JSON-Format mit den Feldern: patientID, firstName, lastName, gender, birthDate, managingClinic, Sonstiges. Antworte NUR mit reinem JSON.");

        Map response = restTemplate.postForObject(url, request, Map.class);
        if(response!=null && response.containsKey("response")) {
            String jsonString = (String) response.get("response");
            System.out.println("Antwort " + jsonString);
            ObjectMapper mapper = new ObjectMapper();
            try {
            PatientEntity entity = mapper.readValue(jsonString, PatientEntity.class);
            patientRepository.save(entity);
            System.out.println("Erfolg");
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            
        }
        
    }
}

package com.med.backend;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
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
        request.put("format", "json"); 
        request.put("prompt", 
            "Generiere einen zufälligen deutschen Patienten im JSON-Format. " +
"WICHTIG: Nutze exakt die vorgegebenen Feldnamen (insbesondere 'sonstiges') und erstelle keine eigenen Key-Namen! " +
"Felder: patientID, firstName, lastName, gender, birthDate, managingClinic, sonstiges, Puls, Blutdruck, Notfallstufe"
        );

        Map response = restTemplate.postForObject(url, request, Map.class);
        if (response != null && response.containsKey("response")) {
            String jsonString = (String) response.get("response");
            
            if (jsonString == null || jsonString.isBlank()) {
                System.err.println("Ollama hat einen leeren Text geliefert.");
                return;
            }

            String cleanedJson = jsonString.replaceAll("```json", "")
                                           .replaceAll("```", "")
                                           .trim();

            if (cleanedJson.isEmpty()) {
                System.err.println(" Kein valides JSON in der Ollama-Antwort vorhanden.");
                return;
            }

            System.out.println(" KI-Patient: " + cleanedJson);
            ObjectMapper mapper = new ObjectMapper();

            try {
                PatientEntity entity = mapper.readValue(cleanedJson, PatientEntity.class);

                if (entity.getSonstiges() != null && !entity.getSonstiges().isEmpty()) {
                    String embedUrl = "http://localhost:11434/api/embeddings";
                    Map<String, Object> embedRequest = new HashMap<>();
                    embedRequest.put("model", "nomic-embed-text");
                    embedRequest.put("prompt", entity.getSonstiges());

                    Map embedResponse = restTemplate.postForObject(embedUrl, embedRequest, Map.class);
                    if (embedResponse != null && embedResponse.containsKey("embedding")) {
                        java.util.List<Double> doubleList = (java.util.List<Double>) embedResponse.get("embedding");

                        float[] floatArray = new float[doubleList.size()];
                        for (int i = 0; i < doubleList.size(); i++) {
                            floatArray[i] = doubleList.get(i).floatValue();
                        }

                        entity.setVector(floatArray); 
                        System.out.println("Vektor-Embedding erfolgreich erzeugt!");
                    }
                }

                patientRepository.save(entity);
                System.out.println(" Patient erfolgreich in PostgreSQL gespeichert!");

            } catch (Exception e) {
                System.err.println("⚠️ Fehler beim Parsen/Speichern: " + e.getMessage());
            }
        }
    }
}
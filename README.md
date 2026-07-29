# FHIR Gateway & AI Vector Engine

Ein Fullstack-Projekt zur Erfassung, FHIR-Standardisierung und semantischen Suche von Patientendaten.

Das System verbindet ein Web-Frontend mit einem Java Spring Boot Backend. Über PostgreSQL mit pgvector und lokale KI-Modelle via Ollama werden eingehende Daten verarbeitet, im HL7 FHIR (R4) Format strukturiert und für eine Vektorsuche indiziert.

---

## Features

- **Datenerfassung im Frontend:** Formular zur Eingabe von Stammdaten, Vitalwerten (Puls, Blutdruck) und Notfallstufe mit dynamischer Farbanpassung.
- **FHIR R4 Konvertierung:** Transformation der Formulardaten in valide HL7 FHIR-Patientenressourcen inklusive Custom Extensions für Vitalparameter.
- **Synthetische Testdaten:** Background-Scheduler (`OllamaScheduler`), der über Llama 3.2 automatisch deutsche Testpatienten erzeugt.
- **Semantische Suche:** Vektor-Embeddings von Freitext-Symptomen (`sonstiges`) über `nomic-embed-text` und Ähnlichkeitssuche mittels PostgreSQL (`pgvector`).

---

## Tech Stack

- **Frontend:** HTML5, CSS3, Vanilla JavaScript
- **Backend:** Java, Spring Boot (REST API, Scheduled Tasks)
- **FHIR Engine:** HAPI FHIR (R4)
- **KI & Embeddings:** Ollama (Llama 3.2, nomic-embed-text)
- **Datenbank:** PostgreSQL mit pgvector Extension

---

## Architektur und Datenfluss

1. **Dateneingang:** Das Backend empfängt Patientendaten über das Web-Formular oder generiert sie automatisch über den `OllamaScheduler`.
2. **Embedding:** Für den Freitext im Feld `sonstiges` wird über Ollama ein 768-dimensionaler Vektor erzeugt.
3. **FHIR-Transformation:** Der `FhirService` wandelt die Daten mithilfe von HAPI FHIR in eine standardisierte FHIR-Struktur um.
4. **Speicherung & Suche:** PostgreSQL speichert die Patient-Entity mitsamt Vektor. Über den Endpunkt `/api/patients/search` können Fälle semantisch verglichen werden.

---

## Beispieldaten (JSON Payload)

```json
{
  "patientID": "P-101",
  "firstName": "Max",
  "lastName": "Mustermann",
  "gender": "male",
  "birthDate": "1990-01-01",
  "managingClinic": "Charité",
  "Puls": "78",
  "Blutdruck": "120/80",
  "Notfallstufe": "Normal",
  "sonstiges": "Patient klagt über leichten Schwindel und Müdigkeit."
}
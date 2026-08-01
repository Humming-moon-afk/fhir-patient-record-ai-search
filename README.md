# Clinical Patient Register & Vector Search


## Tech Stack

* **Frontend:** HTML5, CSS3 (Flexbox), Vanilla JavaScript (Fetch API)
* **Backend:** Java, Spring Boot (REST API, JPA)
* **Datenbank:** PostgreSQL mit `pgvector`-Extension (im Docker-Container)
* **AI / Embeddings:** Ollama (`nomic-embed-text` Modell)

##  Features

* **Patienten-Registrierung:** Erfassung von Stammdaten, Vitalwerten (Puls, Blutdruck), Notfallstufe und Freitext-Notizen.
* **Automatische Embeddings:** Beim Speichern wird aus dem Freitext-Feld über Ollama automatisch ein Vektor Embedding erzeugt und in Postgres gespeichert.
* **Semantische Suche (k-NN):** Suche nach Symptomen oder Beschreibungen (z.B. "Kopfschmerz") findet semantisch passende Patientenakten – unabhängig von exakten Begriffstreffern.
* **Dynamische UI:** Farbliches Feedback für Notfallstufen (Normal, Dringend, Notfall) und dynamisches Rendern der Ergebniskarten.

##  Setup


```bash
docker start fhir_postgres

ollama serve
ollama pull nomic-embed-text

cd backend
mvn spring-boot:run


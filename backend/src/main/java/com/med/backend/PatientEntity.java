package com.med.backend;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientEntity {

    @Id
    @JsonProperty("patientID")
    @JsonAlias({"patientId", "id", "ID"})
    private String id;

    private String firstName;
    private String lastName;
    private String gender;
    private Date birthDate;
    private String managingClinic;
    private String Puls;
    private String Blutdruck;
    private String Notfallstufe;

   @JsonProperty("sonstiges")
    @JsonAlias({
        " sonstiges", " Sonstiges",
        "elsewise", "otherInfo", "Sonstiges", "notes",
        "besonderheiten", "Besonderheiten", "symptome", "Symptome"
    })
    private String sonstiges;

    @Column(columnDefinition = "vector(768)")
    private float[] vector;

    public PatientEntity() {
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getManagingClinic() {
        return managingClinic;
    }

    public void setManagingClinic(String managingClinic) {
        this.managingClinic = managingClinic;
    }

    public String getSonstiges() {
        return sonstiges;
    }

    public void setSonstiges(JsonNode node) {
        if (node == null || node.isNull()) {
            this.sonstiges = null;
        } else if (node.isValueNode()) {
            this.sonstiges = node.asText(); 
        } else {
            this.sonstiges = node.toString(); 
        }
    }

    public float[] getVector() {
        return vector;
    }

    public void setVector(float[] vector) {
        this.vector = vector;
    }

    public String getPuls() {
        return Puls;
    }

    public void setPuls(String puls) {
        Puls = puls;
    }

    public String getBlutdruck() {
        return Blutdruck;
    }

    public void setBlutdruck(String blutdruck) {
        Blutdruck = blutdruck;
    }

    public String getNotfallstuffe() {
        return Notfallstufe;
    }

    public void setNotfallstufe(String notfallstuffe) {
        Notfallstufe = Notfallstufe;
    }
}
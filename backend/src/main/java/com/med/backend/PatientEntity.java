package com.med.backend;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientEntity {
    @Id
    @JsonProperty("patientID")
    String ID;
    String firstName;
    String lastName;
    String gender;
    Date birthDate;
    String managingClinic;

    @JsonProperty("Sonstiges")
    String sonstiges;
    public PatientEntity() {

    }
    public String getID() {
        return ID;
    }
    public void setID(String iD) {
        ID = iD;
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
    public void setSonstiges(String sonstiges) {
        this.sonstiges = sonstiges;
    }
}

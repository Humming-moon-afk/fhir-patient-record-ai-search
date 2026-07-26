package com.med.backend;

import java.util.Date;

public interface PatientSearchResult {
    String getId();
    String getFirstName();
    String getLastName();
    String getGender();
    Date getBirthDate();
    String getManagingClinic();
    String getSonstiges();
}
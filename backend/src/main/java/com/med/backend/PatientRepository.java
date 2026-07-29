package com.med.backend;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PatientRepository extends JpaRepository<PatientEntity, String> {

    @Query(value = "SELECT id, " +
                   "first_name AS firstName, " +
                   "last_name AS lastName, " +
                   "gender, " +
                   "birth_date AS birthDate, " +
                   "managing_clinic AS managingClinic, " +
                   "Puls AS Puls, " + 
                   "Blutdruck AS Blutdruck, " + 
                   "Notfallstufe AS Notfallstufe, " +
                   "sonstiges " +
                   "FROM patient_entity " +
                   "WHERE vector IS NOT NULL " +
                   "ORDER BY vector <=> CAST(:queryVector AS vector) ASC " +
                   "LIMIT :limit", nativeQuery = true)
    List<PatientSearchResult> findSimilarPatients(@Param("queryVector") String queryVector, @Param("limit") int limit);
}
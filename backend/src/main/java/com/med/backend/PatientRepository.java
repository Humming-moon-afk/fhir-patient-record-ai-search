package com.med.backend;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PatientRepository extends JpaRepository<PatientEntity, String> {

    @Query(value = "SELECT * FROM patient_entity " +
                   "WHERE vector IS NOT NULL " +
                   "ORDER BY vector <=> CAST(:queryVector AS vector) ASC " +
                   "LIMIT :limit", nativeQuery = true)
    List<PatientEntity> findSimilarPatients(@Param("queryVector") String queryVector, @Param("limit") int limit);
}
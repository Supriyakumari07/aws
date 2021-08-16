package com.auditmanagement.severity.api.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auditmanagement.severity.api.model.AuditResponse;

@Repository
public interface ResponseRepo extends JpaRepository<AuditResponse, Integer> {

}

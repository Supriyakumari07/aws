package com.auditmanagement.severity.api.model;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor

public class AuditRequest {

	private String projectName;

	private String projectManagerName;

	private String applicationOwnerName;

	private AuditDetail auditDetail;

}
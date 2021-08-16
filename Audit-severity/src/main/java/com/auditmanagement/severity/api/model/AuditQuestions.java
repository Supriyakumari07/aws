package com.auditmanagement.severity.api.model;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditQuestions {

	private int questionSerialNo;
	private int questionId;
	private String auditType;
	private String question;
	private String response;

}
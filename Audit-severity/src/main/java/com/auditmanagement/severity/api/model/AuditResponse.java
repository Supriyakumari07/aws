package com.auditmanagement.severity.api.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "auditresponse")
public class AuditResponse {
	@Id
	@GeneratedValue
	private int auditId;

	private String projectExecutionStatus;

	private String remedialActionDuration;

	private String managerName;
	private String projectName;

	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDateTime;

	public void setCreationDateTime(Date creationDateTime2) {
		this.creationDateTime = creationDateTime2;
	}

}
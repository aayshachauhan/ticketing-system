package com.zee.ticket.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Table(name = "role_master")
public class UserRoleMasterEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "role_name")
	private String roleName;

	@Column(name = "role_code")
	private String roleCode;

	@Column(name = "role_description")
	private String roleDescription;

	@Column(name = "is_active")
	private String isActive;

	@Column(name = "create_date")
	private String createDate;

	@Column(name = "modify_date")
	private String modifyDate;

}

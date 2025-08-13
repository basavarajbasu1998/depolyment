package com.yethi.code.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "test_user")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Long id;

	@Column
	private String userName;

	@Column
	private String password;
	
	
}

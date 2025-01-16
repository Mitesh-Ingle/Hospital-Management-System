package com.tka.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "patients")
public class Patient {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
	private long pId;
	private String pName;
	private String pDisease;
	private String pContact;
	private String pAddress;
	private String pEmail;
	
	   @ManyToOne(optional = true)
	    @JoinColumn(name = "department_id")
	    private Department department;
}

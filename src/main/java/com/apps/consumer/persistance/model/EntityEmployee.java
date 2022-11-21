package com.apps.consumer.persistance.model;

import  javax.persistence.Column;
import  javax.persistence.Entity;
import  javax.persistence.Id;
import  javax.persistence.Table;
import  javax.persistence.GeneratedValue;
import  javax.persistence.GenerationType;

import 	lombok.NoArgsConstructor;
import 	lombok.AllArgsConstructor;
import 	lombok.Getter;
import	lombok.Setter;
 
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "employees")
public class EntityEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name", length = 50)
    private String firstName;
 
    @Column(name = "last_name", length = 50)
    private String lastName;  
    
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	
    	sb.append("Id: ")
    	  .append(id)
    	  .append(", firstName: ")
    	  .append(firstName)
    	  .append(", lastName: ")
    	  .append(lastName)
    	  ;
    	
    	return sb.toString();
    }
}
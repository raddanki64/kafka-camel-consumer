package com.apps.consumer.persistance;

import  com.apps.consumer.persistance.model.EntityEmployee;

import  org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<EntityEmployee, String> {
}
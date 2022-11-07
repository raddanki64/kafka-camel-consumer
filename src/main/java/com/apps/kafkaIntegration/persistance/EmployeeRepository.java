package com.apps.kafkaIntegration.persistance;

import  com.apps.kafkaIntegration.persistance.model.EntityEmployee;

import  org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<EntityEmployee, String> {
}
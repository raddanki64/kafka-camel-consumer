package com.apps.kafkaIntegration.persistance;

import  com.apps.kafkaIntegration.persistance.model.EntitySampleMessage;

import  org.springframework.data.repository.CrudRepository;

public interface SampleMessagesRepository extends CrudRepository<EntitySampleMessage, String> {
}
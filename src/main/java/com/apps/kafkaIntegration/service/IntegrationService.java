package com.apps.kafkaIntegration.service;

public interface IntegrationService {
    void process(String payload) throws Exception;
}

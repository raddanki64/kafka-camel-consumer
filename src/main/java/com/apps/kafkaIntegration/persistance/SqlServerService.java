package com.apps.kafkaIntegration.persistance;

import  com.apps.kafkaIntegration.service.domain.SampleMessage;

public interface SqlServerService {
    void process(SampleMessage sampleMessage) throws Exception;
}

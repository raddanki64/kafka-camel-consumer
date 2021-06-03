package com.apps.kafkaIntegration.api.messaging.inbound;

import  com.apps.kafkaIntegration.service.IntegrationService;

import  org.springframework.beans.factory.annotation.Autowired;
import  org.springframework.stereotype.Component;
import  org.apache.camel.Exchange;
import  org.apache.camel.Processor;

@Component
public class MessageProcessor implements Processor {
    @Autowired
    private IntegrationService integrationService;

    public void process(Exchange exchange) throws Exception {
    	integrationService.process((String) exchange.getIn().getBody());
    }
}

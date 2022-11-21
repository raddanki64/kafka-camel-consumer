package com.apps.consumer.routes;

import 	org.springframework.beans.factory.annotation.Value;
import  org.springframework.stereotype.Component;

import  org.apache.camel.builder.RouteBuilder;
import	org.apache.camel.model.dataformat.JsonLibrary;

import	lombok.NoArgsConstructor;

import  org.slf4j.Logger;
import  org.slf4j.LoggerFactory;

import  com.apps.consumer.exceptions.ValidationException;
import	com.apps.consumer.persistance.model.EntityEmployee;

@Component
@NoArgsConstructor
public class EmployeeEventsRouteBuilder extends RouteBuilder {
    private static Logger logger = LoggerFactory.getLogger(EmployeeEventsRouteBuilder.class);
	
	@Value("${kafka.inbound.endpoint}")
	private String 	inboundQueue;	

    @Override
    public void configure() throws Exception {		
        onException(ValidationException.class)
        .log("Observed validation exception")
        .markRollbackOnly()
        .useOriginalMessage()
        .logStackTrace(true)        
        .end();

        onException(Exception.class)
        .log("Observed exception")
        .markRollbackOnly()
        .useOriginalMessage()
        .logStackTrace(true)
        .end();
        
        logger.info("Kafka endpoint: {}", inboundQueue);
        
		// Reader route
		from(inboundQueue)
		.routeId("Kafka.InboundMessage.Route")
		.to("log:inbound-msg")
		.unmarshal()
		.json(JsonLibrary.Jackson, EntityEmployee.class)
		.to("log:parsed-entity")
		.to("jpa:" + EntityEmployee.class.getName())
		.end();
    }
}
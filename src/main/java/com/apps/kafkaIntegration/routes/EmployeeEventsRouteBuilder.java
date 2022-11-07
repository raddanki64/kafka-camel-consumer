package com.apps.kafkaIntegration.routes;

import  org.springframework.beans.factory.annotation.Autowired;
import 	org.springframework.beans.factory.annotation.Value;
import  org.springframework.stereotype.Component;

import  org.apache.camel.builder.RouteBuilder;
import	org.apache.camel.model.dataformat.JsonLibrary;

import	lombok.NoArgsConstructor;

import  org.slf4j.Logger;
import  org.slf4j.LoggerFactory;

import  com.apps.kafkaIntegration.exceptions.ValidationException;
import	com.apps.kafkaIntegration.processors.EmployeeEventProcessor;

import	com.apps.kafkaIntegration.persistance.model.EntityEmployee;

@Component
@NoArgsConstructor
public class EmployeeEventsRouteBuilder extends RouteBuilder {
    private static Logger logger = LoggerFactory.getLogger(EmployeeEventsRouteBuilder.class);

	@Value("${quartz2.initialDelay}")
	private int initialDelay;

	@Value("${quartz2.repetionCount}")
	private int repetionCount;

	@Value("${kafka.outbound.endpoint}")
	private String 	outbound;
	
	@Value("${kafka.inbound.endpoint}")
	private String 	inboundQueue;	

	@Autowired
	private EmployeeEventProcessor empEventProcessor;

    @Override
    public void configure() throws Exception {
		String timerExpression = String.format("timer://empeventstimer?delay=%d&repeatCount=%d",
					initialDelay,
					repetionCount);
		
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

        // Writer route
		from(timerExpression)
		.routeId("Kafka.Scheduler.Route")
		.log("Will generate employee event messages")
		.process(empEventProcessor)
		.split()
		.body()
		.parallelProcessing()
		.log("Forwading to outbound kafka topic")
		.to(outbound)
		.end();
		
		
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
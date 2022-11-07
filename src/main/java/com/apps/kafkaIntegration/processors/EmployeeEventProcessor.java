package com.apps.kafkaIntegration.processors;

import  org.springframework.stereotype.Component;
import 	org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

import  org.apache.camel.Exchange;
import  org.apache.camel.Processor;

import 	lombok.NoArgsConstructor;

import  org.slf4j.Logger;
import  org.slf4j.LoggerFactory;

import	com.apps.kafkaIntegration.services.domain.Employee;

@Component
@NoArgsConstructor
public class EmployeeEventProcessor implements Processor {
    private static Logger logger = LoggerFactory.getLogger(EmployeeEventProcessor.class);
    
    @Value("${msgCount}")
    private int msgCount;

    public void process(Exchange exchange) throws Exception {
        try {
            List<String> lstOfMessages = new ArrayList<String>();

            for(int idx = 0; idx < msgCount; idx++ ) {
            	Employee anEmployee = Employee.createEmployee();
            	String asJson = anEmployee.toJson();            	
                lstOfMessages.add(asJson);
            }

            logger.info("Count of employee items to be dispatched: " + lstOfMessages.size());
            exchange.getIn().setBody(lstOfMessages);
        }
        catch(Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
    }
}

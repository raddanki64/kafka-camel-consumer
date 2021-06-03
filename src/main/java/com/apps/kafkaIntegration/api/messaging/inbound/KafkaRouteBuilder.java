package com.apps.kafkaIntegration.api.messaging.inbound;

import  com.apps.kafkaIntegration.api.messaging.exception.ValidationException;

import  org.apache.camel.EndpointInject;
import  org.apache.camel.Endpoint;
import  org.apache.camel.Processor;
import  org.apache.camel.Exchange;
import  org.apache.camel.LoggingLevel;
import  org.apache.camel.builder.RouteBuilder;
import  org.slf4j.Logger;
import  org.slf4j.LoggerFactory;
import  org.springframework.beans.factory.annotation.Autowired;
import  org.springframework.stereotype.Component;

@Component
public class KafkaRouteBuilder extends RouteBuilder {
    public static final String INPUT = "Input";
    private static Logger LOG = LoggerFactory.getLogger(KafkaRouteBuilder.class);

    /*
    @Autowired
    private MessageProcessor processor;

    @EndpointInject(uri = "properties:{{spring.inbound.endpoint}}")
    private Endpoint input;

    @EndpointInject(uri = "properties:{{spring.inbound.dlq}}")
    private Endpoint dlq;
    */

    @Override
    public void configure() throws Exception {
	/*
        onException(ValidationException.class)
                .handled(true)
                .process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        LOG.error("Observed input data validation exception");
                    }
                })
                .to(dlq)
                .end();
	*/

        onException(Exception.class)
            .handled(true)
            .process(new Processor() {
                public void process(Exchange exchange) throws Exception {
                    LOG.error("Observed exception");
                }
            })
            .end();



	String topicName = "topic=ramesh-topic";
	String kafkaServer = "kafka:localhost:9092";
	String zooKeeperHost = "zookeeperHost=localhost&zookeeperPort=2181";
	String serializerClass = "serializerClass=kafka.serializer.StringEncoder";

	String toKafka = new StringBuilder().append(kafkaServer)
				.append("?")
				.append(topicName)
				.append("&")
				.append(zooKeeperHost)
				.append("&")
				.append(serializerClass)
				.toString();

	from("file:C:/users/17328/kafka_tests?noop=true").split().tokenize("\n").to(toKafka);


	/*
        from(input)
            .routeId(INPUT)
            .log(LoggingLevel.INFO, "Inbound message headers: ${headers}")
            .log(LoggingLevel.INFO, JmsRouteBuilder.class.getName(), "InBound message payload: ${body} from " + INPUT)
            .process(processor)
            .end();
	*/
    }
}
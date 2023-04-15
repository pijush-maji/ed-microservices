package com.microservices.twitter.to.kafka.exception;

public class TwitterToKafkaServiceException extends RuntimeException {

    public TwitterToKafkaServiceException(){
        super();
    }

    public TwitterToKafkaServiceException(String message) {
        super(message);
    }
    public TwitterToKafkaServiceException(String message, Throwable t) {
        super(message,t);
    }
}

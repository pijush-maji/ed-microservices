package com.microservices.twitter.to.kafka;

import com.microservices.twitter.to.kafka.config.TwitterToKafkaServiceConfigData;
import com.microservices.twitter.to.kafka.runner.StreamRunner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Array;
import java.util.Arrays;


@SpringBootApplication
@Slf4j
public class TwitterToKafkaServiceApplication implements CommandLineRunner {


    private final TwitterToKafkaServiceConfigData toKafkaServiceConfigData;

    private final StreamRunner streamRunner;

    public TwitterToKafkaServiceApplication(TwitterToKafkaServiceConfigData configData,
                                            StreamRunner runner){
        this.toKafkaServiceConfigData=configData;
        this.streamRunner = runner;
    }

    public static void main(String[] args) {

        SpringApplication.run(TwitterToKafkaServiceApplication.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("App starts..");
        log.info(Arrays.toString(toKafkaServiceConfigData.getTwitterKeywords().toArray(new String[]{})));
        log.info(toKafkaServiceConfigData.getWelcomeMessage());
        streamRunner.start();
    }
}
package com.microservices.twitter.to.kafka.runner.impl;

import com.microservices.twitter.to.kafka.config.TwitterToKafkaServiceConfigData;
import com.microservices.twitter.to.kafka.listener.TwitterKafkaStatusListener;
import com.microservices.twitter.to.kafka.runner.StreamRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import twitter4j.FilterQuery;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import javax.annotation.PreDestroy;
import java.util.Arrays;

@Component
public class TwitterKafkaStreamRunner  implements StreamRunner {
    
    private static final Logger LOG = LoggerFactory.getLogger(TwitterKafkaStreamRunner.class);

    private final TwitterKafkaStatusListener twitterKafkaStatusListener;
    private final TwitterToKafkaServiceConfigData twitterToKafkaServiceConfigData;
    private TwitterStream twitterStream;
    
    public TwitterKafkaStreamRunner(TwitterKafkaStatusListener statusListener, 
                                    TwitterToKafkaServiceConfigData configData) {
        this.twitterKafkaStatusListener=statusListener;
        this.twitterToKafkaServiceConfigData = configData;
    }
    
    @Override
    public void start() throws TwitterException {
        twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.addListener(twitterKafkaStatusListener);
        addFilter();

    }

    @PreDestroy
    public void shutDown() {
        if(twitterStream!=null){
            LOG.info("Closing twitter stream!");
            twitterStream.shutdown();
        }
    }

    private void addFilter() {
        String[] keywords = twitterToKafkaServiceConfigData.getTwitterKeywords().toArray(new String[0]);
        FilterQuery filterQuery = new FilterQuery(keywords);
        twitterStream.filter(filterQuery);
        LOG.info("Stated filtering twitter stream for keywords {}", Arrays.toString(keywords));
    }
}

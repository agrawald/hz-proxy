package au.poc.dj.listener;

import au.poc.dj.model.Msg;
import com.hazelcast.core.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class QListener implements Runnable {
    private final IQueue<Msg> topic;
    private final IQueue<Msg> consumerQ;
    private long count;

    public QListener(HazelcastInstance hazelcastInstance) {
        this.topic = hazelcastInstance.getQueue("topic");
        this.consumerQ = hazelcastInstance.getQueue("consumer-one");
    }

    @Override
    public void run() {
        while (true) {
            try {
                final Msg msg = this.consumerQ.take();
                count++;
                log.info("C2: {} : {}", count, msg);
                msg.setStep("consumer-two");
                topic.add(msg);
            } catch (InterruptedException e) {
                log.error("Error", e);
            }
        }
    }

    @PostConstruct
    public void postConstruct() {
        Executors.newSingleThreadExecutor().submit(this);
    }
}

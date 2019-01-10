package au.poc.dj.listener;

import au.poc.dj.model.Msg;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class QListener implements Runnable {
    private final IQueue<Msg> consumerQ;
    private int count;

    public QListener(HazelcastInstance hazelcastInstance) {
        consumerQ = hazelcastInstance.getQueue("consumer-two");
    }

    @Override
    public void run() {
        while (true) {
            try {
                final Msg msg = consumerQ.take();
                log.info("PRODUCER: {} : {}", count, msg);
                count++;
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

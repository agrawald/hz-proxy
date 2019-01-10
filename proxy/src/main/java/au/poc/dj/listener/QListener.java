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
    private final IQueue<Msg> topic;
    private final HazelcastInstance hazelcastInstance;
    private int count;

    public QListener(HazelcastInstance hazelcastInstance) {
        this.topic = hazelcastInstance.getQueue("topic");
        this.hazelcastInstance = hazelcastInstance;
    }

    @Override
    public void run() {
        while (true) {
            try {
                final Msg msg = this.topic.take();
                count++;
                log.info("PROXY: {} : {}", count, msg);
                final IQueue<Msg> queue = hazelcastInstance.getQueue(msg.getStep());
                queue.add(msg);
            } catch (InterruptedException e) {
                log.error("Error", e);
            }
        }
    }

    @PostConstruct
    public void postConstruct() {
        Executors.newSingleThreadExecutor().submit(this);
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("PROXY: " + count);
    }

}

package au.poc.dj;

import au.poc.dj.model.Msg;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IQueue;
import com.hazelcast.core.ITopic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.Executors;

@Slf4j
@SpringBootApplication
public class Application {
    public static int count = 50000;
    public static long start;

    public static void main(String[] args) {
        final ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        final HazelcastInstance hz = ctx.getBean(HazelcastInstance.class);
        final IQueue<Msg> topic = hz.getQueue("topic");
        start = System.currentTimeMillis();
        Executors.newCachedThreadPool().submit(() -> {
        });
        for (long i = 0; i < count; i++) {
            final Msg msg = new Msg("p", i);
            topic.add(msg);
            log.info("Published: {}", msg);
        }
    }
}

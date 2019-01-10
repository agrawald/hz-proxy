package au.poc.dj.config;

import au.poc.dj.model.Msg;
import au.poc.dj.serializer.MsgSerializer;
import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.nio.serialization.StreamSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfig {
    @Bean
    Config config(MsgSerializer msgSerializer) {
        Config config = new Config();
        config.getSerializationConfig().addSerializerConfig(getSerializerConfig(msgSerializer, Msg.class));
        return config;
    }


    @Bean
    HazelcastInstance hazelcastInstance(Config config) {
        return Hazelcast.newHazelcastInstance(config);
    }

    private SerializerConfig getSerializerConfig(StreamSerializer serializer, Class clazz) {
        SerializerConfig config = new SerializerConfig()
                .setImplementation(serializer)
                .setTypeClass(clazz);
        return config;
    }
}

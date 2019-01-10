package au.poc.dj.serializer;

import au.poc.dj.model.Msg;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by proy on 19/11/16.
 */
@Component
public class MsgSerializer implements StreamSerializer<Msg> {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final ObjectReader READER = MAPPER.readerFor(Msg.class);
    private static final ObjectWriter WRITER = MAPPER.writerFor(Msg.class);

    @Override
    public void write(ObjectDataOutput objectDataOutput, Msg msg) throws IOException {
        objectDataOutput.writeUTF(WRITER.writeValueAsString(msg));
    }

    @Override
    public Msg read(ObjectDataInput objectDataInput) throws IOException {
        return READER.readValue(objectDataInput.readUTF());
    }

    @Override
    public int getTypeId() {
        return 3;
    }

    @Override
    public void destroy() {

    }
}
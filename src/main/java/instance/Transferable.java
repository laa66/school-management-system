package instance;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;

//marker interface
public interface Transferable extends Serializable {
    static String instanceToJSON(Transferable transferable) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        StringWriter writer = new StringWriter();
        mapper.writeValue(writer, transferable);
        return writer.toString();
    }
}

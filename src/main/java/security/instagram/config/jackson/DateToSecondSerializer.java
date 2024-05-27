package security.instagram.config.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Date;

/**
 * @author ket_ein17
 */
public class DateToSecondSerializer extends JsonSerializer<Date> { // to epoch
    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        long dateSeconds = date.getTime()/1000;
        jsonGenerator.writeString(String.valueOf(dateSeconds));
    }
}

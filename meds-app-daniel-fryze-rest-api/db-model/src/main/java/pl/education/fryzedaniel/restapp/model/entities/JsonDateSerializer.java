package pl.education.fryzedaniel.restapp.model.entities;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * A component used to serialize <code>java.util.Date</code> to a JSON representation
 * appropriate for out needs. In our case we are using format with dates only.
 */
@Component
public class JsonDateSerializer extends JsonSerializer<Date>{

	private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    /** {@inheritDoc} */
    @Override
    public void serialize(final Date date, final JsonGenerator gen, final SerializerProvider provider)
            throws IOException, JsonProcessingException {

        String formattedDate = dateFormat.format(date);
        gen.writeString(formattedDate);
    }

}
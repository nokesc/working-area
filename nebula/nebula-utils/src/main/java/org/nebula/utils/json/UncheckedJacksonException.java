package org.nebula.utils.json;

import java.io.UncheckedIOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class UncheckedJacksonException extends UncheckedIOException {

	private static final long serialVersionUID = -4701894478471882999L;

	static final UncheckedJacksonException wrapUnchecked(JacksonException jacksonException) {
        try {
            throw jacksonException;
        } catch (JsonMappingException e) {
            return new UncheckedJsonMappingException(e);
        } catch (JsonParseException e) {
            return new UncheckedJsonParseException(e);
        } catch (StreamReadException e) {
            return new UncheckedStreamReadException(e);
        } catch (JsonProcessingException e) {
            return new UncheckedJsonProcessingException(e);
        } catch (JacksonException e) {
            return new UncheckedJacksonException(e);
        }
    }

    public UncheckedJacksonException(JacksonException cause) {
        super(cause);
    }
}

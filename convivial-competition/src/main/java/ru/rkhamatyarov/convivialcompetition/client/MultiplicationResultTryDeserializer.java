package ru.rkhamatyarov.convivialcompetition.client;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ru.rkhamatyarov.convivialcompetition.client.dto.MultiplicationResultTry;

import java.io.IOException;

/**
 * Deserialize an attempt coming from the Multiplication microservice
 * into the competition representation of an attempt
 */
public class MultiplicationResultTryDeserializer extends JsonDeserializer<MultiplicationResultTry> {
    @Override
    public MultiplicationResultTry deserialize(
            JsonParser jsonParser,
            DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        ObjectCodec objectCodec = jsonParser.getCodec();
        JsonNode jsonNode = objectCodec.readTree(jsonParser);

        return new MultiplicationResultTry(
                jsonNode.get("user").get("login").asText(),
                jsonNode.get("multiplication").get("leftMultiplier").asInt(),
                jsonNode.get("multiplication").get("rightMultiplier").asInt(),
                jsonNode.get("multiplicationResult").asInt(),
                jsonNode.get("isRightResult").asBoolean()
                );
    }
}

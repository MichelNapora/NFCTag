package com.nfctag.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * Une chaine vide n'est pas une valeur : elle devient null a la lecture du JSON.
 * Sans ca, un formulaire vide envoie "" et les regles de format le refusent,
 * alors que le champ n'a simplement pas ete rempli.
 */
public class BlankToNull extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        String value = parser.getValueAsString();
        return value == null || value.isBlank() ? null : value;
    }
}
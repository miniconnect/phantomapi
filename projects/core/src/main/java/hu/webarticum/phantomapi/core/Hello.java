package hu.webarticum.phantomapi.core;

import java.io.IOException;
import java.io.UncheckedIOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import io.apicurio.datamodels.Library;
import io.apicurio.datamodels.models.Document;

public class Hello {

    private static final String EXAMPLE_OPENAPI_DESCRIPTION_YAML =
            "openapi: 3.1.0\n" +
            "info:\n" +
            "    title: Sample API\n" +
            "    description: Hello OpenAPI from PhantomAPI Core!\n" +
            "    version: 0.1.0\n";

    public static void hello() {
        ObjectMapper mapper = JsonMapper.builder(new YAMLFactory()).build();
        ObjectNode descriptionJsonNode;
        try {
            descriptionJsonNode = (ObjectNode) mapper.readTree(EXAMPLE_OPENAPI_DESCRIPTION_YAML);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        Document document = Library.readDocument(descriptionJsonNode);
        System.out.println(document.getInfo().getDescription());
    }

}
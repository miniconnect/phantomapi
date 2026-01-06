package hu.webarticum.phantomapi.core;

import java.io.IOException;
import java.io.UncheckedIOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import io.apicurio.datamodels.Library;
import io.apicurio.datamodels.models.openapi.v31.OpenApi31Document;
import io.apicurio.datamodels.models.openapi.v31.OpenApi31Response;
import io.apicurio.datamodels.models.openapi.v31.OpenApi31Schema;

public class Hello {

    private static final String EXAMPLE_OPENAPI_DESCRIPTION_YAML =
            "openapi: 3.1.0\n" +
            "info:\n" +
            "    title: Sample API\n" +
            "    description: Hello OpenAPI from PhantomAPI Core!\n" +
            "    version: 0.1.0\n" +
            "servers:\n" +
            "  - url: '{scheme}://{host}:{port}/{path}'\n" +
            "    variables:\n" +
            "        scheme:\n" +
            "            default: http\n" +
            "            enum: [http, https]\n" +
            "        host:\n" +
            "            default: localhost\n" +
            "            enum:\n" +
            "              - localhost\n" +
            "        port:\n" +
            "            default: '3000'\n" +
            "paths:\n" +
            "    /users/{id}:\n" +
            "        get:\n" +
            "            parameters:\n" +
            "              - name: id\n" +
            "                in: path\n" +
            "                required: true\n" +
            "                schema:\n" +
            "                    type: string\n" +
            "            responses:\n" +
            "                '200':\n" +
            "                    description: OK\n" +
            "                    content:\n" +
            "                        application/json:\n" +
            "                            schema:\n" +
            "                                $ref: '#/components/schemas/User'\n" +
            "components:\n" +
            "    schemas:\n" +
            "        User:\n" +
            "            type: object\n" +
            "            description: Hello Schema Description!\n" +
            "            required: [id, name]\n" +
            "            properties:\n" +
            "                id:\n" +
            "                    type: string\n" +
            "                name:\n" +
            "                    type: string\n";

    public static void hello() {
        ObjectMapper mapper = JsonMapper.builder(new YAMLFactory()).build();
        ObjectNode descriptionJsonNode;
        try {
            descriptionJsonNode = (ObjectNode) mapper.readTree(EXAMPLE_OPENAPI_DESCRIPTION_YAML);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        OpenApi31Document document = (OpenApi31Document) Library.readDocument(descriptionJsonNode);
        System.out.println(document.getClass());
        System.out.println(document.getInfo().getDescription());
        OpenApi31Response response = (OpenApi31Response) document.getPaths().getItem("/users/{id}").getGet().getResponses().getItem("200");
        OpenApi31Schema schema = (OpenApi31Schema) response.getContent().get("application/json").getSchema();
        System.out.println(document.getComponents().getSchemas().get(schema.get$ref()));
    }

}
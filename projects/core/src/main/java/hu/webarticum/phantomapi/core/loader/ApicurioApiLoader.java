package hu.webarticum.phantomapi.core.loader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import hu.webarticum.miniconnect.lang.ImmutableList;
import hu.webarticum.phantomapi.core.model.phantom.PhantomApi;
import hu.webarticum.phantomapi.core.model.phantom.PhantomEndpointDef;
import hu.webarticum.phantomapi.core.model.phantom.PhantomEndpointKey;
import hu.webarticum.phantomapi.core.model.phantom.PhantomResponseDef;
import io.apicurio.datamodels.Library;
import io.apicurio.datamodels.models.openapi.v31.OpenApi31Document;
import io.apicurio.datamodels.models.openapi.v31.OpenApi31MediaType;
import io.apicurio.datamodels.models.openapi.v31.OpenApi31Operation;
import io.apicurio.datamodels.models.openapi.v31.OpenApi31PathItem;
import io.apicurio.datamodels.models.openapi.v31.OpenApi31Paths;
import io.apicurio.datamodels.models.openapi.v31.OpenApi31Response;
import io.apicurio.datamodels.refs.LocalReferenceResolver;

public class ApicurioApiLoader implements ApiLoader {

    public PhantomApi load(JsonNode jsonNode) {
        ObjectNode descriptionJsonNode = (ObjectNode) jsonNode;
        Library.addReferenceResolver(new LocalReferenceResolver());
        OpenApi31Document document = (OpenApi31Document) Library.readDocument(descriptionJsonNode);
        document = (OpenApi31Document) Library.dereferenceDocument(document);
        OpenApi31Paths paths = (OpenApi31Paths) document.getPaths();
        List<PhantomEndpointDef> endpointsBuilder = new ArrayList<>();
        for (String pathName : paths.getItemNames()) {
            OpenApi31PathItem pathItem = (OpenApi31PathItem) paths.getItem(pathName);
            String method = "GET";
            String status = "200";
            OpenApi31Operation operation = (OpenApi31Operation) pathItem.getGet();
            String description = Objects.requireNonNullElse(operation.getDescription(), "[no description]");
            OpenApi31Response responseItem = (OpenApi31Response) operation.getResponses().getItem(status);
            for (Map.Entry<String, OpenApi31MediaType> contentEntry : responseItem.getContent().entrySet()) {
                String contentType = contentEntry.getKey();
                PhantomEndpointKey endpointKey = PhantomEndpointKey.of(pathName, method, contentType);
                PhantomResponseDef response = PhantomResponseDef.of(status, description);
                PhantomEndpointDef endpoint = PhantomEndpointDef.of(endpointKey, ImmutableList.of(response));
                endpointsBuilder.add(endpoint);
            }
        }
        return PhantomApi.of(ImmutableList.fromCollection(endpointsBuilder));
    }

}
package hu.webarticum.miniconnect.phantomapi.app;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import hu.webarticum.miniconnect.lang.ImmutableList;
import hu.webarticum.phantomapi.core.loader.ApicurioApiLoader;
import hu.webarticum.phantomapi.core.model.phantom.PhantomApi;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.annotation.RequestFilter;
import io.micronaut.http.annotation.ServerFilter;

@ServerFilter(Filter.MATCH_ALL_PATTERN)
class Ingress {

    private final ImmutableList<EndpointHandler> handlers;

    public Ingress(@Value("${phantomapi.openapi.descriptionPath}") String openapiFile) {
        ObjectMapper mapper = JsonMapper.builder(new YAMLFactory()).build();
        JsonNode descriptionJsonNode;
        try {
            descriptionJsonNode = mapper.readTree(new File(openapiFile));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        PhantomApi api = new ApicurioApiLoader().load(descriptionJsonNode);
        this.handlers = api.endpoints().map(EndpointHandler::new);
    }

    @RequestFilter
    public HttpResponse<?> filter(HttpRequest<?> request) {
        for (EndpointHandler handler : handlers) {
            HttpResponse<?> response = handler.handle(request);
            if (response != null) {
                return response;
            }
        }
        return HttpResponse.notFound();
    }

}

package hu.webarticum.miniconnect.phantomapi.app;

import java.util.List;
import java.util.Map;

import hu.webarticum.phantomapi.core.model.phantom.PhantomEndpointDef;
import hu.webarticum.phantomapi.core.model.phantom.PhantomEndpointKey;
import hu.webarticum.phantomapi.core.model.phantom.PhantomResponseDef;
import io.micronaut.http.HttpMethod;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.uri.UriMatchInfo;
import io.micronaut.http.uri.UriMatchTemplate;

class EndpointHandler {

    private final PhantomEndpointDef endpoint;

    private final UriMatchTemplate uriMatchTemplate;

    public EndpointHandler(PhantomEndpointDef endpoint) {
        this.endpoint = endpoint;
        this.uriMatchTemplate = UriMatchTemplate.of(endpoint.key().path());
    }

    public HttpResponse<?> handle(HttpRequest<?> request) {
        UriMatchInfo matchInfo = uriMatchTemplate.tryMatch(request.getPath());
        if (matchInfo == null) {
            return null;
        }
        PhantomEndpointKey key = endpoint.key();
        if (!request.getMethod().toString().equalsIgnoreCase(key.method())) {
            return null;
        }
        if (!acceptMethod(request.getMethod())) {
            return null;
        }
        if (!acceptMediaType(request.getHeaders().accept())) {
            return null;
        }
        for (PhantomResponseDef responseDef : endpoint.responses()) {
            // FIXME: temporarily only the statuc code 200 is supported
            if (isStatusMatching("200", responseDef.status())) {
                return HttpResponse.ok(Map.of("message", responseDef.message()));
            }
        }
        return HttpResponse.noContent();
    }

    private boolean acceptMethod(HttpMethod method) {
        return method.toString().equalsIgnoreCase(endpoint.key().method());
    }

    private boolean acceptMediaType(List<MediaType> acceptedMediaTypes) {
        for (MediaType mediaType : acceptedMediaTypes) {
            if (mediaType.matches(MediaType.of(endpoint.key().accept()))) {
                return true;
            }
        }
        return false;
    }

    private boolean isStatusMatching(String status, String statusTemplate) {
        if (status.equals(statusTemplate) || statusTemplate.equals("default")) {
            return true;
        }
        int statusLength = status.length();
        int statusTemplateLength = status.length();
        if (statusLength != statusTemplateLength) {
            return false;
        }
        for (int i = 0; i < statusLength; i++) {
            char t = statusTemplate.charAt(i);
            if (t != 'X' && t != status.charAt(i)) {
                return false;
            }
        }
        return true;
    }

}

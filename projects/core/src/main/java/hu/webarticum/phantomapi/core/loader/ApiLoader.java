package hu.webarticum.phantomapi.core.loader;

import com.fasterxml.jackson.databind.JsonNode;

import hu.webarticum.phantomapi.core.model.phantom.PhantomApi;

public interface ApiLoader {

    public PhantomApi load(JsonNode jsonNode);

}
package hu.webarticum.phantomapi.core.model.phantom;

import hu.webarticum.miniconnect.lang.ImmutableList;

public class PhantomApi {

    private final ImmutableList<PhantomEndpointDef> endpoints;

    private PhantomApi(ImmutableList<PhantomEndpointDef> endpoints) {
        this.endpoints = endpoints;
    }

    public static PhantomApi of(ImmutableList<PhantomEndpointDef> endpoints) {
        return new PhantomApi(endpoints);
    }

    public ImmutableList<PhantomEndpointDef> endpoints() {
        return endpoints;
    }

}

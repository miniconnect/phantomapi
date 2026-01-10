package hu.webarticum.phantomapi.core.model.phantom;

import hu.webarticum.miniconnect.lang.ImmutableList;

public class PhantomEndpointDef {

    private final PhantomEndpointKey key;

    private final ImmutableList<PhantomResponseDef> responses;

    private PhantomEndpointDef(PhantomEndpointKey key, ImmutableList<PhantomResponseDef> responses) {
        this.key = key;
        this.responses = responses;
    }

    public static PhantomEndpointDef of(PhantomEndpointKey key, ImmutableList<PhantomResponseDef> responses) {
        return new PhantomEndpointDef(key, responses);
    }

    public PhantomEndpointKey key() {
        return key;
    }

    public ImmutableList<PhantomResponseDef> responses() {
        return responses;
    }

}

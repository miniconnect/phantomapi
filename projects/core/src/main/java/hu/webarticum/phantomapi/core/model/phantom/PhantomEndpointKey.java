package hu.webarticum.phantomapi.core.model.phantom;

import java.util.Objects;

public class PhantomEndpointKey {

    private final String path;

    private final String method;

    private final String accept;

    private PhantomEndpointKey(String path, String method, String accept) {
        this.path = Objects.requireNonNull(path);
        this.method = Objects.requireNonNull(method);
        this.accept = Objects.requireNonNull(accept);
    }

    public static PhantomEndpointKey of(String path, String method, String accept) {
        return new PhantomEndpointKey(path, method, accept);
    }

    public String path() {
        return path;
    }

    public String method() {
        return method;
    }

    public String accept() {
        return accept;
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, method, accept);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof PhantomEndpointKey)) {
            return false;
        }
        PhantomEndpointKey other = (PhantomEndpointKey) obj;
        return path.equals(other.path) && method.equals(other.method) && accept.equals(other.accept);
    }

}

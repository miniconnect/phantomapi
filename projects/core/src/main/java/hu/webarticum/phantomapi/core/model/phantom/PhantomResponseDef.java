package hu.webarticum.phantomapi.core.model.phantom;

public class PhantomResponseDef {

    private final String status;

    // FIXME: for temporary testing purposes
    private final String message;

    private PhantomResponseDef(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public static PhantomResponseDef of(String status, String message) {
        return new PhantomResponseDef(status, message);
    }

    public String status() {
        return status;
    }

    public String message() {
        return message;
    }

}

package hu.webarticum.miniconnect.phantomapi.app;

import hu.webarticum.phantomapi.core.Hello;

import io.micronaut.context.ExecutionHandleLocator;
import io.micronaut.web.router.DefaultRouteBuilder;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class PhantomapiRouteBuilder extends DefaultRouteBuilder {

    //private final MyDispatcher dispatcher;

    public PhantomapiRouteBuilder(ExecutionHandleLocator locator) {
        super(locator);
        //this.dispatcher = dispatcher;
    }

    @Inject
    void buildRoutes() {
        Hello.hello();
        //GET(op.path(), dispatcher, "handle", HttpRequest.class);
    }

}
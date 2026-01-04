package hu.webarticum.phantomapi.app.launch;

import java.lang.invoke.MethodHandles;

import hu.webarticum.phantomapi.core.Hello;

import picocli.CommandLine;
import picocli.CommandLine.Command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Command(name = "HoloDBServer", mixinStandardHelpOptions = true)
public class PhantomapiServerMain implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void run() {
        Hello.hello();
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new PhantomapiServerMain()).execute(args);
        System.exit(exitCode);
    }

}

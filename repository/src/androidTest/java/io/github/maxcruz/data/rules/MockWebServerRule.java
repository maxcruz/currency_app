package io.github.maxcruz.data.rules;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

public class MockWebServerRule implements TestRule {

    private final MockWebServer server = new MockWebServer();

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                server.start();
                base.evaluate();
                server.shutdown();
            }
        };
    }

    public String getUrl() {
        return server.url("/").toString();
    }

    public void enqueueResponse(String body) {
        MockResponse response = new MockResponse();
        response.setBody(body);
        server.enqueue(response);
    }
}

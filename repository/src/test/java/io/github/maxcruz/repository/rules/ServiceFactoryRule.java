package io.github.maxcruz.repository.remote.rules;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import io.github.maxcruz.repository.remote.ServiceFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServiceFactoryRule implements TestRule {

    private ServiceFactory serviceFactory;
    private OkHttpClient.Builder builder;
    private HttpLoggingInterceptor interceptor;

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {

            @Override
            public void evaluate() throws Throwable {
                builder = mock(OkHttpClient.Builder.class);
                OkHttpClient client = mock(OkHttpClient.class);
                when(builder.build()).thenReturn(client);
                interceptor = mock(HttpLoggingInterceptor.class);
                serviceFactory = new ServiceFactory(builder, interceptor);
                base.evaluate();
            }
        };
    }

    public ServiceFactory getServiceFactory() {
        return serviceFactory;
    }

    public OkHttpClient.Builder getBuilder() {
        return builder;
    }

    public HttpLoggingInterceptor getInterceptor() {
        return interceptor;
    }
}

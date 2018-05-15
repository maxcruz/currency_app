package io.github.maxcruz.repository.rules;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import io.github.maxcruz.repository.remote.ServiceFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class ServiceFactoryRule implements TestRule {

    private ServiceFactory serviceFactory;

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {

            @Override
            public void evaluate() throws Throwable {
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                serviceFactory = new ServiceFactory(builder, interceptor);
                base.evaluate();
            }
        };
    }

    public ServiceFactory getServiceFactory() {
        return serviceFactory;
    }
}

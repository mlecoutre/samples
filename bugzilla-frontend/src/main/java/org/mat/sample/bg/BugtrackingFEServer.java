package org.mat.sample.bg;

import com.google.common.util.concurrent.AbstractIdleService;
import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.jersey.api.core.DefaultResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.core.spi.component.ioc.IoCComponentProviderFactory;
import com.sun.jersey.guice.spi.container.GuiceComponentProviderFactory;
import com.sun.net.httpserver.HttpServer;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import static com.google.inject.Guice.createInjector;
import static com.google.inject.util.Modules.override;

public class BugtrackingFEServer extends AbstractIdleService {
    private final int port;
    private final Module[] modules;

    private HttpServer httpServer;

    public BugtrackingFEServer(int port, Module... modules) {
        this.port = port;
        this.modules = modules;
    }

    public int getPort() {
        return port;
    }

    protected void startUp() throws Exception {
        ResourceConfig config = new DefaultResourceConfig(
                BugtrackingProxyResource.class, JacksonJsonProvider.class);

        Module module = override(new BugtrackingModule()).with(modules);
        Injector injector = createInjector(module);

        IoCComponentProviderFactory ioc = new GuiceComponentProviderFactory(
                config, injector);

        httpServer = HttpServerFactory.create("http://localhost:" + port + "/",
                config, ioc);
        httpServer.start();
    }

    protected void shutDown() {
        httpServer.stop(1);
    }

    public static void main(String[] args) {
        new BugtrackingFEServer(8080).startAndWait();
    }

    static class BugtrackingModule extends AbstractModule {
        protected void configure() {
            bind(TasksService.class).toInstance(new TasksService());
        }
    }
}

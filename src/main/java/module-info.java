open module com.jwebmp.testing {


    exports com.jwebmp.testing;
    exports com.jwebmp.testing.services;

    requires transitive com.guicedee.vertx.web;

    requires java.net.http;
    requires jdk.httpserver;
    requires static lombok;

    requires java.logging;

    requires transitive org.junit.jupiter.api;
    requires com.guicedee.guicedinjection;

    uses com.jwebmp.testing.services.ITestInstanceDestroyService;
    uses com.jwebmp.testing.services.ITestInstanceInitializerService;
    uses com.jwebmp.testing.services.ITestInstanceResetService;
}
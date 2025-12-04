package com.jwebmp.testing;

import com.guicedee.guicedinjection.GuiceContext;
//import com.jwebmp.testing.browserstack.BrowserStackLocalExtension;
import io.vertx.core.http.HttpServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@ExtendWith(BrowserStackLocalExtension.class)
public abstract class BaseIntegrationTest implements com.guicedee.vertx.web.spi.VertxHttpServerConfigurator
{
    @BeforeAll
    static void injectContext()
    {
        // Ensure Guice injection runs before any BrowserStack Local tunnel usage
        GuiceContext.instance()
                    .inject();
        System.setProperty("HTTP_ENABLED", "true");
    }

    @Test
    @DisplayName("BrowserStack Local Tunnel Smoke")
    public void localTunnelSmoke()
    {
        // No WebDriver here. If the extension starts successfully, the tunnel is up.
        // Tests that actually use BrowserStack should live in modules that depend on Selenium.
    }

    /**
     * Core e2e helper: starts a JDK HttpServer that serves htmlSupplier() at "/",
     * fetches it with JDK HttpClient, asserts 200 and basic HTML/body content, then stops the server.
     * Subclasses can simply call runHttpServerSmoke() in their tests.
     */
    protected void runHttpServerSmoke(String mustContainText) throws Exception {
        com.sun.net.httpserver.HttpServer server = com.sun.net.httpserver.HttpServer.create(new InetSocketAddress(0), 0);
        server.createContext("/", new StaticHtmlHandler(htmlSupplier()));
        server.setExecutor(null);
        server.start();
        int port = server.getAddress().getPort();
        String url = "http://127.0.0.1:" + port + "/";
        System.out.println("[BaseIT] Serving test page at: " + url);
        try {
            HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
            HttpRequest req = HttpRequest.newBuilder().uri(URI.create(url)).timeout(Duration.ofSeconds(10)).GET().build();
            long t0 = System.nanoTime();
            HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            long elapsedMs = (System.nanoTime() - t0) / 1_000_000;
            System.out.println("[BaseIT] Network fetch latency: " + elapsedMs + " ms");

            assertEquals(200, resp.statusCode(), "HTTP server should return 200 OK");
            // Basic header validation for correct serving
            String ct = resp.headers().firstValue("content-type").orElse("");
            assertTrue(ct.toLowerCase().contains("text/html"), "Content-Type should be text/html, was: " + ct);

            String body = resp.body();
            System.out.println("[BaseIT] Received HTML (truncated 300 chars):\n" + body.substring(0, Math.min(300, body.length())));
            if (mustContainText != null && !mustContainText.isBlank()) {
                assertTrue(body.contains(mustContainText), "Served HTML should contain '" + mustContainText + "'");
            }
            String lower = body.toLowerCase();
            assertTrue(lower.contains("<body") && lower.contains("</body>"), "Served HTML should include a body element");

            // Optional BrowserStack SDK-style validation (no-op when not configured)
            maybeValidateWithBrowserStackSDK(url, resp);
            validateComponentConsistency(body);
        } finally {
            server.stop(0);
        }
    }

    public void startServer()
    {
        // Placeholder for servers the integrator may start before tests.
    }

    @Override
    public HttpServer builder(HttpServer builder)
    {
        builder.requestHandler(req -> req.response()
                                         .end(htmlSupplier()));
        return builder;
    }

    protected abstract String htmlSupplier();

    /** Simple handler that serves a fixed HTML payload. */
    private static class StaticHtmlHandler implements HttpHandler {
        private final byte[] payload;
        StaticHtmlHandler(String html) { this.payload = html.getBytes(StandardCharsets.UTF_8); }
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            exchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, payload.length);
            try (OutputStream os = exchange.getResponseBody()) { os.write(payload); }
        }
    }

    /**
     * Optional hook to perform BrowserStack SDK style validation. This is a no-op unless
     * system properties or environment variables indicate BrowserStack SDK usage.
     * Subclasses may override to integrate with real SDK/WebDriver when available.
     */
    protected void maybeValidateWithBrowserStackSDK(String url, HttpResponse<String> resp) {
        try {
            String enabled = System.getProperty("browserstack.sdk.enabled", System.getenv("BROWSERSTACK_SDK_ENABLED"));
            if (enabled == null || !Boolean.parseBoolean(enabled)) {
                System.out.println("[BaseIT] BrowserStack SDK validation skipped (not enabled).");
                return;
            }
            // Minimal validations that would typically be captured by SDK or network logs
            System.out.println("[BaseIT] [BS-SDK] Validating page URL: " + url);
            System.out.println("[BaseIT] [BS-SDK] Status: " + resp.statusCode());
            System.out.println("[BaseIT] [BS-SDK] Content-Type: " + resp.headers().firstValue("content-type").orElse(""));
            // Here one could add calls to SDK/WebDriver to setStatus or attach logs when available
        } catch (Throwable t) {
            System.out.println("[BaseIT] BrowserStack SDK validation encountered an error: " + t);
        }
    }

    /**
     * Basic cross-check for component consistency. Default implementation ensures
     * the HTML contains a head and body and is non-empty. Subclasses can override
     * to add stricter checks across HTML/CSS/JS as needed.
     */
    protected void validateComponentConsistency(String html) {
        assertTrue(html != null && !html.isBlank(), "Rendered HTML should not be empty");
        String l = html.toLowerCase();
        assertTrue(l.contains("<html"), "Rendered HTML should include an <html> element");
        assertTrue(l.contains("<head") && l.contains("</head>"), "Rendered HTML should include a head element");
    }
}

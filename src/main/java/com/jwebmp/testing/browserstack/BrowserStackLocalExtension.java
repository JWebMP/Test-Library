/*
package com.jwebmp.testing.browserstack;

import com.browserstack.local.Local;
import org.junit.jupiter.api.extension.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

*/
/**
 * JUnit 5 extension that manages the lifecycle of BrowserStack Local.
 *
 * It starts the local tunnel before each test when BROWSERSTACK_ACCESS_KEY is provided
 * (or system property browserstack.accessKey) and stops it after each test.
 *
 * Additionally, if no environment or system property is found, it will attempt to read
 * accessKey (and userName, when relevant elsewhere) from a browserstack.yml file on the classpath.
 * Only the 'accessKey' field is used here.
 *
 * Additional arguments can be supplied via system properties or environment variables:
 *  - browserstack.verbose / BROWSERSTACK_VERBOSE => "true" for verbose
 *  - browserstack.folder / BROWSERSTACK_FOLDER => path for folder testing
 *  - browserstack.force / BROWSERSTACK_FORCE => "true" to force kill others
 *  - browserstack.onlyAutomate / BROWSERSTACK_ONLY_AUTOMATE => "true" only automate
 *  - proxyHost/Port/User/Pass via browserstack.proxyHost, etc (or env)
 *//*

public class BrowserStackLocalExtension implements BeforeEachCallback, AfterEachCallback, ExecutionCondition {

    private static void log(String msg) {
        System.out.println("[BS-LOCAL] " + msg);
    }

    private static final ExtensionContext.Namespace NS = ExtensionContext.Namespace.create("com","jwebmp","bs-local");

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        String key = resolveAccessKey();
        if (key == null || key.isBlank()) {
            log("Disabled: BrowserStack Local not configured. Missing access key from sys/env/yml.");
            return ConditionEvaluationResult.disabled("BrowserStack Local not configured (missing access key). Skipping test.");
        }
        log("Enabled: Access key detected; BrowserStack Local extension will run.");
        return ConditionEvaluationResult.enabled("BrowserStack Local configured");
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        ExtensionContext.Store store = context.getStore(NS);
        Local local = store.get("bsLocal", Local.class);
        if (local != null && local.isRunning()) {
            log("beforeEach: Reusing existing BrowserStack Local instance (already running).");
            return;
        }
        local = new Local();
        Map<String, String> args = new HashMap<>();
        String key = resolveAccessKey();
        args.put("key", key);
        putIfPresent(args, "v", truthy("browserstack.verbose", "BROWSERSTACK_VERBOSE"));
        putIfPresent(args, "f", get("browserstack.folder", "BROWSERSTACK_FOLDER"));
        putIfPresent(args, "force", truthy("browserstack.force", "BROWSERSTACK_FORCE"));
        putIfPresent(args, "onlyAutomate", truthy("browserstack.onlyAutomate", "BROWSERSTACK_ONLY_AUTOMATE"));
        // proxy
        putIfPresent(args, "proxyHost", get("browserstack.proxyHost", "BROWSERSTACK_PROXY_HOST"));
        putIfPresent(args, "proxyPort", get("browserstack.proxyPort", "BROWSERSTACK_PROXY_PORT"));
        putIfPresent(args, "proxyUser", get("browserstack.proxyUser", "BROWSERSTACK_PROXY_USER"));
        putIfPresent(args, "proxyPass", get("browserstack.proxyPass", "BROWSERSTACK_PROXY_PASS"));

        log("beforeEach: Starting BrowserStack Local with args: " + args.keySet());
        local.start(args);
        log("beforeEach: BrowserStack Local running = " + local.isRunning());
        store.put("bsLocal", local);
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        ExtensionContext.Store store = context.getStore(NS);
        Local local = store.remove("bsLocal", Local.class);
        if (local != null) {
            try {
                log("afterEach: Stopping BrowserStack Local...");
                local.stop();
                log("afterEach: BrowserStack Local stopped.");
            } catch (Throwable ignored) {
                log("afterEach: Exception while stopping BrowserStack Local: " + ignored);
            }
        } else {
            log("afterEach: No BrowserStack Local instance found in store.");
        }
    }

    private static String get(String sys, String env) {
        String v = System.getProperty(sys);
        if (v != null && !v.isBlank()) return v;
        v = System.getenv(env);
        if (v != null && !v.isBlank()) return v;
        return null;
    }

    private static String truthy(String sys, String env) {
        String v = get(sys, env);
        if (v == null) return null;
        return String.valueOf(Boolean.parseBoolean(v));
    }

    private static void putIfPresent(Map<String, String> map, String k, String v) {
        if (v == null || v.isBlank()) return;
        map.put(k, v);
    }

    */
/**
     * Resolves the BrowserStack access key using precedence:
     * 1) System property browserstack.accessKey
     * 2) Environment variable BROWSERSTACK_ACCESS_KEY
     * 3) browserstack.yml on classpath (key: accessKey)
     *//*

    private static String resolveAccessKey() {
        String v = get("browserstack.accessKey", "BROWSERSTACK_ACCESS_KEY");
        if (v != null && !v.isBlank()) {
            log("resolveAccessKey: using key from sys/env.");
            return v;
        }
        String fromYml = readAccessKeyFromYml();
        if (fromYml != null && !fromYml.isBlank()) {
            log("resolveAccessKey: using key from browserstack.yml on classpath.");
            return fromYml;
        }
        return null;
    }

    private static String readAccessKeyFromYml() {
        try (InputStream is = BrowserStackLocalExtension.class.getClassLoader().getResourceAsStream("browserstack.yml")) {
            if (is == null) {
                log("readAccessKeyFromYml: browserstack.yml not found on classpath.");
                return null;
            }
            String content;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                content = br.lines().collect(Collectors.joining("\n"));
            }
            // Very small, tolerant parse: find a line starting with accessKey:
            for (String line : content.split("\n")) {
                String trimmed = line.trim();
                if (trimmed.startsWith("accessKey:")) {
                    String val = trimmed.substring("accessKey:".length()).trim();
                    // Remove quotes if present
                    if ((val.startsWith("\"") && val.endsWith("\"")) || (val.startsWith("'") && val.endsWith("'"))) {
                        val = val.substring(1, val.length() - 1);
                    }
                    return val;
                }
            }
            log("readAccessKeyFromYml: accessKey not found in browserstack.yml.");
            return null;
        } catch (Exception e) {
            log("readAccessKeyFromYml: exception reading browserstack.yml: " + e.getMessage());
            return null;
        }
    }
}
*/

package com.jwebmp.testing;

import com.guicedee.guicedinjection.GuiceContext;
import com.jwebmp.testing.services.ITestInstanceDestroyService;
import com.jwebmp.testing.services.ITestInstanceInitializerService;
import lombok.extern.java.Log;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ServiceLoader;
import java.util.Set;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

/**
 * Base testing class
 */
@SuppressWarnings({"unchecked",
        "rawtypes"})
@TestInstance(PER_CLASS)
@Log
public class BaseTest
        implements IBaseTest
{

    @Test
    public void initializeTest()
    {
        log.config("Completely Initialization Test");
    }

    @Override
    public void reset()
    {
        tearDown();
        IBaseTest.tearDownAll();
        IBaseTest.initializeAll();
        initialize();
    }

    @Override
    @AfterEach
    public void tearDown()
    {
        log.config("Destroying Everything...");
        Set<ITestInstanceDestroyService> resetServices = GuiceContext.instance()
                                                                     .getLoader(ITestInstanceDestroyService.class, ServiceLoader.load(ITestInstanceDestroyService.class));
        for (ITestInstanceDestroyService resetService : resetServices)
        {
            resetService.destroy(this);
        }
    }

    @Override
    @BeforeEach
    public void initialize()
    {
        soutDivider();
        log.config("Initializing Everything");
        Set<ITestInstanceInitializerService> resetServices = GuiceContext.instance()
                                                                         .getLoader(ITestInstanceInitializerService.class,
                                                                                    ServiceLoader.load(ITestInstanceInitializerService.class));
        for (ITestInstanceInitializerService resetService : resetServices)
        {
            resetService.initialize(this);
        }
    }

    public void soutDivider()
    {
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<===============================================================>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}

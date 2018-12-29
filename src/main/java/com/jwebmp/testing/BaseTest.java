package com.jwebmp.testing;

import com.jwebmp.guicedinjection.GuiceContext;
import com.jwebmp.logger.LogFactory;
import com.jwebmp.logger.logging.LogColourFormatter;
import com.jwebmp.testing.services.ITestInstanceDestroyService;
import com.jwebmp.testing.services.ITestInstanceInitializerService;
import org.junit.jupiter.api.*;

import java.util.ServiceLoader;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.TestInstance.Lifecycle.*;

/**
 * Base testing class
 */
@TestInstance(PER_CLASS)
public class BaseTest
{
	private static final Logger log = LogFactory.getLog(BaseTest.class);

	@Test
	void initializeTest()
	{
		log.config("Completely Initialization Test");
	}

	void reset()
	{
		tearDown();
		tearDownAll();
		initAll();
		init();
	}

	@AfterEach
	void tearDown()
	{
		log.config("Destroying Everything...");
		Set<ITestInstanceDestroyService> resetServices = GuiceContext.instance()
		                                                             .getLoader(ITestInstanceDestroyService.class, ServiceLoader.load(ITestInstanceDestroyService.class));
		for (ITestInstanceDestroyService resetService : resetServices)
		{
			resetService.destroy(this);
		}
	}

	@AfterAll
	static void tearDownAll()
	{

	}

	@BeforeAll
	static void initAll()
	{
		LogFactory.configureConsoleColourOutput(Level.FINE);
		LogColourFormatter.setRenderBlack(false);
		log.config("Starting Up Instance");
	}

	@BeforeEach
	void init()
	{
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

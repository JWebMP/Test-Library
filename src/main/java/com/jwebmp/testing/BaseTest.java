package com.jwebmp.testing;

import com.guicedee.guicedinjection.GuiceContext;
import com.guicedee.logger.LogFactory;
import com.guicedee.logger.logging.LogColourFormatter;
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
		implements IBaseTest
{
	private static final Logger log = LogFactory.getLog(BaseTest.class);

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
		IBaseTest.initAll();
		init();
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
	public void init()
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

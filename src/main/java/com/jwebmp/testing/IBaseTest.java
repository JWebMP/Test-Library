package com.jwebmp.testing;

import com.guicedee.logger.LogFactory;
import com.guicedee.logger.logging.LogColourFormatter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.logging.Level;

public interface IBaseTest
{
	@AfterAll
	static void tearDownAll()
	{
		LogFactory.getLog(IBaseTest.class)
		          .info("Tear Down All Complete");
	}

	@BeforeAll
	static void initAll()
	{
		System.out.println("Initializing All");
		LogFactory.configureConsoleColourOutput(Level.FINE);
		LogColourFormatter.setRenderBlack(false);
		System.setErr(System.out);
		LogFactory.getLog(IBaseTest.class)
		          .info("Initialized All");
	}

	void reset();

	@AfterEach
	void tearDown();

	@BeforeEach
	void init();


}

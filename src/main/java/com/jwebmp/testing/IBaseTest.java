package com.jwebmp.testing;


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
	}

	@BeforeAll
	static void initAll()
	{
		System.out.println("Initializing All");
		System.setErr(System.out);
	}

	void reset();

	@AfterEach
	void tearDown();

	@BeforeEach
	void init();


}

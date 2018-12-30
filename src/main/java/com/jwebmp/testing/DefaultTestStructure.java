package com.jwebmp.testing;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class DefaultTestStructure extends BaseTest
{

	@AfterAll
	public static void tearDownAll()
	{
		IBaseTest.tearDownAll();
	}

	@BeforeAll
	public static void initAll()
	{
		IBaseTest.initAll();
	}


	@Override
	@AfterEach
	public void tearDown()
	{
		super.tearDown();
	}

	@Override
	@BeforeEach
	public void init()
	{
		super.init();
	}

}

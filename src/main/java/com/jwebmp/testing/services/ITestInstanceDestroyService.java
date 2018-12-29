package com.jwebmp.testing.services;

import com.jwebmp.testing.BaseTest;

@FunctionalInterface
public interface ITestInstanceDestroyService
{
	/**
	 * Applies destroy logic for everything in the classpath
	 */
	void destroy(BaseTest testInstance);
}

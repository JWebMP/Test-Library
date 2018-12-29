package com.jwebmp.testing.services;

import com.jwebmp.testing.BaseTest;

@FunctionalInterface
public interface ITestInstanceInitializerService
{
	/**
	 * Applies resetting logic for everything in the classpath
	 */
	void initialize(BaseTest testInstance);
}

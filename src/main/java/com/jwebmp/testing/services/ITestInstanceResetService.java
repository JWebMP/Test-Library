package com.jwebmp.testing.services;

import com.jwebmp.testing.BaseTest;

@FunctionalInterface
public interface ITestInstanceResetService
{
	/**
	 * Applies resetting logic for everything in the classpath
	 */
	void reset(BaseTest testInstance);
}

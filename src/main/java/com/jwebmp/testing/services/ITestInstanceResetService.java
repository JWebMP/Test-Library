package com.jwebmp.testing.services;

import com.guicedee.guicedinjection.interfaces.IDefaultService;
import com.jwebmp.testing.BaseTest;

@FunctionalInterface
public interface ITestInstanceResetService<J extends ITestInstanceResetService<J>>
		extends IDefaultService<J>
{
	/**
	 * Applies resetting logic for everything in the classpath
	 */
	void reset(BaseTest testInstance);
}

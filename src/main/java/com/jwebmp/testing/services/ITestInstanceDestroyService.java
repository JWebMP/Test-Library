package com.jwebmp.testing.services;

import com.guicedee.client.services.IDefaultService;
import com.jwebmp.testing.BaseTest;

@FunctionalInterface
public interface ITestInstanceDestroyService<J extends ITestInstanceDestroyService<J>> extends IDefaultService<J>
{
	/**
	 * Applies destroy logic for everything in the classpath
	 */
	void destroy(BaseTest testInstance);
}

package com.jwebmp.testing;


import io.github.bonigarcia.seljup.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


@ExtendWith({SeleniumJupiter.class})
public class BaseIntegrationTest
{

	@Test
	@DisplayName("Chrome Driver Test")
	public void testWithPhantomJS(ChromeDriver chromeDriver) {
		// Use Chrome in this test
	}

	@Test
	@DisplayName("Firefox Driver Test")
	public void testWithFirefox(FirefoxDriver firefoxDriver) {
		// Use Firefox in this test
	}

	@Test
	@DisplayName("Edge Driver Test")
	void edgeTest(EdgeDriver driver) {
		// Use Edge
	}

	public void startServer()
	{

	}
}

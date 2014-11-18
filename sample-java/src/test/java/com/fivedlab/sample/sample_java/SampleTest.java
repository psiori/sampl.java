package com.fivedlab.sample.sample_java;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.junit.Test;

public class SampleTest {

	@Test
	public void testEndpointNotNull() {
		assertNotNull(Sample.getEndpoint());
	}

	@Test
	public void testEndpointDefaultValue() {
		assertSame("https://events.neurometry.com/sample/v01/event",
				Sample.getEndpoint());
	}

	@Test
	public void testSetEndPoint() {
		String endpoint = Sample.getEndpoint();
		Sample.setEndpoint("test");
		assertTrue("test".equals(Sample.getEndpoint()));
		Sample.setEndpoint(endpoint);
		assertTrue(endpoint.equals(Sample.getEndpoint()));
	}

	@Test
	public void testMinSdkVersion() {
		/**
		 * update this if you increase version numbers. should at least match
		 * the last released version so it's sure no stepping back my occur
		 **/
		int[] version = {0,0,5};
		
		StringTokenizer tokenizer = new StringTokenizer(Sample.getSdkVersion(), ".");
		int tokens = tokenizer.countTokens();
		assertTrue(tokens == 3);

		for (int i = 0; i < 3; i++) {
			assertTrue(Integer.valueOf(tokenizer.nextToken()) >= version[i]);
		}
	}
	
	@Test
	public void testServerSideDefaultValue() {
		assertTrue("ServerSide has to be true on default",  Sample.isServerSide() == true);
	}
	
	@Test 
	public void testSetServerSide() {
		boolean serverSide = Sample.isServerSide();
		Sample.setServerSide(!serverSide);
		assertFalse("ServerSide should be false", Sample.isServerSide());
		Sample.setServerSide(serverSide);
		assertTrue("ServerSide should be true", Sample.isServerSide());
	}
	
	@Test
	public void testAppTokenDefaultValue() {
		assertTrue("must be empty on default" ,Sample.getAppToken().isEmpty());
	}
	
	public void testSetAppToken() {
		String appToken = Sample.getAppToken();
		Sample.setEndpoint("my-token");
		assertTrue("my-token".equals(Sample.getAppToken()));
		Sample.setEndpoint(appToken);
		assertTrue(appToken.equals(Sample.getEndpoint()));
	}

	@SuppressWarnings(value = { "rawtypes", "unchecked" })
	@Test
	public void testTrackSucceeds() {
		Map map = new HashMap();
		map.put("app_token","sample.java-test-token");
		map.put("debug", true);
		String response = Sample.track("ping", "session", map);
		assertNotNull("repsonse should not be null", response);
		assertTrue("201".equals(response));
	}
	
	@SuppressWarnings(value = { "rawtypes", "unchecked" })
	@Test
	public void testTrackFails() {
		Map map = new HashMap();
		map.put("debug", new Boolean(true));
		String response = Sample.track("ping", "session", map);
		assertNotNull("repsonse should not be null", response);
		assertTrue("400".equals(response));
	}
}

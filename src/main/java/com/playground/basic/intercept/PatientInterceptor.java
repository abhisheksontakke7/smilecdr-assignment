package com.playground.basic.intercept;

import java.io.IOException;

import org.slf4j.LoggerFactory;

import ca.uhn.fhir.interceptor.api.Hook;
import ca.uhn.fhir.interceptor.api.Interceptor;
import ca.uhn.fhir.interceptor.api.Pointcut;
import ca.uhn.fhir.rest.api.CacheControlDirective;
import ca.uhn.fhir.rest.client.api.IClientInterceptor;
import ca.uhn.fhir.rest.client.api.IHttpRequest;
import ca.uhn.fhir.rest.client.api.IHttpResponse;
import ch.qos.logback.classic.Logger;

@Interceptor
public class PatientInterceptor implements IClientInterceptor {

	private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(PatientInterceptor.class);
	private long myRequestTime = 0L;
	protected static int searchCounter = 0;
	private static int iterationCounter = 0;

	public PatientInterceptor() {
		super();
	}

	@Override
	@Hook(Pointcut.CLIENT_REQUEST)
	public void interceptRequest(IHttpRequest request) {
		LOGGER.info("Request intercept called.");

	}

	@Override
	@Hook(Pointcut.CLIENT_RESPONSE)
	public void interceptResponse(IHttpResponse response) throws IOException {
		long timing = response.getRequestStopWatch().getMillis();
		myRequestTime = myRequestTime + timing;
		LOGGER.info("Response received in {} ms", timing);
		if (searchCounter == 20) {
			double avgRespTime = myRequestTime / 20;
			LOGGER.info("Average response time for 20 searches = {} ms", avgRespTime);
			myRequestTime = 0L;
			searchCounter = 0;
			iterationCounter++;
		}
		if (iterationCounter == 2) {
			CacheControlDirective cacheControlDirective = new CacheControlDirective();
			cacheControlDirective.setNoCache(true);
			cacheControlDirective.setNoStore(true);
		} else {
			CacheControlDirective cacheControlDirective = new CacheControlDirective();
			cacheControlDirective.setNoCache(false);
			cacheControlDirective.setNoStore(false);
		}
		searchCounter++;
	}

}

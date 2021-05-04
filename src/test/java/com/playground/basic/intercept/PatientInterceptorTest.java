package com.playground.basic.intercept;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.mockito.Mockito;

import ca.uhn.fhir.rest.client.api.IHttpRequest;
import ca.uhn.fhir.rest.client.api.IHttpResponse;
import ca.uhn.fhir.util.StopWatch;

public class PatientInterceptorTest {

	@Test
	public void testInterceptRequest(){
		IHttpRequest request = Mockito.mock(IHttpRequest.class);
		PatientInterceptor interceptor = new PatientInterceptor();
		interceptor.interceptRequest(request);
	}
	
	@Test
	public void testInterceptResponse() throws IOException{
		for (int i = 0; i < 40; i++) {
			IHttpResponse response = Mockito.mock(IHttpResponse.class);
			StopWatch stopWatch = Mockito.mock(StopWatch.class);
			PatientInterceptor interceptor = new PatientInterceptor();
			long timing = 200L;
			Mockito.doReturn(stopWatch).when(response).getRequestStopWatch();
			Mockito.doReturn(timing).when(stopWatch).getMillis();
			interceptor.interceptResponse(response);
			if (i < 20) {
				assertEquals((i+1), PatientInterceptor.searchCounter);
			} else {
				assertEquals((i-19), PatientInterceptor.searchCounter);
			}
			
		}
	}
	
}

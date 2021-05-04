import com.playground.basic.intercept.PatientInterceptor;
import com.playground.basic.service.ISearchService;
import com.playground.basic.serviceimpl.SearchServiceImpl;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;

public class SampleClient {

	public static void main(String[] theArgs) throws Exception {

		// Create a FHIR client
		FhirContext fhirContext = FhirContext.forR4();
		IGenericClient client = fhirContext.newRestfulGenericClient("http://hapi.fhir.org/baseR4");
		client.registerInterceptor(new LoggingInterceptor(false));
		client.registerInterceptor(new PatientInterceptor());// register PatientInterceptor
		
		ISearchService searchService = new SearchServiceImpl();
		searchService.searchPatient(client);
	}

	

}

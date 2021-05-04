import java.io.File;
import java.util.List;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;
import org.slf4j.LoggerFactory;

import com.playground.basic.intercept.PatientInterceptor;
import com.playground.basic.util.FileUtil;
import com.playground.basic.util.PropertiesCache;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;
import ch.qos.logback.classic.Logger;

public class SampleClient {

	private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(SampleClient.class);

	public static void main(String[] theArgs) throws Exception {

		// Create a FHIR client
		FhirContext fhirContext = FhirContext.forR4();
		IGenericClient client = fhirContext.newRestfulGenericClient("http://hapi.fhir.org/baseR4");
		client.registerInterceptor(new LoggingInterceptor(false));
		client.registerInterceptor(new PatientInterceptor());// register PatientInterceptor
		// get the directory location and file name from application.properties file
		String directoryLoc = PropertiesCache.getInstance().getProperty("directoryLocation");
		String fileName = PropertiesCache.getInstance().getProperty("surnameFileName");
		// read all the files from given directory location
		FileUtil fileUtil = new FileUtil();
		// processing the file as per requirement.
		try {
			// read all the contents from a file
			List<String> surnames = getFileContents(directoryLoc, fileName, fileUtil);
			// Search for Patient resources
			Bundle response = null;
			if (null != surnames && surnames.size() > 0) {
				// run the loop for 3 times
				for (int i = 1; i <= 3; i++) {
					LOGGER.info("Current iteration = {}", i);
					// run the loop for each surname present in the file
					for (String surname : surnames) {
						if (null != surname && surname.trim().length() > 0) {
							LOGGER.info("Fetching data for surname={}", surname);
							response = client.search().forResource("Patient")
									.where(Patient.FAMILY.matches().value(surname.toUpperCase())).returnBundle(Bundle.class)
									.execute();
							LOGGER.info("Response for surname= {} is {}", surname, response.getId());
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured while fetching patient details.", e.getMessage());
			throw new Exception("Exception while fetching patient details", e.getCause());
		}
	}

	public static List<String> getFileContents(String directoryLoc, String fileName, FileUtil fileUtil)
			throws Exception {
		return fileUtil.getFileContents(new File(directoryLoc.concat("\\".concat(fileName))));
	}

}

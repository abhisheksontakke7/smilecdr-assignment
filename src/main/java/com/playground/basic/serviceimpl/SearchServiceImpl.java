package com.playground.basic.serviceimpl;

import java.io.File;
import java.util.List;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;
import org.slf4j.LoggerFactory;

import com.playground.basic.service.ISearchService;
import com.playground.basic.util.FileUtil;

import ca.uhn.fhir.rest.client.api.IGenericClient;
import ch.qos.logback.classic.Logger;

public class SearchServiceImpl implements ISearchService {

	private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(SearchServiceImpl.class);

	@Override
	public void searchPatient(IGenericClient client) throws Exception {

		// get the directory location and file name from application.properties
		// file
		// read all the files from given directory location
		FileUtil fileUtil = new FileUtil();
		// processing the file as per requirement.
		try {
			// read all the contents from a file
			List<String> surnames = getFileContents(fileUtil);
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
									.where(Patient.FAMILY.matches().value(surname.toUpperCase()))
									.returnBundle(Bundle.class).execute();
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

	public List<String> getFileContents(FileUtil fileUtil) throws Exception {
		String fileName = "patient-surname.txt";
		ClassLoader classLoader = this.getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
		return fileUtil.getFileContents(file);
	}
}

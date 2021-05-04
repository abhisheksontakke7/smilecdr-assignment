package com.playground.basic.service;

import ca.uhn.fhir.rest.client.api.IGenericClient;

public interface ISearchService {

	public void searchPatient(IGenericClient client) throws Exception;
}

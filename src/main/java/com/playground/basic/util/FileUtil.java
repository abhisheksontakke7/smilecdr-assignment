package com.playground.basic.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

public class FileUtil {

	private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(FileUtil.class);

	/**
	 * This method reads all the contents from a file.
	 * 
	 * @param surnameFile
	 * @return List<String>
	 * @throws Exception 
	 */
	public List<String> getFileContents(File surnameFile) throws Exception {
		List<String> surnames = null;
		LOGGER.info("Fetching file contents.");
		try {
			surnames = getSurnameList(surnameFile);
		} catch (IOException e) {
			LOGGER.error("Error during file read. Error message:{}", e.getMessage());
			throw new Exception("Exception occured while getting data from file", e);
		}
		return surnames;
	}

	protected List<String> getSurnameList(File surnameFile) throws IOException {
		return Files.lines(surnameFile.toPath()).collect(Collectors.toList());
	}
}

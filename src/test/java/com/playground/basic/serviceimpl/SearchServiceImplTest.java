package com.playground.basic.serviceimpl;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.playground.basic.util.FileUtil;

public class SearchServiceImplTest {

	@Test
	public void testGetFileContents() throws Exception{
		FileUtil fileUtil = new FileUtil();
		SearchServiceImpl searchServiceImpl = new SearchServiceImpl();
		List<String> list = searchServiceImpl.getFileContents(fileUtil);
		assertTrue(list.size() > 0);
	}
}

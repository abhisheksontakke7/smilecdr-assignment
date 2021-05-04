package com.playground.basic.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

public class FileUtilTest {

	@Test
	public void testGetFileContents() throws Exception{
		FileUtil fileUtil = new Spy_FileUtil();
		File file = new File("D:/dummypath/test.txt");
		assertNotNull("File list is null", fileUtil.getFileContents(file));
	}
	
	@Test(expected = Exception.class)
	public void testGetFileContentsForException() throws Exception{
		FileUtil fileUtil = new Spy_FileUtil();
		File file = new File("D:/dummypath/abc.txt");
		//fileUtil.getFileContents(file);
		Mockito.doThrow(Exception.class).when(fileUtil).getFileContents(file);
	}
	
	public class Spy_FileUtil extends FileUtil{
		protected List<String> getSurnameList(File surnameFile) throws IOException{
			return Arrays.asList("Smith", "Joe");
			
		}
	}
}

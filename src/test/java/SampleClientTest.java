import java.util.List;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

import com.playground.basic.util.FileUtil;

public class SampleClientTest {

	@Test
	public void testMain() throws Exception{
		FileUtil fileUtil = new FileUtil();
		List<String> list = SampleClient.getFileContents("D:/test", "test.txt", fileUtil);
		SampleClient.main(new String[]{""});
		assertTrue(list.size() > 0);
	}
	
	
}

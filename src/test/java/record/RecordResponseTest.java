package record;

import ivy.basic.ViException;
import ivy.util.ReaderFileInString;

import java.io.File;
import java.util.Set;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

public class RecordResponseTest {

	String result;

	@Before
	public void read() {
		File file = new File("D:/", "attendance.html");
		try {
			result = ReaderFileInString.instance.read(file);
		} catch (ViException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test() {
		CatchLink cl = new CatchLink();
		cl.result = result;
		cl.parse();
		Set<String> set = cl.links;
		for (String s : set) {
			if (s.contains("#") || s.contains(".jsp"))
				continue;
			String source = Pattern.quote(s);
			String target = "style/" + s.replaceAll("/", "_");
			result = result.replaceAll(source, target);
			System.out.println("Source->" + source + ", Target->" + target);
		}
		System.out.println(result);
	}
}

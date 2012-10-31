package record;

import ivy.basic.ViException;
import ivy.util.ReaderFileInString;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

public class RecordCatchResultTimeTest {
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
		Matcher m = p.matcher(result);
		while (m.find()) {
			System.out.println(m.group().substring(1));
		}
	}

	Pattern p = Pattern.compile("[^\"]\\d{2}:\\d{2}:\\d{2}");

}

package record;

import ivy.basic.ViException;
import ivy.util.ReaderFileInString;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

import com.ly.global.SerInfo;

public class RecordCatchResultTimeTest {
	String result;

	@Before
	public void read() {
		File file = new File(SerInfo.getProjectPath() + "/record", "attendance.html");
		try {
			result = ReaderFileInString.instance.read(file);
		} catch (ViException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test() {
		Matcher m = ptime.matcher(result);
		while (m.find()) {
			System.out.println(m.group().substring(1));
		}

		m = pdate.matcher(result);
		while (m.find()) {
			System.out.println(m.group());
		}
	}

	Pattern ptime = Pattern.compile("[^\"]\\d{2}:\\d{2}:\\d{2}");
	Pattern pdate = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");

}

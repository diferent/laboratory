package record;

import ivy.basic.ViException;
import ivy.util.ReaderFileInString;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.ly.global.SerInfo;

public class ReadInputFormTest {

	private String result;

	@Before
	public void read() {

		File file = new File(SerInfo.getProjectPath() + "/record",
				"example.html");
		try {
			result = ReaderFileInString.instance.read(file);
		} catch (ViException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void catchForm() {
		CatchForm cf = new CatchForm();
		cf.catchForm(result);
	}

	@Test
	public void catchLinks() {
		CatchLink cl = new CatchLink();
		cl.result = result;
		cl.parse();
		cl.output();

	}

}

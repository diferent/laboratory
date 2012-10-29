package record;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class ReadInputFormTest {

	private StringBuilder buf = new StringBuilder();
	private String result;

	@Before
	public void read() {
		File file = new File("D:/", "example.html");
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String result = null;
			int line = 0;
			while ((result = reader.readLine()) != null) {
				// System.out.println("行号: " + (++line) + ",内容:" + result);
				buf.append(result);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		result = buf.toString();
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

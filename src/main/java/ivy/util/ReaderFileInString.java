package ivy.util;

import ivy.basic.ViException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

public class ReaderFileInString {
	public static final ReaderFileInString instance = new ReaderFileInString();
	public static final Logger logger = Logger
			.getLogger(ReaderFileInString.class);
	private StringBuilder buf = new StringBuilder();

	public String read(File file) throws ViException {
		if (file.exists()) {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(file));
				String result = null;
				while ((result = reader.readLine()) != null) {
					buf.append(result);
				}

			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new ViException(e.getMessage());
			} finally {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
			return buf.toString();
		}
		throw new ViException("File Not Found!");
	}
}

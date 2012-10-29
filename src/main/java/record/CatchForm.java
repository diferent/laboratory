package record;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CatchForm extends CatchHtml {
	private List<String> inputs = new ArrayList<String>();
	public Map<String, FormInput> form = new LinkedHashMap<>();
	public String formuri;
	private Pattern pattern = Pattern.compile("<input [^>]+>");
	private Pattern inputPattern = Pattern.compile(" name=\"([^\"]+)\"");
	private Pattern inputValuePattern = Pattern.compile(" value=\"([^\"]+)\"");
	private Pattern actionPattern = Pattern.compile(" action=\"([^\"]+)\"");
	private Matcher matcher;
	private Pattern formPattern = Pattern.compile("<form [^>]+>");
	private String formString;

	public void catchForm(String target) {
		if (target != null) {
			matcher = pattern.matcher(target);
			while (matcher.find()) {
				inputs.add(matcher.group());
			}
			catchInput();
			matcher = formPattern.matcher(target);
			if (matcher.find())
				formString = matcher.group();
			catchForm();
		}

	}

	private void catchForm() {
		if (formString != null) {
			matcher = actionPattern.matcher(formString);
			if (matcher.find())
				formuri = matcher.group(1);
		}
	}

	private void catchInput() {
		for (String s : inputs) {
			FormInput fi = new FormInput();
			fi.inputString = s;
			fi.parse();
			if (fi.name != null)
				form.put(fi.name, fi);
			System.out.println(fi);
		}
		System.out.println("Form Size " + form.size());
	}

	public class FormInput {

		public String name;
		public String value = "";
		public boolean isUsername;
		public boolean isPassword;

		public String inputString;
		public static final String CLASS = "class";
		public static final String PASSWORD = "password";
		public void parse() {
			Matcher m = inputPattern.matcher(inputString);
			if (m.find())
				name = m.group(1);
			m = inputValuePattern.matcher(inputString);
			if (m.find())
				value = m.group(1);
			if (hasClass()) {
				if (hasPassword())
					isPassword = true;
				else
					isUsername = true;
			}
		}

		protected boolean hasClass() {
			return inputString.indexOf(CLASS) > 0;
		}

		protected boolean hasPassword() {
			return inputString.indexOf(PASSWORD) > 0;
		}

		@Override
		public String toString() {
			return "Name is :"
					+ name
					+ "\t Value is : "
					+ value
					+ (isUsername ? ", Username" : isPassword ? ", Password"
							: "");
		}

	}
}
package record;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import record.CatchForm.FormInput;

public class RecLogin extends RecordTask implements IRecordTask {

	CatchForm cf;
	UserContext user;

	@Override
	public void doError() {

	}

	public Map<String, Object> fetchParam() {
		Map<String, Object> p = new HashMap<>();
		for (Map.Entry<String, FormInput> entry : cf.form.entrySet()) {
			FormInput fi = entry.getValue();
			if (fi.isPassword)
				fi.value = user.password;
			else if (fi.isUsername)
				fi.value = user.username;
			p.put(fi.name, fi.value);
		}
		return p;
	}

	public boolean fetchFormURI() throws URISyntaxException {
		if (cf.formuri == null || cf.formuri.isEmpty()) {
			error = "未获取得指定的Form URI";
			return false;
		}
		uri = new URI(rootURI + cf.formuri);
		return true;
	}

	private void formCommit() throws Exception {
		cf = new CatchForm();
		cf.catchForm(result);
		Map<String, Object> p = fetchParam();
		if (!fetchFormURI())
			throw new Exception("Form URI Not Found!");
		// uri = new URI("http://127.0.0.1:8080/catch");
		http.uri = uri;
		http.params = p;
		http.send(cover());
		pushResult(http.fetchResultAsString());
		check();
		redirect();
	}

	@Override
	public boolean start() {
		try {
			formCommit();
			Thread.sleep(getRandomSleepTime());
			formCommit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public long getRandomSleepTime() {
		return (long) (Math.random() * 300 + 200);
	}
	
	public void sleep() {
		try {
			Thread.sleep(getRandomSleepTime());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean check() throws Exception {
		if (result.contains("error") || result.contains("index")) {
			outputResult();
			throw new Exception("REDIRECT ERROR");
		}
		return true;
	}

	public boolean redirect() throws IOException {
		uri = fetchRedirectURI();
		if (uri != null) {
			http.uri = uri;
			http.params = null;
			http.send(cover());
			pushResult(http.fetchResultAsString());
			return true;
		}
		return false;
	}

	public URI fetchRedirectURI() {
		String s = "";
		URI uri = null;
		Pattern p = Pattern.compile(" href=\"([^\"]+)\"");
		Matcher m = p.matcher(result);
		if (m.find()) {
			s = m.group(1);
			System.out.println("REDIRECT: " + s);
			try {
				uri = new URI(s);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		return uri;
	}

}

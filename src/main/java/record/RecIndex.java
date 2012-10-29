package record;

import java.net.URI;

import org.apache.http.Header;

public class RecIndex extends RecordTask {

	CHttpProxy http = new CHttpProxy();
	URI uri;
	CatchForm cf;

	public void fetchCookie() {
		Header[] headers = http.response.getAllHeaders();
		for (Header h : headers) {
			if (h.getName().equalsIgnoreCase("Set-Cookie"))
				cookie = h.getValue();
		}
	}

	public boolean start() {
		try {
			uri = new URI(rootURI);
			http.post(uri, null);
			result = http.fetchResultAsString();
			fetchCookie();
			if (check()) {
				pushResult(result);
				sleep();
				return true;
			} else
				doError();
		} catch (Exception e) {
			e.printStackTrace();
			doError();
		}
		return false;
	}

	public void doError() {

	}

	public boolean check() throws Exception {
		if (result != null && result.indexOf("考勤") > 0)
			return true;
		outputResult();
		throw new Exception("登陆出错");
	}
}

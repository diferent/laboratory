package record;

import java.net.URI;
import java.util.regex.Pattern;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;

public abstract class RecordTask implements IRecordTask {

	CHttpProxy http = new CHttpProxy();
	URI uri;
	String error;
	String result;
	static String cookie;
	String rootURI = "http://kq.neusoft.com";
	String cookieName = "Cookie";
	String cookieValue;

	public static final String REFERER = "Referer";

	public HttpUriRequest addCookie(HttpUriRequest request) {
		if (findCookie(cookie))
			request.setHeader(cookieName, cookieValue);
		return request;
	}

	public HttpUriRequest cover() {
		HttpUriRequest request = http.buildRequest();
		request.setHeader(REFERER, rootURI + "/");
		return addCookie(request);
	}

	public boolean findCookie(String c) {
		if (c != null) {
			cookieValue = c.replace("; path=/", "");
			System.out.println("Cookie Value:" + cookieValue);
			return true;
		}
		System.out.println("Cookie Not Found!");
		return false;
	}

	public HttpUriRequest buildGet() {
		HttpGet get = new HttpGet();
		return get;
	}

	public HttpUriRequest buildPost(HttpPost post) {

		return post;
	}

	public void pushResult(String str) {
		result = str;
		outputResult();
	}

	public void outputResult() {
		System.out.println(result);
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
	
	public void parseResult( ) {
		
	}
	
	public Pattern hrefPattern = Pattern.compile(" href=\"([^\"]+)\"");
	public Pattern srcPattern  = Pattern.compile(" src=\"([^\"]+)\"");
}

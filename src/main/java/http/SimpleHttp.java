package http;


import java.net.URI;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import record.SimulationHttp;

public class SimpleHttp {

	public static String uri = "http://localhost:8080/catch";
	public static String kq = "http://kq.neusoft.com";

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(new URI(kq));

		post = (HttpPost) (new SimulationHttp()).fetch(post);

		HttpResponse response = client.execute(post, httpContext);

		HttpEntity entity = response.getEntity();
		System.out.println(EntityUtils.toString(entity));
		outputCookie();

	}

	public static void outputCookie() {
		List<Cookie> cookies = cookieStore.getCookies();
		for (int i = 0; i < cookies.size(); i++) {
			System.out.println("Local cookie: " + cookies.get(i));
		}
	}

	public static final String ChromeUserAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.4 (KHTML, like Gecko) Chrome/22.0.1229.94 Safari/537.4";

	public static final String FirefoxUserAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:14.0) Gecko/20100101 Firefox/14.0.1";

	public static CookieStore cookieStore = new BasicCookieStore();
	public static HttpContext httpContext = new BasicHttpContext();

	static {
		httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

	}

}

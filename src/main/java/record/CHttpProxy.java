package record;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

/**
 * 
 * @author holaivy@gmail.com
 * 
 */
public class CHttpProxy {

	public static CookieStore cookieStore = new BasicCookieStore();
	public static HttpContext httpContext = new BasicHttpContext();

	public static void outputCookie() {
		List<Cookie> cookies = cookieStore.getCookies();
		for (int i = 0; i < cookies.size(); i++) {
			System.out.println("Local cookie: " + cookies.get(i));
		}
	}

	HttpClient client = new DefaultHttpClient();

	public URI uri;
	public Map<String, Object> params;
	protected String method = HttpPost.METHOD_NAME;
	protected HttpUriRequest request;
	public HttpResponse response;
	protected String resultAsString;
	public HTTPType type = HTTPType.HttpClient;
	SimulationHttp simulation = new SimulationHttp();

	public String fetchResultAsString() throws ParseException, IOException {
		return resultAsString = EntityUtils.toString(response.getEntity());
	}

	public boolean fine() {
		return response != null
				&& response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
	}

	public HttpResponse post(URI uri, Map<String, Object> param)
			throws IOException {
		this.uri = uri;
		this.params = param;
		method = HttpPost.METHOD_NAME;
		buildRequest();
		return send();
	}

	public HttpResponse get(URI uri, Map<String, Object> param)
			throws IOException {
		this.uri = uri;
		this.params = param;
		method = HttpGet.METHOD_NAME;
		buildRequest();
		return send();
	}

	public void ready(URI uri, Map<String, Object> param, String method) {
		this.uri = uri;
		this.params = param;
		this.method = method;
	}

	public HttpResponse send() throws IOException {
		return response = client.execute(request, httpContext);
	}

	public HttpResponse send(HttpUriRequest r) throws IOException {
		this.request = r;
		return response = client.execute(r, httpContext);
	}

	public HttpUriRequest buildRequest() {
		if (method.equalsIgnoreCase(HttpPost.METHOD_NAME)) {
			request = buildHttpPost();
		} else if (method.equalsIgnoreCase(HttpGet.METHOD_NAME)) {
			request = buildHttpGet();
		}
		return simulation.fetch(request);
	}

	protected HttpGet buildHttpGet() {
		HttpGet get = new HttpGet(uri);
		return get;
	}

	protected HttpPost buildHttpPost() {
		HttpPost post = new HttpPost(uri);
		if (params != null) {
			HttpEntity entity = null;
			List<NameValuePair> p = new ArrayList<NameValuePair>();
			for (Map.Entry<String, Object> e : params.entrySet()) {
				p.add(new BasicNameValuePair(e.getKey(), e.getValue()
						.toString()));
			}
			try {
				entity = new UrlEncodedFormEntity(p, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			post.setEntity(entity);
		}
		return post;
	}

	public enum HTTPType {
		FireFox, Chrome, HttpClient;
	}

}

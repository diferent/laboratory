package record;

import ivy.common.StreamU;
import ivy.system.SysInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
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
	public Pattern hrefPattern = Pattern.compile(" href=\"([^\"]+)\"");
	public Pattern srcPattern = Pattern.compile(" src=\"([^\"]+)\"");
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
		if (result != str)
			result = str;
		outputResult();
		parseResult();
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

	public void parseResult() {

		CatchLink cl = new CatchLink();
		cl.result = result;
		cl.parse();
		callCapillaries(cl.links);

	}

	public void callCapillaries(Set<String> links) {
		int taskSize = links.size();
		ExecutorService pool = Executors.newFixedThreadPool(taskSize);
		List<Future<ResourceObject>> list = new ArrayList<Future<ResourceObject>>();
		for (String s : links) {
			try {
				URI uri = buildURI(s);
				FetchResource fr = new FetchResource(uri);
				Future<ResourceObject> f = pool.submit(fr);
				list.add(f);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		pool.shutdown();
		for (Future<ResourceObject> future : list) {
			ResourceObject ro;
			try {
				ro = future.get();
				System.out.println(ro);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	public URI buildURI(String uri) throws URISyntaxException {
		URI u = null;
		if (uri != null) {
			uri = uri.trim();
			uri = uri.startsWith("/") ? uri.substring(1) : uri;
			uri = rootURI + "/" + uri;
			u = new URI(uri);
		}
		return u;
	}

	public String fetchCapillaries(String uri) {
		return "";
	}

	public class FetchResource implements Callable<ResourceObject> {

		private URI uri;

		public FetchResource(URI uri) {
			super();
			this.uri = uri;
			down = new File(store.toString(), "kq");
			if (!down.exists())
				down.mkdirs();
		}

		public File down;
		public File store = SysInfo.instance.fetchStoreFileDir();

		@Override
		public ResourceObject call() throws Exception {
			File file = new File(down, uri.toString().replaceAll(rootURI, "")
					.replace("/", "_"));
			ResourceObject obj = new ResourceObject();
			if (file.exists()) {
				obj.type = ResourceType.File;
				obj.value = file.toString();
				return obj;
			}
			CHttpProxy h = new CHttpProxy();
			HttpGet get = new HttpGet(uri);
			new SimulationHttp().fetch(get);
			get.setHeader(REFERER, rootURI + "/");
			addCookie(get);
			h.send(get);
			HttpEntity entity = h.response.getEntity();

			if (entity.isStreaming()) {
				FileOutputStream out = new FileOutputStream(file);
				StreamU.stream(entity.getContent(), out);
				obj.value = file.toString();
				obj.type = ResourceType.File;
			} else {
				obj.value = h.fetchResultAsString();
				obj.type = ResourceType.Text;
			}
			return obj;
		}

	}

	public class ResourceObject {
		public ResourceType type;
		public String value;

		@Override
		public String toString() {
			return "TYPE:" + type + ", VALUE:" + value;
		}

	}

	public static enum ResourceType {
		File, Text;
	}

}

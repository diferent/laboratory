package record;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpUriRequest;

public class SimulationHttp {

	public HttpUriRequest request;

	protected List<HttpPairHeader> list = new ArrayList<HttpPairHeader>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		{
			add(new HttpPairHeader("Accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
			add(new HttpPairHeader("Accept-Language",
					"zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3"));
			add(new HttpPairHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:14.0) Gecko/20100101 Firefox/14.0.1"));
			add(new HttpPairHeader("Accept-Encoding", "gzip, deflate"));
		}
	};

	public HttpUriRequest fetch() {
		for (HttpPairHeader p : list) {
			request.setHeader(p.name, p.value);
		}
		return request;
	}

	public HttpUriRequest fetch(HttpUriRequest request) {
		this.request = request;
		return fetch();
	}

	public class HttpPairHeader {
		public String name;
		public String value;

		public HttpPairHeader(String name, String value) {
			super();
			this.name = name;
			this.value = value;
		}

		public HttpPairHeader() {
			super();
		}
	}

}

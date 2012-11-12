package record;

import java.net.URI;
import java.util.concurrent.Callable;

public class FetchResource implements Callable<Object> {

	
	private URI uri  ;
	
	
	
	public FetchResource(URI uri) {
		super();
		this.uri = uri;
	}



	@Override
	public Object call() throws Exception {
		
		return null;
	}

}

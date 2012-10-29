package record;

/**
 * @author holaivy@gmail.com
 * 
 */
public class AutoRec {

	public static void main(String[] args) {
		AutoRec r = new AutoRec();
		r.record();
	}

	public void record() {
		if (index.start()) {
			login.user = user;
			login.result = index.result;
			login.start();
			result = login.result;
		}
	}

	String result;

	public UserContext user = new UserContext("xubch", "!IVYivy2004");

	RecIndex index = new RecIndex();
	RecLogin login = new RecLogin();
}

package record;

import record.RecLogin.LoginType;

/**
 * @author holaivy@gmail.com
 * 
 */
public class AutoRec {
	/**
	 * 
	 */
	public String result;
	public UserContext user = new UserContext("xubch", "!IVYivy2004");

	public static void main(String[] args) {
		AutoRec r = new AutoRec();
		r.startRecord();
	}

	public void startRecord() {
		if (startIndex()) {
			login.result = index.result;
			login.start();
			result = login.result;
		}
	}

	public void startQuery() {
		if (startIndex()) {
			login.type = LoginType.Query;
			login.result = index.result;
			login.start();
			result = login.result;
		}
	}

	public boolean startIndex() {
		if (index.start()) {
			this.result = index.result;
			return true;
		}
		return false;
	}

	public AutoRec(UserContext user) {
		super();
		this.user = user;
		login = new RecLogin(user);
	}

	AutoRec() {
		super();
		login = new RecLogin(user);
	}

	RecIndex index = new RecIndex();
	RecLogin login;

}

package record;

public interface IRecordTask {
	void doError();

	boolean start();

	boolean check() throws Exception;
}

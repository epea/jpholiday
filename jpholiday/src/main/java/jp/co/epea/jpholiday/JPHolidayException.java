package jp.co.epea.jpholiday;

public class JPHolidayException extends Exception {

	/**
	 * */
	private static final long serialVersionUID = 6455120384844397924L;

	public JPHolidayException(String format) {
		super(format);
	}

	public JPHolidayException(Exception e) {
		super(e);
	}
}

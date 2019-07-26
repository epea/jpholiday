package jp.co.epea.jpholiday;

/**
 * ライブラリで発生する例外全般用.
 * 
 * @author yoshitake
 *
 */
public class JPHolidayException extends Exception {

	/**
	 * 生成シリアルバージョン.
	 */
	private static final long serialVersionUID = 6455120384844397924L;

	/**
	 * コンストラクタ.
	 * 
	 * @param msg メッセージ
	 */
	public JPHolidayException(String msg) {
		super(msg);
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param e 発生した例外
	 */
	public JPHolidayException(Exception e) {
		super(e);
	}
}

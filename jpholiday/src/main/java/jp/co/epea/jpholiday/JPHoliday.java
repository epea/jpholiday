package jp.co.epea.jpholiday;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 休日情報保持クラス.
 * 
 * @author yoshitake
 */
public class JPHoliday implements Serializable {
	/**
	 * 生成シリアルバージョン.
	 */
	private static final long serialVersionUID = -4699496678215224180L;

	/**
	 * 日時.
	 */
	private LocalDate date;
	
	/**
	 * 名称.
	 */
	private String name;

	/**
	 * コンストラクタ.
	 * 
	 * @param date 日時
	 * @param name 名称
	 */
	public JPHoliday(LocalDate date, String name) {
		super();
		this.date = date;
		this.name = name;
	}

	/**
	 * ゲッター.
	 * 
	 * @return 日時
	 */
	public LocalDate getDate() {
		return date;
	}

	void setDate(LocalDate date) {
		this.date = date;
	}

	/**
	 * ゲッター.
	 * 
	 * @return 名称
	 */
	public String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}

	/**
	 * 文字列を返す.
	 */
	@Override
	public String toString() {
		return "Holiday [date=" + date + ", name=" + name + "]";
	}

}

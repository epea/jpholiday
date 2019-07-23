package jp.co.epea.jpholiday;

import java.io.Serializable;
import java.time.LocalDate;

public class JPHoliday implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4699496678215224180L;

	private LocalDate date;
	
	private String name;

	public JPHoliday(LocalDate date, String name) {
		super();
		this.date = date;
		this.name = name;
	}

	public LocalDate getDate() {
		return date;
	}

	void setDate(LocalDate date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Holiday [date=" + date + ", name=" + name + "]";
	}

}

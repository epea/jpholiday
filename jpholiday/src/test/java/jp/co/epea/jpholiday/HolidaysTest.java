package jp.co.epea.jpholiday;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

class HolidaysTest {

	/*
	 * 内閣の外部APIをコール
	 * 接続先はHTTP.OKを返す前提
	 */
	@Test
	void getJPHolidays() throws JPHolidayException {
		Holidays holidays = new Holidays();
		List<JPHoliday> jpHolidays = holidays.getJPHolidays();
		assertNotNull(jpHolidays);
		assertAll("getJPHolidays", 
			() -> assertTrue(0 < jpHolidays.size()), 
			() -> assertNotNull(jpHolidays.get(0).getDate()),
			() -> assertNotNull(jpHolidays.get(0).getName())
		);
		
		String old = jpHolidays.get(0).getName();
		jpHolidays.get(0).setName("deepcopy確認");
		assertEquals(old,holidays.getJPHolidays().get(0).getName());
	}
	
	/*
	 * 内閣の外部APIを内部でコール
	 * 当年の正月と勤労感謝の日は祝日として返される前提
	 */
	@Test
	void isJPHoliday() throws JPHolidayException {
		Holidays holidays = new Holidays();
		
		// 当年の正月は多分リストにある
		LocalDate newYearsDay = LocalDate.of(LocalDate.now().getYear(), 1, 1);
		// 当年の勤労感謝の日は多分リストにある（最後）
		LocalDate kinrouDay = LocalDate.of(LocalDate.now().getYear(),11,23);
		// 平日
		LocalDate other = LocalDate.of(2014,6,7);
		
		assertAll("isJPHoliday", 
			() -> assertTrue(holidays.isJPHoliday(newYearsDay)),
			() -> assertTrue(holidays.isJPHoliday(kinrouDay)),
			() -> assertFalse(holidays.isJPHoliday(other)) 
		);
	}
}

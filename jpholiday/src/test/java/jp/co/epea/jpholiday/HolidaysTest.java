package jp.co.epea.jpholiday;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
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
	}
	
	

}

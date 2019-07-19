package jp.co.epea.jpholiday;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LoaderTest {

	// 接続先はHTTP.OKを返す前提
	@Test
	void loadTest() throws JPHolidayException {
		List<Holiday> holidays = new Loader().load();
		assertNotNull(holidays);
		assertAll("load", 
			() -> assertTrue(0 < holidays.size()), 
			() -> assertNotNull(holidays.get(0).getDate()),
			() -> assertNotNull(holidays.get(0).getName())
		);
	}

}

package jp.co.epea.jpholiday;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.http.HttpStatus;

import kong.unirest.Unirest;

public class Holidays {

	private static final String NAIKAKU_URL = "https://www8.cao.go.jp/chosei/shukujitsu/syukujitsu_kyujitsu.csv";

	private static final String S_JIS = "SHIFT-JIS";

	private static DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private List<JPHoliday> jpHolidays;

	public void load() throws JPHolidayException {
		var response = Unirest.get(NAIKAKU_URL).responseEncoding(S_JIS).asString();
		if (response.getStatus() != HttpStatus.SC_OK)
			throw new JPHolidayException(String.format("statuscode[%d]", response.getStatus()));
		this.jpHolidays = parse(response.getBody());
	}

	public List<JPHoliday> getJPHolidays() throws JPHolidayException {
		if (jpHolidays == null)
			load();
		return deepcopy(this.jpHolidays);
	}

	private List<JPHoliday> parse(String apiResponse) {
		return Stream.of(apiResponse.split("\r\n")).skip(1).map(Holidays::map).collect(Collectors.toList());
	}

	private static JPHoliday map(String value) {
		assert (value != null) : "value must not be null.";
		assert (value.matches("\\d{4}-\\d{2}-\\d{2},.+")) : String.format("正規表現エラー [%s]", value);
		String[] array = value.split(",");
		return new JPHoliday(LocalDate.parse(array[0], df), array[1]);
	}

	@SuppressWarnings("unchecked")
	public static <T> T deepcopy(T obj) throws JPHolidayException {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			new ObjectOutputStream(baos).writeObject(obj);
			return (T) new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray())).readObject();
		} catch (IOException | ClassNotFoundException e) {
			throw new JPHolidayException(e);
		}
	}
}

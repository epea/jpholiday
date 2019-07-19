package jp.co.epea.jpholiday;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.http.HttpStatus;

import kong.unirest.Unirest;

public class Loader {

	private static final String NAIKAKU_URL = "https://www8.cao.go.jp/chosei/shukujitsu/syukujitsu_kyujitsu.csv";

	private static final String S_JIS = "SHIFT-JIS";

	private static DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public List<JPHoliday> load() throws JPHolidayException {
		return load(NAIKAKU_URL);
	}

	public List<JPHoliday> load(String targetURL) throws JPHolidayException {
		var response = Unirest.get(targetURL).responseEncoding(S_JIS).asString();
		if (response.getStatus() != HttpStatus.SC_OK)
			throw new JPHolidayException(String.format("statuscode[%d]", response.getStatus()));
		return parse(response.getBody());
	}

	private List<JPHoliday> parse(String apiResponse) {
		return Stream.of(apiResponse.split("\r\n")).skip(1).map(Loader::map).collect(Collectors.toList());
	}

	private static JPHoliday map(String value) {
		assert (value != null) : "value must not be null.";
		assert (value.matches("\\d{4}-\\d{2}-\\d{2},.+")) : String.format("正規表現エラー [%s]", value);
		String[] array = value.split(",");
		return new JPHoliday(LocalDate.parse(array[0], df), array[1]);
	}
}

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

/**
 * 休日情報処理クラス.
 * 内閣が祝日を発表するのは向こう一年未満の模様（未確認）
 * 
 * @author yoshitake
 *
 */
public class Holidays {

	/**
	 * 内閣APIのURL.
	 */
	private static final String NAIKAKU_URL = "https://www8.cao.go.jp/chosei/shukujitsu/syukujitsu_kyujitsu.csv";

	/**
	 * 内閣APIの文字コード.
	 */
	private static final String S_JIS = "SHIFT-JIS";

	/**
	 * 内閣APIの日付けフォーマット.
	 */
	private static DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	/**
	 * 休日情報保持用リスト.
	 */
	private List<JPHoliday> jpHolidays;

	/**
	 * 読み込み処理.
	 * 
	 * @throws JPHolidayException 主に接続先がHTTP.OK以外
	 */
	public synchronized void load() throws JPHolidayException {
		var response = Unirest.get(NAIKAKU_URL).responseEncoding(S_JIS).asString();
		if (response.getStatus() != HttpStatus.SC_OK)
			throw new JPHolidayException(String.format("statuscode[%d]", response.getStatus()));
		this.jpHolidays = parse(response.getBody());
	}

	/**
	 * 休日情報保持用リストを取得する.
	 * 
	 * @return 休日情報保持用リスト(deepcopy)
	 * @throws JPHolidayException 例外発生
	 */
	public synchronized List<JPHoliday> getJPHolidays() throws JPHolidayException {
		if (jpHolidays == null) load();
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
	private static <T> T deepcopy(T obj) throws JPHolidayException {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			new ObjectOutputStream(baos).writeObject(obj);
			return (T) new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray())).readObject();
		} catch (IOException | ClassNotFoundException e) {
			throw new JPHolidayException(e);
		}
	}
	
	/**
	 * 指定日が祝日か判定する.
	 * 
	 * @param date 指定日
	 * @return true:祝日/false:祝日以外
	 * @throws JPHolidayException 例外発生
	 */
	public synchronized boolean isJPHoliday(LocalDate date) throws JPHolidayException {
		if (jpHolidays == null) load();
		return jpHolidays.stream().anyMatch(s -> s.getDate().isEqual(date));
	}
}

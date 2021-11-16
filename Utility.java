package aj;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Utility {
	public static String toTimeString(long seconds) {
		long m = seconds / 60;
		long s = seconds % 60;
		return String.format("%d:%02d", m, s);
	}
	public static String instantToString(Instant instant, String pattern)
	{
		try {
			// use system default zone otherwise pattern will not be recognized
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault());
			return formatter.format(instant);
		} catch (Exception e) {
			// something goes wrong
			return "";
		}
	}

}

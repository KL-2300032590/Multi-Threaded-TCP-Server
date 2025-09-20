package webs;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class LoggerUtil {
	private static final String LOG_FILE = "server.log";
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	public static synchronized void log(String msg) {
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE,true))){
			String timestamp = "["+ FORMATTER.format(LocalDateTime.now());
			writer.write(timestamp + msg);
            writer.newLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

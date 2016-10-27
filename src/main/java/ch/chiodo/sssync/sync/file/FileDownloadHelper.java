package ch.chiodo.sssync.sync.file;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class FileDownloadHelper {
    private static final String PATH_DELIMITER = "\\.(?=[^.]*$)";
    public static final String DATE_FORMAT_PATTERN = "dd-MM-yyyy-HH-mm";

    public static String getFilenameWithDate(String currentAbsolutePath) {
        String[] split = currentAbsolutePath.split(PATH_DELIMITER);
        String newName = split[0].concat("_").concat(getDateTimeString());
        return newName + ".".concat(split[1]);
    }

    private static String getDateTimeString() {
        DateTimeFormatter format = DateTimeFormat.forPattern(DATE_FORMAT_PATTERN);
        return DateTime.now().toString(format);
    }
}


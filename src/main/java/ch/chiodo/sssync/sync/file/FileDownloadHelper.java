package ch.chiodo.sssync.sync.file;

import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.io.IOException;
import java.io.InputStream;

public class FileDownloadHelper {

    private static final String PATH_DELIMITER = "\\.(?=[^.]*$)";
    public static final String DATE_FORMAT_PATTERN = "dd-MM-yyyy-HH-mm";

    private FileDownloadHelper() {
        //Empty private constructor to avoid instanciation of class
    }

    public static String getFilenameWithDate(String currentAbsolutePath) {
        String[] split = currentAbsolutePath.split(PATH_DELIMITER);
        String newName = split[0].concat("_").concat(getDateTimeString());
        return newName + ".".concat(split[1]);
    }

    private static String getDateTimeString() {
        DateTimeFormatter format = DateTimeFormat.forPattern(DATE_FORMAT_PATTERN);
        return DateTime.now().toString(format);
    }

    public static boolean equalsTransferFile(TransferFile fileOne, TransferFile fileTwo) {
        boolean result = fileOne.getLastModified().isEqual(fileTwo.getLastModified());
        if(!result) {
            try (InputStream is1 = fileOne.createInputStream()) {
                try (InputStream is2 = fileTwo.createInputStream()) {
                    result = IOUtils.contentEquals(is1, is2);
                }
            } catch (IOException e) {
                throw new NetworkException(e);
            }
        }
        return result;
    }
}


package ch.chiodo.sssync.sync.file;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

import static ch.chiodo.sssync.sync.file.FileDownloadHelper.DATE_FORMAT_PATTERN;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class FileDownloadHelperTest {
    @Test
    public void getFilenameWithDate() throws Exception {
        String path = "/home/user/ch.chiodo.java/file.txt";
        DateTime now = DateTime.now();
        String result = FileDownloadHelper.getFilenameWithDate(path);
        String excpected = "/home/user/ch.chiodo.java/file_" + now.toString(DateTimeFormat.forPattern(DATE_FORMAT_PATTERN)) + ".txt";
        assertThat(excpected, is(result));
    }
}
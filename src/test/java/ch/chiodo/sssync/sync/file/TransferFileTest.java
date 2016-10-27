package ch.chiodo.sssync.sync.file;

import ch.chiodo.sssync.sync.file.LegacyTransferFile;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TransferFileTest {
    @Test
    public void newFileName() throws Exception {
        String newFileName = LegacyTransferFile.getVersionedFileString("/tmp/ch.chiodo.file.java");
        String separator = DateTime.now().toString(DateTimeFormat.forPattern(LegacyTransferFile.DATE_FORMAT_PATTERN));
        String expected = "/tmp/ch.chiodo.file-" + separator + ".java";
        assertThat(expected, is(newFileName));
    }

}
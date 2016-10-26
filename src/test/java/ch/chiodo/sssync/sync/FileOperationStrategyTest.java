package ch.chiodo.sssync.sync;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class FileOperationStrategyTest {
    @Test
    public void copyFile() throws Exception {
        String testString = "test file";
        InputStream input = new ByteArrayInputStream(testString.getBytes());
        OutputStream output = new ByteArrayOutputStream();
        FileOperationStrategy fos = new FileOperationStrategy();
        fos.copyFile(input, output);
        assertThat(input.available(), is(0));
        assertThat(output.toString(), is(testString));
    }

}
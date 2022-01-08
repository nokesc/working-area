package org.nebula.io;

import static org.nebula.io.IOTemplate.IO_TEMPLATE;
import static org.nebula.io.Resources.UTF8_RESOURCES;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.nebula.io.IOTemplate.CloseAdapter;
import org.nebula.io.Resources.FileCharResource;
import org.nebula.io.file.FileUtil;

public class ResourcesTest {

    FileCharResource pom_xml = UTF8_RESOURCES.fileChars(new File("pom.xml"));

    @Test
    public void warnOnClose() {
        System.out.println(FileUtil.getCanonicalPath(new File(".")));
        var s = IO_TEMPLATE.$closeIOException_logWarnOnly().read(pom_xml::toReader, fileReader -> {
            var chars = new char[512];
            var read = fileReader.read(chars);
            return new String(chars, 0, read);
        });
        System.out.println(s);

    }

    @Test
    public void readLines() {
        IO_TEMPLATE.read(pom_xml::toBufferedReader, IOUtil::readLines)
                .forEach(line -> System.out.println("IN.read asLines: " + line));
        pom_xml.readLines().forEach(line -> System.out.println("pom_xml.readLines(): " + line));
        int max = 5;
        pom_xml.readLines((i, line) -> {
            System.out.printf("line %s/%s: %s\n", i, max, line);
            return i < (max - 1);
        });
    }

    @Test
    public void readString() {
        System.out.println(IO_TEMPLATE.read(pom_xml::toReader, IOUtil::asString));
        System.out.println(pom_xml.readString());
    }

    @Test
    public void writeLines() {
        UTF8_RESOURCES.fileChars(new File("text.txt")).writeLines(Arrays.asList("a", "b"));
    }

    @Test
    public void readGoogle() {
        System.out.println(UTF8_RESOURCES.urlChars("https://www.google.com").readString());
    }

    @Test
    public void closeAdapter() {
        try (var ca = CloseAdapter.LOG_ERROR_ONLY(pom_xml.toReader())) {
            var chars = new char[512];
            var read = ca.get().read(chars);
            System.out.println(new String(chars, 0, read));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

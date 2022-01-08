package org.nebula.io.file;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class FileUtilTest {

    public FileUtilTest() {
        super();
    }

    @Test
    public void testGetExtension() {
        assertThat(FileUtil.getExtension("ab.txt")).isEqualTo("txt");
    }

    @Test
    public void testGetExtension_multipleDots() {
        assertThat(FileUtil.getExtension("ab.txt.p")).isEqualTo("p");
    }

    @Test
    public void testGetExtension_endingDot() {
        assertThat(FileUtil.getExtension("ab.")).isNull();
    }

    @Test
    public void testGetExtension_NoDot() {
        assertThat(FileUtil.getExtension("abc")).isNull();
    }
}

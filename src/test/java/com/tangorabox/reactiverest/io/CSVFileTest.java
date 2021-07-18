package com.tangorabox.reactiverest.io;

import org.junit.jupiter.api.BeforeAll;

import java.nio.file.Files;
import java.nio.file.Path;

public class CSVFileTest {

    protected static final Path CSV_PATH = Path.of("src/test/resources/prices.csv");
    public static final int NUM_ENTRIES_IN_FILE = 4;

    @BeforeAll
    public static void beforeClass() {
        assert Files.exists(CSV_PATH) : "The test file must exists";
        assert Files.isRegularFile(CSV_PATH ) : "The test file must be a valid file";
    }
}

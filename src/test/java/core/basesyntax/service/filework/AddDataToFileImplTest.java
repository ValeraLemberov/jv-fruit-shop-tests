package core.basesyntax.service.filework;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddDataToFileImplTest {
    private static final String PATH_TO_FILE_TEST
            = "src/test/java/resources/test/fileReportTest.csv";
    private static final String WRONG_PATH_TO_FILE = "src/test/java/resources/test";
    private static AddDataToFile addDataToFile;
    private static Map<String,Integer> testMap;

    @BeforeClass
    public static void beforeClass() {
        addDataToFile = new AddDataToFileImpl();
        testMap = new HashMap<>();
    }

    @Before
    public void setUp() {
        File file = new File(PATH_TO_FILE_TEST);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Can't create new test file");
        }
        testMap.put("banana",320);
        testMap.put("apple",213);
        testMap.put("pineapple",32);
    }

    @Test
    public void name() {
        List<String> expected = new ArrayList<>();
        expected.add("fruit,quantity");
        expected.add("banana,320");
        expected.add("apple,213");
        expected.add("pineapple,32");
        addDataToFile.addInStorage(testMap,PATH_TO_FILE_TEST);
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(PATH_TO_FILE_TEST));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from test file");
        }
        assertEquals(expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void addDataToWrongPath_not_ok() {
        addDataToFile.addInStorage(testMap,WRONG_PATH_TO_FILE);
    }

    @After
    public void tearDown() {
        try {
            Files.delete(Path.of(PATH_TO_FILE_TEST));
        } catch (IOException e) {
            throw new RuntimeException("Can't delete test file");
        }
    }
}

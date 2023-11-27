import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;

public class LinebreakTest {
    @Test
    public void testLinebreak() throws IOException {
        List<String> strings = Files.readAllLines(Paths.get("logs/application.log"));
        strings.forEach(System.out::println);
    }

    @Test
    public void test() {
        System.out.println(byte[].class.getCanonicalName());
        System.out.println(int.class.getCanonicalName());
    }
}

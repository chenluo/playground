import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

public class KbdDetector {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.printf("[%s] start.%n", LocalDateTime.now().toString());
        while (true) {
            Process exec = Runtime.getRuntime().exec("bash -c lsusb | grep \"cnkb\"");
            try (BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(exec.getInputStream()))) {
                System.out.printf("[%s] detecting.%n", LocalDateTime.now().toString());
                String result = bufferedReader.readLine();
                if (result.isBlank()) {
                    System.out.printf("[%s] disconnected.%s%n", LocalDateTime.now().toString());
                    break;
                }
                Thread.sleep(60000);
            }
        }
    }
}

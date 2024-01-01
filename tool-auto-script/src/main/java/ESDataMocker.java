import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

public class ESDataMocker {

    public static void main(String[] args) throws IOException {
        new ESDataMocker().run();
    }

    private void run() throws IOException {
        int threadCount = 32;
        CountDownLatch latch = new CountDownLatch(threadCount);
        int totalDocCount = 1000000000;

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                int failCount = 0;
                for (int j = 0; j < totalDocCount; j++) {
                    if ((j + 1) % 1000 == 0) {
                        System.out.println(
                                Thread.currentThread().getName() + ": progress " + j + "/" +
                                        totalDocCount);
                    }
                    try {
                        postData();
                        //                try {
                        //                    Thread.sleep(1);
                        //                } catch (InterruptedException e) {
                        //                    e.printStackTrace();
                        //                }
                    } catch (Exception e) {
                        e.printStackTrace();
                        failCount++;
                        if (failCount > 10) {
                            System.out.println("[Main] failed too many times.");
                            break;
                        }
                    }
                }
                latch.countDown();
            });
        }
        try {
            latch.await();
            executorService.shutdown();
            while (!executorService.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void postData() throws IOException {
        //        System.out.println("[Post data] start");
        URL url = new URL("http://localhost:9200/simple-index-9999/_bulk");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setUseCaches(false);

        String authHeader = Base64.getEncoder()
                .encodeToString("elastic:elastic".getBytes(StandardCharsets.UTF_8));

        httpURLConnection.setRequestProperty("Content-type", "application/json;utf-8");
        httpURLConnection.setRequestProperty("Authorization", "Basic " + authHeader);

        String docTemplate = "{\"text-field\":\"%s\", \"keyword-field\":\"%s\"}";
        int docCountPerRequest = 50;
        StringBuilder stringBuilder = new StringBuilder();
        List<String> docList = new ArrayList<>(docCountPerRequest);
        for (int i = 0; i < docCountPerRequest; i++) {
            docList.add("{\"index\":{}}");
            String textField = generateString(ThreadLocalRandom.current().nextInt(5000));
            String keywordField = UUID.randomUUID().toString();
            docList.add(String.format(docTemplate, textField, keywordField));
        }
        stringBuilder.append(String.join(System.lineSeparator(), docList))
                .append(System.lineSeparator());

        try (OutputStream outputStream = httpURLConnection.getOutputStream()) {
            outputStream.write(stringBuilder.toString().getBytes(StandardCharsets.UTF_8));
        }

        int respCode = httpURLConnection.getResponseCode();

        if (respCode >= 200 && respCode < 300) {
            //            System.out.println("[Post data] success");
        } else {
            System.out.println("[Post data] failed: code " + httpURLConnection.getResponseCode());
        }
        try (InputStream inputStream = httpURLConnection.getInputStream()) {
        } catch (IOException e) {
            e.printStackTrace();
        }

        //        System.out.println("[Post data] end");
    }

    private String generateString(int length) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'

        String generatedString = ThreadLocalRandom.current().ints(leftLimit, rightLimit + 1)
                .map(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97) ? i : 32).limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }
}

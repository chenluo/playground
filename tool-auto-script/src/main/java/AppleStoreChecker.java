import com.google.gson.Gson;

import javax.mail.Session;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AppleStoreChecker {
    volatile Session session;
    String productUrlTemplate = "https://www.apple.com.cn/shop/buy-iphone/iphone-13-pro/%s";
    Map<String, ZonedDateTime> stockToAvailableTime = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException {
        new AppleStoreChecker().run();
    }

    private void run() throws IOException {
        int failCount = 0;

        String[] productCodes = {
//                "MLT63CH/A", // 银色
                "MLT53CH/A", // 石墨色
//                "MLT83CH/A" // 蓝色
        };
        for (int i = 0; i < 1000; i++) {
            try {
                for (int j = 0; j < productCodes.length; j++) {
//                    checkRecommend(productCode);
                    check(productCodes[j]);
                }
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
                failCount++;
                if (failCount > 10) {
                    System.out.println("[Main] failed too many times.");
                    break;
                }
            }
        }

//        sendMail("testSubject", "testBody");
    }

    private void sendSlackMessage(String message) throws IOException {
//        curl -X POST -H 'Content-type: application/json' --data '{"text":"Hello, World!"}' https://hooks.slack
//        .com/services/T0D189UMD/B02JQAG7BSL/DDggdBoSSUZZAFfnCc7JXOWs
        URL url = new URL("https://hooks.slack.com/services/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");
        HttpURLConnection hc = ((HttpURLConnection) url.openConnection());
        hc.setRequestMethod("POST");
        hc.setDoOutput(true);
        hc.setDoInput(true);
        hc.setUseCaches(false);

        hc.setRequestProperty("Content-type", "application/json;utf-8");

        try (OutputStream os = hc.getOutputStream()) {
            byte[] input = String.format("{\"text\":\"%s\"}", message).getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = hc.getResponseCode();
        if (responseCode == 200) {
            System.out.println("[Slack] push slack message success!");
        } else {
            System.out.println("[Slack] push failed: " + responseCode);
        }
        try (InputStream inputStream = hc.getInputStream()) {

        }
    }

    private void check(String productCode) throws IOException {
        System.out.println("[Check Main Product] start:" + productCode);
        String url = String.format("https://www.apple.com.cn/shop/fulfillment-messages?pl=true&mt=compact&parts" +
                ".0=%s&searchNearby=true&store=R401", productCode);
        String content = readContent(url);
        Gson gson = new Gson();
        Map<String, Object> body = ((Map) gson.fromJson(content, HashMap.class).get("body"));
        List<Map<String, Object>> storeList = (List) ((Map) ((Map<Object, Object>) body.get("content")).get(
                "pickupMessage")).get("stores");
        List<String> availableStore = new ArrayList<>();

        ZonedDateTime now = ZonedDateTime.now();

        for (Map<String, Object> store : storeList) {
            String storeName = ((String) store.get("storeName"));
            String availability =
                    ((String) ((Map<Object, Object>) ((Map<Object, Object>) store.get("partsAvailability")).get(productCode)).get("pickupDisplay"));
            if (!availability.equals("unavailable")) {
                System.out.println(availability);
                System.out.println(storeName);
                stockToAvailableTime.compute(storeName, (s, lastSeenTime) -> {
                    if (null == lastSeenTime || lastSeenTime.isBefore(now.minus(60, ChronoUnit.SECONDS))) {
                        availableStore.add(storeName);
                        return now;
                    }
                    return lastSeenTime;
                });
            }
        }
        if (availableStore.isEmpty()) {
            System.out.println("no product available.");
        } else {
            System.out.println(availableStore);

            sendSlackMessage(availableStore.stream().reduce((s1, s2) -> s1 + ", " + s2).get() + ", url: " + String.format(productUrlTemplate, productCode));
        }
        System.out.println("[Check Main Product] end:" + productCode);
    }

    private void checkRecommend(String productCode) throws IOException {
        System.out.println("[Check Recommend Product] start:" + productCode);
        String url = String.format("https://www.apple.com.cn/shop/pickup-message-recommendations?mt=compact" +
                "&searchNearby=true&store=R401&product=%s", productCode);
        String content = readContent(url);
        Gson gson = new Gson();
        Map<String, Object> body = ((Map) gson.fromJson(content, HashMap.class).get("body"));
        List recommendList = (List) ((Map) body.get("PickupMessage")).get("recommendedProducts");
        if (!recommendList.isEmpty()) {
            System.out.println(recommendList);
            sendSlackMessage(recommendList.stream()
                    .map(s -> "url: " + String.format(productUrlTemplate, s.toString()))
                    .reduce((s1, s2) -> s1.toString() + ", " + s2.toString()).get().toString());
        } else {
            System.out.println("no available recommend products.");
        }
        System.out.println("[Check Recommend] end:" + productCode);
    }

    private String readContent(String url) throws IOException {
        URL url1 = new URL(url);
        HttpURLConnection con = (HttpURLConnection) url1.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");

        int status = con.getResponseCode();
        StringBuffer content = new StringBuffer();
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
        }
//        con.disconnect();
        return content.toString();
    }

//    private void sendMail(String subject, String body) {
//        // Recipient's email ID needs to be mentioned.
//        String to = "chenluo.cn@gmail.com";
//
//        // Sender's email ID needs to be mentioned
//        String from = "chenluo.cn@gmail.com";
//
//        // Used to debug SMTP issues
//        getSession().setDebug(true);
//
//        try {
//            // Create a default MimeMessage object.
//            MimeMessage message = new MimeMessage(session);
//
//            // Set From: header field of the header.
//            message.setFrom(new InternetAddress(from));
//
//            // Set To: header field of the header.
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//
//            // Set Subject: header field
//            message.setSubject(subject);
//
//            // Now set the actual message
//            message.setText(body);
//
//            System.out.println("sending...");
//            // Send message
//            Transport.send(message);
//            System.out.println("Sent message successfully....");
//        } catch (MessagingException mex) {
//            mex.printStackTrace();
//        }
//    }

//    private Session getSession() {
//        if (session == null) {
//            synchronized (this) {
//                if (session != null) {
//                    return session;
//                }
//                // Setup mail server
//                // Get system properties
//                Properties properties = System.getProperties();
//                // Assuming you are sending email from through gmails smtp
//                String host = "smtp.gmail.com";
//                properties.put("mail.smtp.host", host);
////               properties.put("mail.smtp.ssl.trust", host);
//                properties.put("mail.smtp.port", "465");
//                properties.put("mail.smtp.ssl.enable", "true");
//                properties.put("mail.smtp.auth", "true");
////               properties.put("mail.smtp.starttls.enable", "true");
////               properties.put("mail.smtp.socketFactory.port", "465");
////               properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//
//                // Get the Session object.// and pass username and password
//                session = Session.getInstance(properties, new javax.mail.Authenticator() {
//                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
//                        return new javax.mail.PasswordAuthentication("chenluo.cn@gmail.com", "l0u8o1c3h5enGoogle");
//
//                    }
//                });
//                return session;
//            }
//        }
//        return session;
//
//    }
}

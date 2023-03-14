package kafka;

public class KafkaDemoRunner {
    public static void main(String[] args) throws InterruptedException {
        new MyKafkaProducer(1_000_000, 10).run();
        for (int i = 0; i < 9; i++) {
            new MyKafkaConsumer("group_" + i / 3, String.valueOf(i % 3)).run();
        }
    }
}

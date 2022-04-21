package kafka;

public class KafkaExp {
    public static void main(String[] args) {
        new MyKafkaProducer(10, 1).run();
        for (int i = 0; i < 9; i++) {
            new MyKafkaConsumer("group_"+i/3).run();
        }
    }
}

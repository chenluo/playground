import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;

public class FirstSparkApp {

    public static void main(String[] args) {
        String logFile = "/home/chen/spark/spark-3.1.2-bin-hadoop3.2/README.md"; // Should be some file on your system
        SparkSession spark = SparkSession.builder().master("local[16]").appName("Simple Application").getOrCreate();
        Dataset<String> logData = spark.read().textFile(logFile).cache();

        long numAs = logData.filter((FilterFunction<String>) s -> s.contains("a")).count();
        long numBs = logData.filter((FilterFunction<String>) s -> s.contains("b")).count();

        System.out.println("Lines with a: " + numAs + ", lines with b: " + numBs);

        spark.stop();
    }
}

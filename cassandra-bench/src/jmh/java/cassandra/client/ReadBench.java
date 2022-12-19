package cassandra.client;

import com.datastax.driver.core.*;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import org.openjdk.jmh.annotations.*;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 1)
@Threads(1)
@State(Scope.Benchmark)
public class ReadBench {
    public static final String FIELD_PK1 = "pk1";
    public static final String FIELD_PK2 = "pk2";
    public static final String FIELD_CK1 = "ck1";
    public static final String FIELD_CK2 = "ck2";
    public static final String FIELD_VAL1 = "val1";
    public static final String FIELD_VAL2 = "val2";
    public static final String FIELD_VAL3 = "val3";
    private final Random random = new Random(1);
    private Session session;
    private Cluster cluster;

    //    public static void main(String[] args) throws Exception {
    ////        org.openjdk.jmh.Main.main(args);
    //        Options options = new OptionsBuilder()
    //                .include(ReadBench.class.getSimpleName())
    //                .build();
    //        new Runner(options).run();
    //    }

    @Setup(Level.Trial)
    public void init() {
        PoolingOptions poolingOptions = new PoolingOptions();
        poolingOptions.setConnectionsPerHost(HostDistance.LOCAL, 1, 1);
        poolingOptions.setConnectionsPerHost(HostDistance.REMOTE, 1, 1);
        poolingOptions.setMaxRequestsPerConnection(HostDistance.REMOTE, 32000);
        poolingOptions.setMaxRequestsPerConnection(HostDistance.LOCAL, 32000);

        cluster = Cluster.builder().withPoolingOptions(poolingOptions)
//                .addContactPoint("192.168.50.42") // pc
//                .addContactPoint("192.168.50.90") // mac
                .addContactPoint("localhost")
                .build();
        session = cluster.newSession();
    }

    public void createSampleData() {
        createKeyspace();
        createTable();

        for (int i = 0; i < 100; i++) {
            Insert insert = QueryBuilder.insertInto("sampledb", "sampletable")
                    .value(FIELD_PK1, UUID.randomUUID().toString())
                    .value(FIELD_PK2, UUID.randomUUID().toString())
                    .value(FIELD_CK1, random.nextInt()).value(FIELD_CK2, random.nextInt())
                    .value(FIELD_VAL1, nextRandomLocCode()).value(FIELD_VAL2, nextRandomLocCode())
                    .value(FIELD_VAL3, nextRandomLocCode());

            ResultSet execute = session.execute(insert);
            System.out.println(execute);
        }
    }

    private ResultSet createTable() {
        return session.execute(
                "create table if not exists sampledb.sampletable ( pk1   text, pk2   text, ck1   " +
                        "int, ck2   int, val1 text, val2 text, val3 text, primary key ((pk1, pk2)" +
                        ", ck1, ck2) );");
    }

    private ResultSet createKeyspace() {
        return session.execute(
                "create keyspace if not exists sampledb with replication = {'class': 'org.apache" +
                        ".cassandra.locator.SimpleStrategy', 'replication_factor': '1'};");
    }

    @Benchmark
    public void write() {
        Insert insert = QueryBuilder.insertInto("sampledb", "sampletable")
                .value(FIELD_PK1, nextRandomLocCode()).value(FIELD_PK2, nextRandomLocCode())
                .value(FIELD_CK1, random.nextInt()).value(FIELD_CK2, random.nextInt())
                .value(FIELD_VAL1, nextRandomLocCode()).value(FIELD_VAL2, nextRandomLocCode())
                .value(FIELD_VAL3, nextRandomLocCode());
        ResultSet execute = session.execute(insert);

    }

    @TearDown
    public void close() {
        session.close();
        cluster.close();
    }


    @Benchmark
    public void read() {
        Select.Where query =
                QueryBuilder.select(FIELD_PK1, FIELD_PK2, FIELD_CK1, FIELD_CK2, FIELD_VAL1,
                                FIELD_VAL2, FIELD_VAL3).from("sampledb", "sampletable")
                        .where(QueryBuilder.eq(FIELD_PK1, nextRandomLocCode()))
                        .and(QueryBuilder.eq(FIELD_PK2, nextRandomLocCode()));
        ResultSet execute = session.execute(query);
        for (Row row : execute) {
            row.getString(0);
        }
    }

    private String nextRandomLocCode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            char c = (char) ('A' + random.nextInt(26));
            sb.append(c);
        }
        return sb.toString();
    }
}

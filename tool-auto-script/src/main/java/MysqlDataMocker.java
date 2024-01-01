import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import mapper.StockMapper;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class MysqlDataMocker {
    private static String TABLE_DDL_TEMPLATE =
            "CREATE TABLE IF NOT EXISTS `stock_tbl_%s` (\n" + "  `sku` int NOT NULL,\n" +
                    "  `location` varchar(64) NOT NULL DEFAULT 'UNDEFINED',\n" +
                    "  `stock` int DEFAULT NULL,\n" +
                    "  `update_timestamp` timestamp NULL DEFAULT NULL,\n" +
                    "  `update_datetime` datetime DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`sku`,`location`),\n" +
                    "  UNIQUE KEY `search_by_sku_location` (`sku`,`location`),\n" +
                    "  KEY `search_by_location` (`location`),\n" +
                    "  KEY `search_by_sku` (`sku`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci\n" + "\n";
    private static String TABLE_TRUNCATE_TEMPLATE = " truncate table test_db.stock_tbl_%s;";
    private static SqlSessionFactory factory;

    static {
        DataSource dataSource = HikariWrapDataSource.ds;
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        Properties properties = new Properties();
        properties.put("mapUnderscoreToCamelCase", true);
        configuration.setVariables(properties);
        configuration.addMapper(StockMapper.class);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        factory = sqlSessionFactory;
    }

    Random random = new Random();

    public static void main(String[] args) throws SQLException {
        int cnt = 1_000_000_000;
        new MysqlDataMocker().createTableWithRowCount(cnt);
        new MysqlDataMocker().insert(cnt);
        //        List<StockDto> stockDtoList = new ArrayList<>();
        //        try (Connection connection = HikariWrapDataSource.getConnection()) {
        //            PreparedStatement preparedStatement =
        //                    connection.prepareStatement("select * from stock_tbl;");
        //            ResultSet resultSet = preparedStatement.executeQuery();
        //            while (resultSet.next()) {
        //                stockDtoList.add(toDto(resultSet, StockDto.class));
        //            }
        //        }
        //        stockDtoList.forEach(System.out::println);
        //        try (SqlSession sqlSession = factory.openSession()) {
        //            StockMapper mapper = sqlSession.getMapper(StockMapper.class);
        //            mapper.selectAll().forEach(System.out::println);
        //        } catch (Exception e) {
        //            e.printStackTrace();
        //        }
    }

    private static <T> T toDto(ResultSet rs, Class<T> clazz) {
        try {
            T t = clazz.getDeclaredConstructor().newInstance();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                field.set(t, rs.getObject(camelToSnake(field.getName()), field.getType()));
                field.setAccessible(false);
            }
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String camelToSnake(String str) {
        // Regular Expression
        String regex = "([a-z])([A-Z]+)";

        // Replacement string
        String replacement = "$1_$2";

        // Replace the given regex
        // with replacement string
        // and convert it to lower case.
        str = str.replaceAll(regex, replacement).toLowerCase();

        // return string
        return str;
    }

    private boolean createTableWithRowCount(int rowCount) throws SQLException {
        System.out.println("create table:");
        String ddl = String.format(TABLE_DDL_TEMPLATE, String.valueOf(rowCount));
        System.out.println(ddl);
        try (Connection connection = HikariWrapDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(ddl);
            boolean success = preparedStatement.execute();
            if (success) {
                //            connection.prepareStatement(String.format(TABLE_TRUNCATE_TEMPLATE,
                //            rowCount));
            }
        }

        return true;
    }

    private boolean insert(int rowCount) throws SQLException {
        int batchSize = 1_000;
        List<CompletableFuture<?>> insertFutures = new ArrayList<>();
        for (int i = 0; i < rowCount / batchSize; i++) {

            CompletableFuture<Void> insertFuture = CompletableFuture.runAsync(() -> {
                try (Connection connection = HikariWrapDataSource.getConnection()) {
                    connection.setAutoCommit(false);
                    connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                    PreparedStatement selectPs = connection.prepareStatement(
                            "select 1 from stock_tbl_" + rowCount + " where sku=?" +
                                    " and localtion=?;");
                    PreparedStatement insertPs = connection.prepareStatement(
                            "insert into stock_tbl_" + rowCount + " values (?, ?, ?, ?, ?);");
                    PreparedStatement updatePs = connection.prepareStatement(
                            "update stock_tbl_" + rowCount + " set stock = stock+1," +
                                    " update_timestamp=?," + " update_datetime=? where sku=?  and" +
                                    " location=?;");
                    executeUpdateOrInsert(rowCount, batchSize, connection, insertPs, updatePs);
                    connection.commit();
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            insertFutures.add(insertFuture);
        }
        insertFutures.forEach(cf -> {
            try {
                cf.get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
        return true;
    }

    private void executeUpdateOrInsert(int rowCount, int batchSize, Connection connection,
                                       PreparedStatement insertPs, PreparedStatement updatePs)
            throws SQLException {
        for (int j = 0; j < batchSize; j++) {
            try {
                int sku = random.nextInt(10000);
                String location = nextRandomLocCode();
                LocalDateTime now = LocalDateTime.now();
                Timestamp ts = Timestamp.valueOf(now);
                LocalDate ld = now.toLocalDate();
                updatePs.setTimestamp(1, ts);
                updatePs.setDate(2, Date.valueOf(ld));
                updatePs.setInt(3, sku);
                updatePs.setString(4, location);
                int updated = updatePs.executeUpdate();
                boolean inserted = false;
                if (updated == 0) {
                    // insert new sku
                    insertPs.setInt(1, sku);
                    insertPs.setString(2, location);
                    insertPs.setInt(3, 1);
                    insertPs.setTimestamp(4, ts);
                    insertPs.setDate(5, Date.valueOf(ld));
                    inserted = insertPs.execute();
                }
                //                System.out.println("[" + i + "]: updated:" +
                //                updated + ".
                //                inserted:" + inserted);

                //                if (j % 50 == 0) {
                //                    if (j % 10_000 == 0) {
                //                        System.out.println(
                //                                "[" + Thread.currentThread().getName() + "]
                //                                finish " +
                //                                        j + " inserts");
                //                    }
                //                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    private static class HikariWrapDataSource {

        private static HikariConfig config = new HikariConfig();
        private static HikariDataSource ds;

        static {
            config.setJdbcUrl("jdbc:mysql://192.168.50.42:3306/test_db");
            config.setUsername("chen");
            // config.setPassword( "password" );
            config.setPassword("HPaADeEl6K6Mo+ib");
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            config.setMaximumPoolSize(100);
            ds = new HikariDataSource(config);
        }

        private HikariWrapDataSource() {
        }

        public static Connection getConnection() throws SQLException {
            return ds.getConnection();
        }

        public static DataSource getDataSouece() {
            return ds;
        }
    }
}

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dto.StockDto;
import mapper.StockMapper;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MysqlDataMocker {
    private static String TABLE_DDL_TEMPLATE = "CREATE TABLE `stock_tbl_%s` (\n" +
            "  `sku` int NOT NULL,\n" +
            "  `location` varchar(64) NOT NULL DEFAULT 'UNDEFINED',\n" +
            "  `stock` int DEFAULT NULL,\n" +
            "  `update_timestamp` timestamp NULL DEFAULT NULL,\n" +
            "  `update_datetime` datetime DEFAULT NULL,\n" +
            "  PRIMARY KEY (`sku`,`location`),\n" +
            "  UNIQUE KEY `search_by_sku_location` (`sku`,`location`),\n" +
            "  KEY `search_by_location` (`location`),\n" +
            "  KEY `search_by_sku` (`sku`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci\n" +
            "\n";
    private static String TABLE_TRUNCATE_TEMPLATE = " truncate table test_db.stock_tbl_%s;";
    private static SqlSessionFactory factory;

    static {
        DataSource dataSource = HikariWrapDataSource.ds;
        TransactionFactory transactionFactory =
                new JdbcTransactionFactory();
        Environment environment =
                new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        Properties properties = new Properties();
        properties.put("mapUnderscoreToCamelCase", true);
        configuration.setVariables(properties);
        configuration.addMapper(StockMapper.class);
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(configuration);
        factory = sqlSessionFactory;
    }

    public static void main(String[] args) throws SQLException {
        List<StockDto> stockDtoList = new ArrayList<>();
        try ( Connection connection = HikariWrapDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from stock_tbl;");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                stockDtoList.add(toDto(resultSet, StockDto.class));
            }
        }
        stockDtoList.forEach(System.out::println);
        try (SqlSession sqlSession = factory.openSession()){
            StockMapper mapper = sqlSession.getMapper(StockMapper.class);
            mapper.selectAll().forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean createTableWithRowCount(int rowCount) throws SQLException {
        System.out.println("create table:");
        String ddl = String.format(TABLE_DDL_TEMPLATE, String.valueOf(rowCount));
        System.out.println(ddl);
        Connection connection = HikariWrapDataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(ddl);
        boolean success = preparedStatement.execute();
        if (success) {
//            connection.prepareStatement(String.format(TABLE_TRUNCATE_TEMPLATE, rowCount));
        }

        return true;
    }

    private static <T> T toDto(ResultSet rs, Class<T> clazz) {
        try {
            T t = clazz.getDeclaredConstructor().newInstance();
            for (Field field: clazz.getDeclaredFields()) {
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

    private static class HikariWrapDataSource {

        private static HikariConfig config = new HikariConfig();
        private static HikariDataSource ds;

        static {
            config.setJdbcUrl( "jdbc:mysql://192.168.50.42:3306/test_db" );
            config.setUsername( "chen" );
            config.setPassword( "password" );
            config.addDataSourceProperty( "cachePrepStmts" , "true" );
            config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
            config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
            ds = new HikariDataSource( config );
        }

        private HikariWrapDataSource() {}

        public static Connection getConnection() throws SQLException {
            return ds.getConnection();
        }
        public static DataSource getDataSouece() {
            return ds;
        }
    }
    public static String camelToSnake(String str)
    {
        // Regular Expression
        String regex = "([a-z])([A-Z]+)";

        // Replacement string
        String replacement = "$1_$2";

        // Replace the given regex
        // with replacement string
        // and convert it to lower case.
        str = str
                .replaceAll(
                        regex, replacement)
                .toLowerCase();

        // return string
        return str;
    }
}


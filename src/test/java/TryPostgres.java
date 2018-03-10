import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.AfterClass;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class TryPostgres{

    private PostgreSQLContainer postgres = new PostgreSQLContainer();

    @BeforeMethod
    public void init(){
        postgres.start();
    }

    @AfterMethod
    public void destroy(){
        postgres.stop();
    }


    @Test(invocationCount = 10)
    public void testPostgres() throws SQLException {
        DataSource ds = new DriverManagerDataSource(postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword());
        QueryRunner qr = new QueryRunner(ds);
        Integer res = qr.query("select 123", new ScalarHandler<Integer>());
        Assert.assertEquals(res.intValue(), 123);
    }

}
package tk.fishfish.influxdb;

import lombok.extern.slf4j.Slf4j;
import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @author 奔波儿灞
 * @since 1.0.0
 */
@Slf4j
@RunWith(SpringRunner.class)
public class InfluxdbTest {

    private InfluxDB influxDB;

    private static final String DB_NAME = "NOAA_water_database";

    @Before
    public void setup() {
        // Create an object to handle the communication with InfluxDB.
        // (best practice tip: reuse the 'influxDB' instance when possible)
        final String serverURL = "http://127.0.0.1:8086", username = "root", password = "root";
        influxDB = InfluxDBFactory.connect(serverURL, username, password);
    }

    @After
    public void cleanup() {
        influxDB.close();
    }

    @Test
    public void createDatabase() {
        // Create a database...
        // https://docs.influxdata.com/influxdb/v1.7/query_language/database_management/
        influxDB.query(new Query("CREATE DATABASE " + DB_NAME));
    }

    @Test
    public void write() {
        influxDB.setDatabase(DB_NAME);
        // Enable batch writes to get better performance.
        influxDB.enableBatch(BatchOptions.DEFAULTS);
        // Write points to InfluxDB.
        influxDB.write(Point.measurement("h2o_feet")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .tag("location", "santa_monica")
                .addField("level description", "below 3 feet")
                .addField("water_level", 2.064d)
                .build());

        influxDB.write(Point.measurement("h2o_feet")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .tag("location", "coyote_creek")
                .addField("level description", "between 6 and 9 feet")
                .addField("water_level", 8.12d)
                .build());
    }

    @Test
    public void query() {
        influxDB.setDatabase(DB_NAME);
        // Query your data using InfluxQL.
        // https://docs.influxdata.com/influxdb/v1.7/query_language/data_exploration/#the-basic-select-statement
        QueryResult queryResult = influxDB.query(new Query("SELECT * FROM h2o_feet"));
        log.info("queryResult: {}", queryResult.getResults());
    }

}

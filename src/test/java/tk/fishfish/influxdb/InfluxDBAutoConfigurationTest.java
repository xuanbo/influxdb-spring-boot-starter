package tk.fishfish.influxdb;

import lombok.extern.slf4j.Slf4j;
import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.fishfish.influxdb.autoconfigure.InfluxDBAutoConfiguration;

import java.util.concurrent.TimeUnit;

/**
 * InfluxDB测试
 *
 * @author 奔波儿灞
 * @since 1.0.0
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InfluxDBAutoConfiguration.class)
public class InfluxDBAutoConfigurationTest {

    @Autowired
    private InfluxDB influxdb;

    @Test
    public void write() {
        // Write points to InfluxDB.
        influxdb.write(Point.measurement("h2o_feet")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .tag("location", "santa_monica")
                .addField("level description", "below 3 feet")
                .addField("water_level", 2.064d)
                .build());

        influxdb.write(Point.measurement("h2o_feet")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .tag("location", "coyote_creek")
                .addField("level description", "between 6 and 9 feet")
                .addField("water_level", 8.12d)
                .build());
    }

    @Test
    public void query() {
        // Query your data using InfluxQL.
        // https://docs.influxdata.com/influxdb/v1.7/query_language/data_exploration/#the-basic-select-statement
        QueryResult queryResult = influxdb.query(new Query("SELECT * FROM h2o_feet"));
        log.info("queryResult: {}", queryResult.getResults());
    }

}

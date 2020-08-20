# influxdb-spring-boot-starter

>  A lower level influxdb api for spring boot

## dependency

```xml
<dependency>
    <groupId>tk.fishfish</groupId>
    <artifactId>influxdb-spring-boot-starter</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

## usage

### configuration

```yaml
spring:
  influxdb:
    server: http://127.0.0.1:8086
    username: root
    password: root
    database: NOAA_water_database
    enableBatch: true
    batch:
      actions: 1000
      flushDuration: 1000
      jitterDuration: 1000
      bufferLimit: 1000
```

### test

```java
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

```

package tk.fishfish.influxdb.autoconfigure;

import lombok.RequiredArgsConstructor;
import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * InfluxDB自动配置
 *
 * @author 奔波儿灞
 * @since 1.0.0
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(InfluxDBProperties.class)
public class InfluxDBAutoConfiguration {

    private final InfluxDBProperties properties;

    @Bean(destroyMethod = "close")
    public InfluxDB influxdb() {
        InfluxDB influxdb = InfluxDBFactory.connect(properties.getServer(), properties.getUsername(), properties.getPassword());
        influxdb.setDatabase(properties.getDatabase());
        Boolean enableBatch = properties.getEnableBatch();
        InfluxDBProperties.Batch batch = properties.getBatch();
        if (enableBatch != null && enableBatch) {
            BatchOptions options = BatchOptions.DEFAULTS
                    .actions(properties.getBatch().getActions())
                    .flushDuration(properties.getBatch().getFlushDuration())
                    .jitterDuration(properties.getBatch().getJitterDuration())
                    .bufferLimit(properties.getBatch().getBufferLimit())
                    .precision(properties.getBatch().getPrecision());
            influxdb.enableBatch(options);
        }
        return influxdb;
    }

}

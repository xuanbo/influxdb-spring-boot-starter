package tk.fishfish.influxdb.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.concurrent.TimeUnit;

/**
 * InfluxDB属性配置
 *
 * @author 奔波儿灞
 * @since 1.0.0
 */
@Data
@ConfigurationProperties("spring.influxdb")
public class InfluxDBProperties {

    /**
     * 连接地址
     */
    @NotBlank
    private String server = "http://127.0.0.1:8086";

    /**
     * 用户名
     */
    @NotBlank
    private String username = "root";

    /**
     * 密码
     */
    @NotBlank
    private String password = "root";

    /**
     * 数据库
     */
    @NotBlank
    private String database;

    /**
     * 是否开启批量
     */
    @NotNull
    private Boolean enableBatch = true;

    @Valid
    @NotNull
    private Batch batch;

    @Data
    public static class Batch {

        public static final int DEFAULT_BATCH_ACTIONS_LIMIT = 1000;
        public static final int DEFAULT_BATCH_INTERVAL_DURATION = 1000;
        public static final int DEFAULT_JITTER_INTERVAL_DURATION = 0;
        public static final int DEFAULT_BUFFER_LIMIT = 10000;
        public static final TimeUnit DEFAULT_PRECISION = TimeUnit.NANOSECONDS;

        /**
         * the number of actions to collect
         */
        @NotNull
        private Integer actions = DEFAULT_BATCH_ACTIONS_LIMIT;

        /**
         * the time to wait at most (milliseconds).
         */
        @NotNull
        private Integer flushDuration = DEFAULT_BATCH_INTERVAL_DURATION;

        /**
         * the batch flush interval by a random amount
         */
        @NotNull
        private Integer jitterDuration = DEFAULT_JITTER_INTERVAL_DURATION;

        /**
         * maximum number of points stored in the retry buffer
         */
        @NotNull
        private Integer bufferLimit = DEFAULT_BUFFER_LIMIT;

        /**
         * sets the precision to use
         */
        @NotNull
        private TimeUnit precision = DEFAULT_PRECISION;

    }

}

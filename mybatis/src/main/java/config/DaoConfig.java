package config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.shardingsphere.api.config.encrypt.EncryptRuleConfiguration;
import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.ComplexShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.ShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.core.strategy.keygen.SnowflakeShardingKeyGenerator;
import org.apache.shardingsphere.core.strategy.route.ShardingStrategyFactory;
import org.apache.shardingsphere.core.strategy.route.complex.ComplexShardingStrategy;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import strategy.complexStrategy;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

@Configuration
@PropertySource("classpath:dataSource.properties")
@MapperScan(value = {"simple.repostory"})
public class DaoConfig {
    @Value("${userName}")
    private String userName;
    @Value("${passWord}")
    private String passWord;
    @Value("${connectionProperties}")
    private String connectionProperties;
    @Value("${driver}")
    private String driver;

    @Value("${url0}")
    private String url0;
    @Value("${url1}")
    private String url1;
    @Value("${url2}")
    private String url2;
    @Value("${url3}")
    private String url3;

    private DataSource gends(String url) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUsername(userName);
        dataSource.setPassword(passWord);
        dataSource.setConnectionProperties(connectionProperties);
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        return dataSource;
    }

    @Bean
    @Scope("singleton")
    public Map<String, DataSource> dataSourceMap() {
        final Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("ds_m0", gends(url0));
        dataSourceMap.put("ds_s0", gends(url1));
        dataSourceMap.put("ds_m1", gends(url2));
        dataSourceMap.put("ds_s1", gends(url3));
        return dataSourceMap;
    }

    @Bean
    @Scope("singleton")
    public Collection<MasterSlaveRuleConfiguration> config_ms() {
        Collection<MasterSlaveRuleConfiguration> collection = new LinkedList<>();
        collection.add(new MasterSlaveRuleConfiguration(
                "ds_ms0",
                "ds_m0",
                Collections.singletonList("ds_s0")
        ));
        collection.add(new MasterSlaveRuleConfiguration(
                "ds_ms1",
                "ds_m1",
                Collections.singletonList("ds_s1")
        ));
        return collection;
    }

    @Bean
    @Scope("singleton")
    public Collection<TableRuleConfiguration> config_table() {
        Collection<TableRuleConfiguration> collection = new LinkedList<>();
        return collection;
    }

    @Bean
    @Scope("singleton")
    public EncryptRuleConfiguration config_encrypt() {
        EncryptRuleConfiguration configuration = new EncryptRuleConfiguration();
        return configuration;
    }

    @Bean
    @Scope("singleton")
    public KeyGeneratorConfiguration config_keygen(){
        return new KeyGeneratorConfiguration("SNOWFLAKE","id");
    }

    @Bean
    @Scope("singleton")
    public ShardingStrategyConfiguration config_dbSharding(){
        return new InlineShardingStrategyConfiguration("id","");
    }

    @Bean
    @Scope("singleton")
    public ShardingStrategyConfiguration config_tbSharding(){
        return new InlineShardingStrategyConfiguration("id","");
    }

    @Bean
    public DataSource ds() throws SQLException {
        // 配置分片规则
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();

        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(config_dbSharding());
        shardingRuleConfig.setDefaultTableShardingStrategyConfig(config_tbSharding());
        shardingRuleConfig.setDefaultKeyGeneratorConfig(config_keygen());

        shardingRuleConfig.setMasterSlaveRuleConfigs(config_ms());
        shardingRuleConfig.setTableRuleConfigs(config_table());
        shardingRuleConfig.setEncryptRuleConfig(config_encrypt());

        return ShardingDataSourceFactory.createDataSource(dataSourceMap(), shardingRuleConfig, new Properties());

//        RegistryCenterConfiguration regConfig = new RegistryCenterConfiguration("zookeeper");
//        regConfig.setServerLists("localhost:2181");
//        regConfig.setNamespace("sharding-sphere-orchestration");
//
//        // Configure orchestration
//        OrchestrationConfiguration orchConfig = new OrchestrationConfiguration("orchestration-sharding-data-source", regConfig, false);
//        return OrchestrationShardingDataSourceFactory.createDataSource(dataSourceMap(), shardingRuleConfig, new Properties(), orchConfig);
    }

}
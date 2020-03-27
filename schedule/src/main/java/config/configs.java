package config;

import jobs.job_test01;
import org.apache.commons.dbcp2.BasicDataSource;
import org.quartz.*;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;

@Configuration
@EnableScheduling
public class configs {
    @Bean
    @Scope("singleton")
    public JobDetail jobDetail_test01() {
        return JobBuilder.newJob(job_test01.class)
                .storeDurably()
                .requestRecovery()
                .withIdentity("job_name_001", "job_group_001")
                .withDescription("job_name001_group001")
                .build();
    }

    /**
     * 位置 时间域  允许值   特殊值
     * 1    秒      0-59 	, - * /
     * 2    分      0-59 	, - * /
     * 3    时   	0-23 	, - * /
     * 4    天      1-31    , - * ? / L W C
     * 5    月 	    1-12 	, - * /
     * 6    周	    1-7     , - * ? / L C #
     * 7    年 	    1-31 	, - * /
     * 星号()：可用在所有字段中，表示对应时间域的每一个时刻
     * 问号（?）：该字符只在日期和星期字段中使用，它通常指定为“无意义的值”，相当于点位符；
     * 减号(-)：表达一个范围
     * 逗号(,)：表达一个列表值
     * 斜杠(/)：x/y表达一个等步长序列，x为起始值，y为增量步长值。
     * L：（日期和星期字段）最后一天
     * W：（日期字段）最近的工作日
     * C：（日期和星期字段）计划所关联的日期
     * @return
     */
    @Bean
    @Scope("singleton")
    public Trigger trigger_test01() {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail_test01())
                .withIdentity("trigger_name_001", "trigger_group_001")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?"))
                .build();
    }

    @Bean
    @Scope("singleton")
    @QuartzDataSource
    @ConfigurationProperties(prefix = "spring.quartz.properties.org.quartz.datasource")
    DataSource quartzDataSource() {
        return new BasicDataSource();
    }
}

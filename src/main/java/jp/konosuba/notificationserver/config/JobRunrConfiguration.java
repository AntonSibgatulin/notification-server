package jp.konosuba.notificationserver.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.jobrunr.configuration.JobRunr;
import org.jobrunr.jobs.mappers.JobMapper;
import org.jobrunr.scheduling.JobScheduler;
import org.jobrunr.storage.InMemoryStorageProvider;
import org.jobrunr.storage.StorageProvider;
import org.jobrunr.utils.mapper.jackson.JacksonJsonMapper;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

@SpringBootConfiguration
@Configuration
public class JobRunrConfiguration {


    @Bean
    public JobMapper jobMapper() {
        return new JobMapper(new JacksonJsonMapper());
    }

    @Bean
    @DependsOn("jobMapper")
    public StorageProvider storageProvider(JobMapper jobMapper) {
        InMemoryStorageProvider storageProvider = new InMemoryStorageProvider();
        storageProvider.setJobMapper(jobMapper);
        return storageProvider;
    }
/*
    @Bean
    @DependsOn("storageProvider")
    public JobScheduler jobScheduler(StorageProvider storageProvider, ApplicationContext applicationContext) {
        return JobRunr.configure().useStorageProvider(storageProvider)
                .useJobActivator(applicationContext::getBean)
                .useDefaultBackgroundJobServer()
                .useDashboard()
                .useJmxExtensions()
                .initialize();
    }

 */



    /* Postegres
    @Bean
    public DataSource dataSource(){
        PGSimpleDataSource data = new PGSimpleDataSource();
        data.setUrl("jdbc:postgresql://localhost:5432/notification?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        data.setUser("root");
        data.setPassword("Dert869$$");
        return data;
    }

     */



    @Bean
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceBuilder.url("jdbc:mysql://localhost:3306/notification?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("");
        return dataSourceBuilder.build();
    }

}

package com.example.quartz.scheduler;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import com.example.quartz.config.AutoWiringSpringBeanJobFactory;
import com.example.quartz.jobs.SampleJobOne;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.*;

@Configuration
@ConditionalOnExpression("'${using.spring.schedulerFactory}'=='false'")
public class QuartzScheduler {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        logger.info("Hello world from Quartz...");
    }

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
        logger.debug("Configuring Job factory");

        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public Scheduler scheduler(Trigger trigger, JobDetail job,
                               SchedulerFactoryBean factory, DataSource quartzDataSource) throws SchedulerException {
        logger.debug("Getting a handle to the Scheduler");
        factory.setDataSource(quartzDataSource);
        Scheduler scheduler = factory.getScheduler();
        scheduler.scheduleJob(job, trigger);

        logger.debug("Starting Scheduler threads");
        scheduler.start();
        return scheduler;
    }

    //***********

//    @Bean
//    public Scheduler scheduler(Map<String, JobDetail> jobMap, Set<?
//            extends Trigger> triggers, SchedulerFactoryBean factory) throws SchedulerException,
//            IOException {
//
////        StdSchedulerFactory factory = new StdSchedulerFactory();
////        factory.initialize(new ClassPathResource("quartz.properties").getInputStream());
//
//        logger.debug("Getting a handle to the Scheduler");
//        Scheduler scheduler = factory.getScheduler();
//        scheduler.setJobFactory(springBeanJobFactory());
////        Map<JobDetail, Set<? extends Trigger>> triggersAndJobs =
////                new HashMap<JobDetail, Set<? extends Trigger>>();
//        Map<JobDetail, Set<? extends Trigger>> triggersAndJobs =
//                new HashMap<>();
//
//        for (JobDetail jobDetail : jobMap.values()) {
//            for (Trigger trigger : triggers) {
//                System.out.println(trigger.getJobKey() + "   " + jobDetail.getKey());
//                if (trigger.getJobKey().equals(jobDetail.getKey())) {
//                    Set<Trigger> set = new HashSet<>();
//                    set.add(trigger);
//                    triggersAndJobs.put(jobDetail, set);
//                }
//            }
//        }
//        scheduler.scheduleJobs(triggersAndJobs, false);
//
//        logger.debug("Starting Scheduler threads");
//        scheduler.start();
//        return scheduler;
//    }


    //***********


    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobFactory(springBeanJobFactory());
        factory.setQuartzProperties(quartzProperties());
        return factory;
    }

    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    @Bean(name = "jobOne")
    public JobDetail jobDetailOne() {

        return newJob().ofType(SampleJobOne.class).storeDurably().withIdentity(JobKey.jobKey("Qrtz_Job_Detail")).withDescription("Invoke Sample Job service...").build();
    }

    @Bean
    public Trigger triggerOne(@Qualifier("jobOne") JobDetail job) {

        int frequencyInSec = 10;
        logger.info("Configuring trigger to fire every {} seconds", frequencyInSec);

        return newTrigger().forJob(job).withIdentity(TriggerKey.triggerKey("Qrtz_Trigger")).withDescription("Sample trigger").withSchedule(simpleSchedule().withIntervalInSeconds(frequencyInSec).repeatForever()).build();
    }

    @Bean
    @QuartzDataSource
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource quartzDataSource() {
        return DataSourceBuilder.create().build();
    }
}

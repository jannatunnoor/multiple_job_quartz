package com.example.quartz.scheduler;

import com.example.quartz.service.SampleJobService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SampleJobTwo implements Job {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SampleJobService jobService;

    @Override
    public void execute(JobExecutionContext context) {

        logger.info("JobTwo ** {} ** fired @ {}",
                context.getJobDetail().getKey().getName(), context.getFireTime());

        jobService.executeSampleJob();

        logger.info("Next jobTwo scheduled @ {}", context.getNextFireTime());
    }
}

package com.github.khangzxrr.config;

import com.github.khangzxrr.service.job.RequestExpiredJob;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.TriggerBuilder;
import org.quartz.impl.JobDetailImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

@Configuration
public class QuartzRequestExpiredTimeConfiguration {

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();

        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        schedulerFactoryBean.setJobFactory(jobFactory);
        schedulerFactoryBean.setTriggers(trigger());
        schedulerFactoryBean.setJobDetails(jobDetail());

        return schedulerFactoryBean;
    }

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        return new AutowiringSpringBeanJobFactory();
    }

    @Bean
    public JobDetail jobDetail() {
        JobDetailImpl jobDetail = new JobDetailImpl();
        jobDetail.setKey(new JobKey(RequestExpiredJob.name));
        jobDetail.setJobClass(RequestExpiredJob.class);
        jobDetail.setDurability(true);

        return jobDetail;
    }

    @Bean
    public CronTrigger trigger() {
        return TriggerBuilder
            .newTrigger()
            .forJob(jobDetail())
            .withIdentity(RequestExpiredJob.name, RequestExpiredJob.group)
            .startNow()
            .withSchedule(CronScheduleBuilder.cronSchedule("0/10 0/1 0 ? * * *")) //        0 0 0 * * ?
            .build();
    }
}

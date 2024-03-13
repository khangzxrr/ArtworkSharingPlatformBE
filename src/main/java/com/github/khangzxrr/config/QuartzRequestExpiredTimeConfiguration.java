package com.github.khangzxrr.config;

import com.github.khangzxrr.service.job.CleanupExpiredArtworkSellingAuction;
import com.github.khangzxrr.service.job.RequestExpiredFirstPaymentJob;
import com.github.khangzxrr.service.job.RequestExpiredJob;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
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
        schedulerFactoryBean.setTriggers(requestExpiredTrigger(), requestFirstPaymentExpiredTrigger(), cleanUpExpiredAuctionTrigger());
        schedulerFactoryBean.setJobDetails(
            requestExpiredJobDetail(),
            requestFirstPaymentExpiredJobDetail(),
            cleanUpExpiredAuctionJobDetail()
        );

        return schedulerFactoryBean;
    }

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        return new AutowiringSpringBeanJobFactory();
    }

    @Bean
    public JobDetail cleanUpExpiredAuctionJobDetail() {
        JobDetailImpl jobDetail = new JobDetailImpl();
        jobDetail.setKey(new JobKey(CleanupExpiredArtworkSellingAuction.name));
        jobDetail.setJobClass(CleanupExpiredArtworkSellingAuction.class);
        jobDetail.setDurability(true);

        return jobDetail;
    }

    @Bean
    public JobDetail requestFirstPaymentExpiredJobDetail() {
        JobDetailImpl jobDetail = new JobDetailImpl();
        jobDetail.setKey(new JobKey(RequestExpiredFirstPaymentJob.name));
        jobDetail.setJobClass(RequestExpiredFirstPaymentJob.class);
        jobDetail.setDurability(true);

        return jobDetail;
    }

    @Bean
    public Trigger cleanUpExpiredAuctionTrigger() {
        return TriggerBuilder
            .newTrigger()
            .forJob(cleanUpExpiredAuctionJobDetail())
            .withIdentity(CleanupExpiredArtworkSellingAuction.name, CleanupExpiredArtworkSellingAuction.group)
            .startNow()
            .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(10))
            .build();
    }

    @Bean
    public Trigger requestFirstPaymentExpiredTrigger() {
        return TriggerBuilder
            .newTrigger()
            .forJob(requestFirstPaymentExpiredJobDetail())
            .withIdentity(RequestExpiredFirstPaymentJob.name, RequestExpiredFirstPaymentJob.group)
            .startNow()
            .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(10))
            //.withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(0, 0))
            .build();
    }

    @Bean
    public JobDetail requestExpiredJobDetail() {
        JobDetailImpl jobDetail = new JobDetailImpl();
        jobDetail.setKey(new JobKey(RequestExpiredJob.name));
        jobDetail.setJobClass(RequestExpiredJob.class);
        jobDetail.setDurability(true);

        return jobDetail;
    }

    @Bean
    public Trigger requestExpiredTrigger() {
        return TriggerBuilder
            .newTrigger()
            .forJob(requestExpiredJobDetail())
            .withIdentity(RequestExpiredJob.name, RequestExpiredJob.group)
            .startNow()
            .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(0, 0))
            .build();
    }
}

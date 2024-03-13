package com.github.khangzxrr.service.job;

import com.github.khangzxrr.service.ArtworkSellingService;
import jakarta.transaction.Transactional;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class CleanupExpiredArtworkSellingAuction extends QuartzJobBean {

    private final Logger log = LoggerFactory.getLogger(RequestExpiredJob.class);

    public static final String name = "CleanupExpiredArtworkSellingAuction";
    public static final String group = "ArtworkSellingGroup";

    @Autowired
    private ArtworkSellingService artworkSellingService;

    public void setArtworkSellingService(ArtworkSellingService artworkSellingService) {
        this.artworkSellingService = artworkSellingService;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("check expired auction...");

        artworkSellingService.cleanUpExpiredAuction();
    }
}

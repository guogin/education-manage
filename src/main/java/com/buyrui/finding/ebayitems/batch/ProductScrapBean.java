package com.buyrui.finding.ebayitems.batch;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



@Profile("batch")
@Component
public class ProductScrapBean {
    private Logger          logger = LoggerFactory.getLogger(this.getClass());

    @Scheduled(cron = "${batch.greeting.cron}")
    public void cronJob() {
        //logger.info("> cronJob");
        //new EbayAppNovaCoders().findGalleryUrl();
        //logger.info("< cronJob");
    }

    @Scheduled(
               initialDelayString = "${batch.greeting.initialdelay}",
               fixedRateString = "${batch.greeting.fixedrate}")
    public void fixedRateJobWithInitialDelay() {
        logger.info("> fixedRateJobWithInitialDelay");

        long pause = 5000;
        long start = System.currentTimeMillis();
        do {
            if (start + pause < System.currentTimeMillis()) {
                break;
            }
        } while (true);

        logger.info("Processing time was {} seconds.", pause / 1000);
        logger.info("< fixedRateJobWithInitialDelay");
    }

    @Scheduled(
               initialDelayString = "${batch.greeting.initialdelay}",
               fixedDelayString = "${batch.greeting.fixeddelay}")
    public void fixedDelayJobWithInitialDelay() {
        logger.info("> fixedDelayJobWithInitialDelay");

        long pause = 5000;
        long start = System.currentTimeMillis();
        do {
            if (start + pause < System.currentTimeMillis()) {
                break;
            }
        } while (true);

        logger.info("Processing time was {} seconds.", pause / 1000);
        logger.info("< fixedDelayJobWithInitialDelay");
    }
}

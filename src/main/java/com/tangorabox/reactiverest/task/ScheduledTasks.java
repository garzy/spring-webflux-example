package com.tangorabox.reactiverest.task;

import com.tangorabox.reactiverest.service.PricesDatabaseUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final Path CSV_PATH = Path.of("prices.csv");

    private final PricesDatabaseUpdater pricesDatabaseUpdater;



    public ScheduledTasks(PricesDatabaseUpdater pricesDatabaseUpdater) {
        this.pricesDatabaseUpdater = pricesDatabaseUpdater;
        assert Files.exists(CSV_PATH) : "The test file must exists";
        assert Files.isRegularFile(CSV_PATH ) : "The test file must be a valid file";
    }


    @Scheduled(cron = "0 0 23 * * *")
    public void updatePricesFromCSVFile() throws IOException {
        log.info("Starting Update Princes From CSV Task...");
        pricesDatabaseUpdater.update(CSV_PATH);
    }
}

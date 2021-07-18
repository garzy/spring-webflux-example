package com.tangorabox.reactiverest;

import com.tangorabox.reactiverest.mappers.PriceMapper;
import com.tangorabox.reactiverest.service.PricesDatabaseUpdater;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class ApplicationConfiguration{

    private static final Logger log = LoggerFactory.getLogger(ApplicationConfiguration.class);

    private static final Path CSV_PATH = Path.of("prices.csv");

    public ApplicationConfiguration() {
        assert Files.exists(CSV_PATH) : "The test file must exists";
        assert Files.isRegularFile(CSV_PATH ) : "The test file must be a valid file";
    }

    @Bean
    public PriceMapper priceMapper() {
        return Mappers.getMapper(PriceMapper.class);
    }

    @Bean
    ApplicationRunner init(PricesDatabaseUpdater pricesDatabaseUpdater, DatabaseClient client) {
        return args -> {
            var initDb = client.sql("create table PRICES" +
                    "(id SERIAL PRIMARY KEY, " +
                    "brand_id long," +
                    "start_date TIMESTAMP, " +
                    "end_date TIMESTAMP, " +
                    "price_list integer, " +
                    "product_id long, " +
                    "priority integer, " +
                    "price double, " +
                    "currency varchar(255), " +
                    "last_update TIMESTAMP, " +
                    "last_update_by varchar(255));");

            initDb.then().doAfterTerminate(() -> {
                try {
                    pricesDatabaseUpdater.update(CSV_PATH);
                } catch (IOException e) {
                    log.error("Database Error", e);
                }
            }).subscribe();
        };
    }
}

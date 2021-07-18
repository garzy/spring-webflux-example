package com.tangorabox.reactiverest.test.util;

import com.tangorabox.reactiverest.entity.Price;
import com.tangorabox.reactiverest.io.CSVFileTest;
import com.tangorabox.reactiverest.model.PriceCSV;

import java.time.LocalDateTime;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class AssertionUtils {

    private AssertionUtils() {
        super();
    }

    public static void assertValuesCSV(Collection<PriceCSV> prices) {
        assertThat(prices).hasSize(CSVFileTest.NUM_ENTRIES_IN_FILE);
        for (PriceCSV price : prices) {
            assertThat(price.getBrandId()).isEqualTo(1);
            assertDate(price.getStartDate());
            assertDate(price.getEndDate());
            assertThat(price.getPriceList()).isIn(1, 2, 3, 4);
            assertThat(price.getProductId()).isEqualTo(35455);
            assertThat(price.getPriority()).isIn(0, 1);
            assertThat(price.getPrice()).isGreaterThan(20);
            assertThat(price.getCurrency()).isEqualTo("EUR");
            assertDate(price.getLastUpdate());
            assertThat(price.getLastUpdateBy()).contains("user");
        }

    }

    private static void assertDate(LocalDateTime dateTime) {
        assertThat(dateTime).isNotNull();
        assertThat(dateTime.getYear()).isEqualTo(2020);
    }

    public static void assertValues(Collection<Price> prices) {
        assertThat(prices).hasSize(CSVFileTest.NUM_ENTRIES_IN_FILE);
        for (Price price : prices) {
            assertThat(price.getBrandId()).isEqualTo(1);
            assertDate(price.getStartDate());
            assertDate(price.getEndDate());
            assertThat(price.getPriceList()).isIn(1, 2, 3, 4);
            assertThat(price.getProductId()).isEqualTo(35455);
            assertThat(price.getPriority()).isIn(0, 1);
            assertThat(price.getPrice()).isGreaterThan(20);
            assertThat(price.getCurrency()).isEqualTo("EUR");
            assertDate(price.getLastUpdate());
            assertThat(price.getLastUpdateBy()).contains("user");
        }

    }
}

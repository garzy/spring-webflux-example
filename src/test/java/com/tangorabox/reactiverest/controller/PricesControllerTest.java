package com.tangorabox.reactiverest.controller;

import com.tangorabox.reactiverest.ApplicationConfigurationTest;
import com.tangorabox.reactiverest.io.CSVReaderOpenCSV;
import com.tangorabox.reactiverest.model.PriceResponse;
import com.tangorabox.reactiverest.service.PricesDatabaseUpdater;
import com.tangorabox.reactiverest.service.PricesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@WebFluxTest()
@ContextConfiguration(classes = {ApplicationConfigurationTest.class,
        PricesController.class, PricesService.class, PricesDatabaseUpdater.class, CSVReaderOpenCSV.class})
public class PricesControllerTest {


    @Autowired
    private ApplicationContext context;

    WebTestClient webTestClient;

    @BeforeEach
    public void setUp() {
        webTestClient = WebTestClient.bindToApplicationContext(context).build();
    }


    @Test
    @DisplayName("Test1: petición a las 10:00 del día 14 del producto 35455 para la brand 1")
    public void test1() throws Exception {
        testEndPointWithDate("2020-06-14T10:00:00", 35.5d);
    }

    @Test
    @DisplayName("Test2: petición a las 16:00 del día 14 del producto 35455 para la brand 1")
    public void test2() {
        testEndPointWithDate("2020-06-14T16:00:00", 25.45d);
    }

    @Test
    @DisplayName("Test3: petición a las 21:00 del día 14 del producto 35455 para la brand 1")
    public void test3() {
        testEndPointWithDate("2020-06-14T21:00:00", 35.5d);
    }

    @Test
    @DisplayName("Test4: petición a las 10:00 del día 15 del producto 35455 para la brand 1")
    public void test4() {
        testEndPointWithDate("2020-06-15T10:00:00", 30.5d);
    }

    @Test
    @DisplayName("Test5: petición a las 21:00 del día 16 del producto 35455 para la brand 1")
    public void test5() {
        testEndPointWithDate("2020-06-16T21:00:00", 38.95d);
    }

    private void testEndPointWithDate(String date, double expectedPrice) {
        webTestClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/prices")
                                .queryParam("date", date)
                                .queryParam("productId", 35455)
                                .queryParam("brandId", 1)
                                .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(PriceResponse.class)
                .value(priceResponse -> assertThat(priceResponse.getFinalPrice()).isEqualTo(expectedPrice));
    }

}

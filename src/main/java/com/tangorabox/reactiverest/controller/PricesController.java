package com.tangorabox.reactiverest.controller;

import com.tangorabox.reactiverest.model.PriceResponse;
import com.tangorabox.reactiverest.service.PricesService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("prices")
public class PricesController {

    private final PricesService pricesService;

    public PricesController(PricesService pricesService) {
        this.pricesService = pricesService;
    }

    @GetMapping
    public Mono<ResponseEntity<PriceResponse>> findPrice(@RequestParam
                                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                         LocalDateTime date,
                                                         @RequestParam long productId,
                                                         @RequestParam long brandId) {
        return pricesService.findCurrentPrice(date, productId, brandId)
                    .map(ResponseEntity::ok)
                    .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}

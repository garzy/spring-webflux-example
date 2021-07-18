package com.tangorabox.reactiverest.service;

import com.tangorabox.reactiverest.mappers.PriceMapper;
import com.tangorabox.reactiverest.model.PriceResponse;
import com.tangorabox.reactiverest.repository.PriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class PricesService {

    private static final Logger log = LoggerFactory.getLogger(PricesService.class);
    private final PriceRepository priceRepository;
    private final PriceMapper priceMapper;

    public PricesService(PriceRepository priceRepository, PriceMapper priceMapper) {
        this.priceRepository = priceRepository;
        this.priceMapper = priceMapper;
    }

    public Mono<PriceResponse> findCurrentPrice(LocalDateTime date, long productId, long brandId) {
        return priceRepository.findAllByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc
                (productId, brandId, date, date)
                .doOnNext(price -> log.debug("Founded Price: {}", price))
                .next()//takes the Price with most higher priority
                .map(priceMapper::toPriceResponse);
    }
}

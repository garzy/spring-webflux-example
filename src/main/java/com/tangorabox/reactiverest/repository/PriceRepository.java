package com.tangorabox.reactiverest.repository;

import com.tangorabox.reactiverest.entity.Price;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public interface PriceRepository extends ReactiveCrudRepository<Price, Integer> {

    Flux<Price> findAllByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(long productId, long brandId, LocalDateTime date, LocalDateTime date2);

}

package com.tangorabox.reactiverest.service;

import com.tangorabox.reactiverest.entity.Price;
import com.tangorabox.reactiverest.io.CSVReactiveReader;
import com.tangorabox.reactiverest.mappers.PriceMapper;
import com.tangorabox.reactiverest.model.PriceCSV;
import com.tangorabox.reactiverest.repository.PriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

@Service
public class PricesDatabaseUpdater {

    private static final Logger log = LoggerFactory.getLogger(PricesDatabaseUpdater.class);


    private final PriceRepository priceRepository;
    private final CSVReactiveReader csvReactiveReader;
    private final PriceMapper priceMapper;


    public PricesDatabaseUpdater(PriceRepository priceRepository,
                                 CSVReactiveReader csvReactiveReader, PriceMapper priceMapper) {
        this.priceRepository = priceRepository;
        this.csvReactiveReader = csvReactiveReader;
        this.priceMapper = priceMapper;
    }

    public void update(Path csvPath) throws IOException {
        log.debug("Starting Update...");

        final Set<Integer> pricesInCSVFileAsSet = new HashSet<>();

        final Flux<Price> pricesInCSVFile = getPricesInCSVFile(csvPath)
                                            .doOnNext(price -> pricesInCSVFileAsSet.add(price.getId()))
                                            .doOnComplete(() -> deletePricesThatAreNotInSet(pricesInCSVFileAsSet))
                                            .doOnComplete(() -> log.debug("End Update"));

        Flux<Price> pricesToUpdateOrInsert = getPricesToUpdateOrInsert(pricesInCSVFile);

        priceRepository.saveAll(pricesToUpdateOrInsert).then().subscribe();
    }

    private Flux<Price> getPricesInCSVFile(Path csvPath) throws IOException {
        return csvReactiveReader
                .readFile(csvPath)
                .zipWith(Flux.range(1, Integer.MAX_VALUE))
                .doOnNext(objects -> log.debug("Incoming PriceCSV: (id:{}) {})", objects.getT2(), objects.getT1()))
                .map(this::updatePriceIDWithRowNumber)
                .doOnNext(price -> log.debug("Mapped Price: {}", price));
    }

    private Price updatePriceIDWithRowNumber(Tuple2<PriceCSV, Integer> objects) {
        Price price = priceMapper.toPrice(objects.getT1());
        price.setId(objects.getT2());
        return price;
    }

    private Flux<Price> getPricesToUpdateOrInsert(Flux<Price> pricesInCSVFile) {
        return pricesInCSVFile.flatMap(csvPrice ->
                      priceRepository
                        .findById(csvPrice.getId())
                        .filter(isNewDataMoreRecent(csvPrice))
                        .map(p -> csvPrice) //change to more recent entity
                        .switchIfEmpty(newEntityForInsert(csvPrice))
                        .doOnNext(p -> log.debug("Price Will Be Saved: {}", p)));
    }

    private void deletePricesThatAreNotInSet(Set<Integer> pricesInCSVFileAsSet) {
        final Flux<Price> pricesToRemove = priceRepository.findAll().filter(notInSet(pricesInCSVFileAsSet));
        priceRepository.deleteAll(pricesToRemove).then().subscribe();
    }

    private static Mono<Price> newEntityForInsert(Price csvPrice) {
        return Mono.just(csvPrice).map(p -> {
            p.setId(null);
            return p;
        });
    }

    private static Predicate<Price> isNewDataMoreRecent(Price csvPrice) {
        return databasePrice -> databasePrice.getLastUpdate().isBefore(csvPrice.getLastUpdate());
    }

    private static Predicate<Price> notInSet(Set<Integer> pricesInCSVFileAsSet) {
        return price -> !pricesInCSVFileAsSet.contains(price.getId());
    }
}

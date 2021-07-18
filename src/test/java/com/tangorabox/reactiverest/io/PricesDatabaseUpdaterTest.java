package com.tangorabox.reactiverest.io;

import com.tangorabox.reactiverest.entity.Price;
import com.tangorabox.reactiverest.mappers.PriceMapper;
import com.tangorabox.reactiverest.repository.PriceRepository;
import com.tangorabox.reactiverest.service.PricesDatabaseUpdater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PricesDatabaseUpdaterTest extends CSVFileTest {

    @Mock
    private PriceRepository priceRepository;

    private CSVReactiveReader csvReactiveReader;

    @BeforeEach
    public void beforeTest() {
        csvReactiveReader = new CSVReaderOpenCSV();
    }

    @Test
    public void test() throws IOException {
        //Given
        PricesDatabaseUpdater updater = new PricesDatabaseUpdater(priceRepository, csvReactiveReader, Mappers.getMapper(PriceMapper.class));
        List<Price> defaultDatabasePrices = generateDatabaseDefaults();

        when(priceRepository.findById(anyInt())).thenReturn(Mono.empty());
        when(priceRepository.findAll()).thenReturn(Flux.fromIterable(defaultDatabasePrices));
        when(priceRepository.deleteAll(any(Publisher.class))).thenReturn(Mono.empty());
        when(priceRepository.saveAll(ArgumentMatchers.<Publisher<Price>>any())).thenAnswer(invocationOnMock -> {
            Publisher<Price> publisher = invocationOnMock.getArgument(0, Publisher.class);
            StepVerifier.create(publisher)
                    .expectNextCount(NUM_ENTRIES_IN_FILE)
                    .verifyComplete();
            return Flux.empty();
        });

        //When
        updater.update(CSV_PATH);

        //Then
        verify(priceRepository,  times(NUM_ENTRIES_IN_FILE)).findById(anyInt());
        verify(priceRepository, atLeastOnce()).saveAll(any(Publisher.class));
        verify(priceRepository, atLeastOnce()).deleteAll(any(Publisher.class));

    }

    private List<Price> generateDatabaseDefaults() {
        List<Price> result = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Price price = new Price();
            price.setId(i);
            result.add(price);
        }
        return result;
    }
}

package com.tangorabox.reactiverest.io;

import com.google.common.collect.Lists;
import com.tangorabox.reactiverest.model.PriceCSV;
import com.tangorabox.reactiverest.test.util.AssertionUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.tangorabox.reactiverest.test.util.AssertionUtils.assertValuesCSV;
import static org.assertj.core.api.Assertions.assertThat;

public class CSVReaderOpenCSVTest extends CSVFileTest {


    @Test
    @DisplayName("Read from CSV as Iterable an checks that all content is well read")
    public void testReadFromIterable() throws IOException {
        //Given
        CSVReaderOpenCSV reader = new CSVReaderOpenCSV();
        //When
        final List<PriceCSV> pricesList = Lists.newArrayList(reader.readFileAsIterable(CSV_PATH));
        //Then
        assertValuesCSV(pricesList);
    }

    @Test
    @DisplayName("Read from CSV as reactive Flux an checks that all content is well read")
    public void testReadFromFlux() throws IOException {
        //Given
        CSVReactiveReader reader = new CSVReaderOpenCSV();
        //When
        final Flux<PriceCSV> priceObservable = reader.readFile(CSV_PATH);

        //Then
        assertThat(priceObservable).isNotNull();
        StepVerifier.create(priceObservable)
                .recordWith(ArrayList::new)
                .expectNextCount(NUM_ENTRIES_IN_FILE)
                .consumeRecordedWith(AssertionUtils::assertValuesCSV)
                .verifyComplete();
    }
}

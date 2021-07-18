package com.tangorabox.reactiverest.io;

import com.google.common.collect.Lists;
import com.tangorabox.reactiverest.entity.Price;
import com.tangorabox.reactiverest.mappers.PriceMapper;
import com.tangorabox.reactiverest.model.PriceCSV;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.tangorabox.reactiverest.test.util.AssertionUtils.assertValues;

public class PriceMapperTest extends CSVFileTest {

    @Test
    public void testMapper() throws IOException {
        //Given
        PriceMapper mapper = Mappers.getMapper(PriceMapper.class);
        CSVReaderOpenCSV csvReaderOpenCSV = new CSVReaderOpenCSV();
        Iterable<PriceCSV> pricesCSV = csvReaderOpenCSV.readFileAsIterable(CSV_PATH);

        //When
        List<Price> pricesConverted = Lists.newArrayList(pricesCSV)
                .stream().map(mapper::toPrice
                ).collect(Collectors.toList());

        //Then
        assertValues(pricesConverted);
    }

}

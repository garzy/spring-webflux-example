package com.tangorabox.reactiverest.io;

import com.opencsv.bean.CsvToBeanBuilder;
import com.tangorabox.reactiverest.model.PriceCSV;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class CSVReaderOpenCSV implements CSVReactiveReader {

    @Override
    public Flux<PriceCSV> readFile(Path pathToFile) throws IOException {
        return Flux.create(emitter ->  {
            try {
                for (PriceCSV priceCSV : readFileAsIterable(pathToFile)) {
                    emitter.next(priceCSV);
                }
                emitter.complete();

            } catch (IOException e) {
                emitter.error(e);
            }
        });
    }

    protected Iterable<PriceCSV> readFileAsIterable(Path pathToFile) throws IOException {
        CsvToBeanBuilder<PriceCSV> beanBuilder = new CsvToBeanBuilder<>(Files.newBufferedReader(pathToFile));
        beanBuilder.withType(PriceCSV.class);
        return beanBuilder.build();
    }
}

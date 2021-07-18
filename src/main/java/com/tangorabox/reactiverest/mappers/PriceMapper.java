package com.tangorabox.reactiverest.mappers;

import com.tangorabox.reactiverest.entity.Price;
import com.tangorabox.reactiverest.model.PriceCSV;
import com.tangorabox.reactiverest.model.PriceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    Price toPrice(PriceCSV dto);

    @Mapping(source = "price", target = "finalPrice")
    PriceResponse toPriceResponse(Price price);
}

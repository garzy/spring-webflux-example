package com.tangorabox.reactiverest.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PriceResponse {

    private long productId;
    private long brandId;
    private int priceList;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private double finalPrice;
}

package com.tangorabox.reactiverest.model;

import com.opencsv.bean.CsvDate;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class PriceCSV {

    private long brandId;
    /** start date in which the indicated rate price applies */
    @CsvDate("yyyy-MM-dd-HH.mm.ss")
    private LocalDateTime startDate;
    /** end date in which the indicated rate price applies */
    @CsvDate("yyyy-MM-dd-HH.mm.ss")
    private LocalDateTime endDate;
    /** Identifier of the applicable price list.*/
    private int priceList;
    /** Product code identifier */
    private long productId;
    /** Pricing enforcer stripper. If two rates coincide in a range of dates, the one with the highest priority (highest numerical value) is applied.*/
    private int priority;
    /** final sale price.*/
    private double price;
    /** Currency ISO Code */
    private String currency;
    /** the last upate date of current row **/
    @CsvDate("yyyy-MM-dd-HH.mm.ss")
    private LocalDateTime lastUpdate;
    /** the user who makes de last update */
    private String lastUpdateBy;
}

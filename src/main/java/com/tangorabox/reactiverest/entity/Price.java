package com.tangorabox.reactiverest.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("PRICES")
public class Price {

    @Id
    private Integer id;

    private long brandId;
    /** start date in which the indicated rate price applies */
    private LocalDateTime startDate;
    /** end date in which the indicated rate price applies */
    private LocalDateTime endDate;
    /** Identifier of the applicable price list.*/
    private int priceList;
    /** Product code identifier */
    private long productId;
    /** Pricing enforcer stripper. If two rates coincide in a range of dates,
     * the one with the highest priority (highest numerical value) is applied.*/
    private int priority;
    /** final sale price.*/
    private double price;
    /** Currency ISO Code */
    private String currency;
    private LocalDateTime lastUpdate;
    private String lastUpdateBy;
}

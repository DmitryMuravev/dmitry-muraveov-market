package com.dmitry.muravev.market.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "goods_statistics")
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsStatisticEntity implements StatisticEntity {
    @Id
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;

    @Column(name = "check_count")
    private int checkCount;

    @Column(name = "total_cost")
    private Long totalCost;

    @Column(name = "total_discount")
    private Long totalDiscount;

}

package com.dmitry.muravev.market.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "positions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellPositionEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "goods_id", referencedColumnName = "id")
    private GoodsEntity goods;

    @Column(name = "count")
    private Integer count;

    @Column(name = "initial_cost")
    private Long initialCost;

    @Column(name = "result_cost")
    private Long resultCost;

    @Column(name = "result_discount")
    private Integer resultDiscount;

    @ManyToOne
    @JoinTable(name = "sell_positions",
            joinColumns = {@JoinColumn(name = "position_id")},
            inverseJoinColumns = {@JoinColumn(name = "check_number"), @JoinColumn(name = "sell_date")})
    private SellEntity sell;
}

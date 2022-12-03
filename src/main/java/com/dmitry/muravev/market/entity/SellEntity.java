package com.dmitry.muravev.market.entity;

import com.dmitry.muravev.market.entity.id.SellId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

@Entity
@IdClass(SellId.class)
@Table(name = "sells")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(
        name = "sell-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("client"),
                @NamedAttributeNode("positions")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "positions-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode("goods")
                        }
                )
        }
)
public class SellEntity {

    @Id
    @SequenceGenerator(name = "COL_GEN", sequenceName = "check_num_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COL_GEN")
    @Column(name = "check_number")
    private Integer checkNumber;

    @Id
    @Column(name = "sell_date")
    private LocalDate sellDate;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private ClientEntity client;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sell_positions",
            joinColumns = {@JoinColumn(name = "check_number"), @JoinColumn(name = "sell_date")},
            inverseJoinColumns = {@JoinColumn(name = "position_id")})
    private List<SellPositionEntity> positions;
}

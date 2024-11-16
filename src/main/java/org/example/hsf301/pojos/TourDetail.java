package org.example.hsf301.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "tour_detail")
public class TourDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tours tour;

    @ManyToOne
    @JoinColumn(name = "farm_id")
    private KoiFarms farm;

    @Column(name = "description")
    private String description;

    public TourDetail(Tours tour, KoiFarms farm, String description) {
        this.tour = tour;
        this.farm = farm;
        this.description = description;
    }
}

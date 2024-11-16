package org.example.hsf301.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tours")
@ToString(exclude = {"bookingTourDetails", "tourDetails"}) //prevent stack overflow when call toString
public class Tours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tour_name")
    private String tourName;

    @Column(name = "unit_price")
    private float unitPrice;

    @Column(name = "max_participants")
    private int maxParticipants;

    @Column(name = "remaining")
    private Integer remaining;

    @Column(name = "description")
    private String description;

    @Column(name = "start_time")
    private LocalDate startTime;

    @Column(name = "end_time")
    private LocalDate endTime;

    @Column(name = "tour_image")
    private String tourImg;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "tour", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private Set<BookingTourDetail> bookingTourDetails;

    @OneToMany(mappedBy = "tour", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH},fetch = FetchType.LAZY)
    private Set<TourDetail> tourDetails;

}

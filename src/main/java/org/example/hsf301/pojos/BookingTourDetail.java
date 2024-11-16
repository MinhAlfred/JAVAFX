package org.example.hsf301.pojos;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "booking_tour_detail")
public class BookingTourDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "booking_id") //name same foreign key mapping
    private Bookings booking;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tours tour;


    @Column(name = "participant")
    private int participant;

    @Column(name = "total_amount")
    private float totalAmount;

    public BookingTourDetail(Bookings booking, Tours tour, int participant) {
        this.booking = booking;
        this.tour = tour;
        this.participant = participant;
    }
}

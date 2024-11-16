package org.example.hsf301.pojos;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.hsf301.enums.CCSTATUS;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="delivery")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "receive_date")
    private LocalDate receiveDate;

    @Column(name = "health_koi_description")
    private String healthKoiDescription;

    @Column(name = "remain_amount")
    private float remainAmount;

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CCSTATUS status;

    @Column(name = "reason")
    private String reason;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinColumn(name = "delivery_staff_id")
    private Account deliveryStaff;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinColumn(name = "booking_id",unique = true)
    private Bookings booking;
}

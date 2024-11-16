package org.example.hsf301.pojos;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.hsf301.enums.CCSTATUS;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "deposit")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "deposit_amount")
    private float depositAmount;

    @Column(name = "remain_amount")
    private float remainAmount;

    @Column(name = "deposit_date")
    private LocalDateTime depositDate;

    @Column(name ="deposit_percentage")
    private float depositPercentage;

    @Column(name = "deposit_status")
    @Enumerated(EnumType.STRING)
    private CCSTATUS depositStatus;

    @Column(name = "delivery_expected_date")
    private LocalDate deliveryExpectedDate;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "shipping_fee")
    private float shippingFee;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinColumn(name = "booking_id",unique = true)
    private Bookings booking;

    @PrePersist
    protected void onCreate(){
        depositDate = LocalDateTime.now();
    }
}

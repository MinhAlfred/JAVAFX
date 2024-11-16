package org.example.hsf301.pojos;


import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "booking_koi_detail")
public class BookingKoiDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "koifarm_id")
    private KoiFarms koiFarms;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "koi_id")
    private Koi koi;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "booking_id")
    private Bookings booking;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "total_amount")
    private float totalAmount;

    @Column(name = "unit_Price")
    private float unitPrice;

    public BookingKoiDetail(Bookings booking, Koi koi, int quantity, float unitPrice) {
        this.booking =booking;
        this.koi=koi;
        this.quantity=quantity;
        this.unitPrice=unitPrice;
    }
    @PrePersist
    @PreUpdate
    private void validateData() {
        if (quantity <= 0) {
            throw new IllegalStateException("Quantity must be positive");
        }
        if (unitPrice <= 0) {
            throw new IllegalStateException("Unit price must be positive");
        }
        // Recalculate total amount to ensure consistency
        totalAmount = unitPrice*quantity;
    }
}

package org.example.hsf301.pojos;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "koi_of_farm")
public class KoiOfFarm {

    @JoinColumn(name = "farm_id")
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private KoiFarms koiFarms;

    @JoinColumn(name = "koi_id")
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Koi koi;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "available")
    private boolean available;
}

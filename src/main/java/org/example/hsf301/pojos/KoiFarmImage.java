package org.example.hsf301.pojos;

import lombok.*;

import javax.persistence.*;


/**
 * @author KoiFarmImage
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "koi_farm_image")
public class KoiFarmImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "koi_farm_id")
    private KoiFarms koiFarms;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;
}
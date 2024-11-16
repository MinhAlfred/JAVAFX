package org.example.hsf301.pojos;

import javax.persistence.*;

import lombok.*;

import javax.persistence.Entity;

@Entity
@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KoiImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "koi_id")
    private Koi kois;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;
}
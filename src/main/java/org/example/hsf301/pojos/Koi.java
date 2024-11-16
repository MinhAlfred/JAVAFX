package org.example.hsf301.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "kois")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"koiOfFarmList", "bookingKoiDetails"})
public class Koi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @Column(name = "koi_name")
    private String koiName;

    @Column(name = "origin")
    private String origin;

    @Column(name = "color")
    private String color;

    @Column(name = "description")
    private String description;

    @Column(name = "koi_image")
    private String koiImage;

        @OneToMany(mappedBy = "koi", cascade = {CascadeType.ALL} ,fetch = FetchType.LAZY)
        @JsonIgnore
        private List<KoiOfFarm> koiOfFarmList;

        @OneToMany(mappedBy = "koi", cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
        @JsonIgnore
        private List<BookingKoiDetail> bookingKoiDetails;

    @Column(name = "is_active")
    private boolean isActive;

}

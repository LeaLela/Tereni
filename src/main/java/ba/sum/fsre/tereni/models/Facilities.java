package ba.sum.fsre.tereni.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
@Table(name = "facilities")
public class Facilities {

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 200, nullable = false)
    @NotBlank(message = "Molimo unesite sport.")
    String sport;

    @Column(nullable = false)
    @NotBlank(message = "Molimo postavite sliku")
    String image;

    @Column(length = 200, nullable = false)
    @NotBlank(message = "Molimo unesite opis terena.")
    String description;
    Float price;

    @OneToMany(mappedBy = "facilities", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;
    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
}
}
package cristiancicale.capstone.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private int seat;

    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;

    @OneToMany(mappedBy = "event")
    private Set<Reservation> reservations = new HashSet<>();

    public Event(String title, String city, String country, LocalDate date, int seat, Artist artist) {
        this.title = title;
        this.city = city;
        this.country = country;
        this.date = date;
        this.seat = seat;
        this.artist = artist;
    }
}

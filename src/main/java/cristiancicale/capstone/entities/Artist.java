package cristiancicale.capstone.entities;

import cristiancicale.capstone.enums.Genre;
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
@Table(name = "artists")
@Getter
@Setter
@NoArgsConstructor
public class Artist {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(nullable = false)
    private String artistName;

    @Column(nullable = false)
    private String nationality;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genre genre;

    @Column
    private String avatar;

    @OneToMany(mappedBy = "artist")
    private Set<SongArtist> songArtists = new HashSet<>();

    @OneToMany(mappedBy = "artist")
    private Set<Event> events = new HashSet<>();

    public Artist(String artistName, String nationality, LocalDate dateOfBirth, Genre genre, String avatar) {
        this.artistName = artistName;
        this.nationality = nationality;
        this.dateOfBirth = dateOfBirth;
        this.genre = genre;
        this.avatar = avatar;
    }
}

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
@Table(name = "songs")
@Getter
@Setter
@NoArgsConstructor
public class Song {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String cover;

    @Column(nullable = false)
    private int duration;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genre genre;

    @Column(nullable = false)
    private LocalDate releaseDate;

    @OneToMany(mappedBy = "song")
    private Set<SongArtist> songArtists = new HashSet<>();

    @OneToMany(mappedBy = "song")
    private Set<Favorite> favorites = new HashSet<>();

    @OneToMany(mappedBy = "song")
    private Set<Review> reviews = new HashSet<>();

    public Song(String title, String cover, int duration, Genre genre, LocalDate releaseDate) {
        this.title = title;
        this.cover = cover;
        this.duration = duration;
        this.genre = genre;
        this.releaseDate = releaseDate;
    }
}

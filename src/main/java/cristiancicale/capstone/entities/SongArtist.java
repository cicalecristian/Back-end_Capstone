package cristiancicale.capstone.entities;

import cristiancicale.capstone.enums.RoleArtist;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(
        name = "song_artists",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"song_id", "artist_id", "role"})
        }
)
@Getter
@Setter
@NoArgsConstructor
public class SongArtist {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(nullable = false)
    private RoleArtist role;

    @ManyToOne
    @JoinColumn(name = "song_id", nullable = false)
    private Song song;

    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;

    public SongArtist(RoleArtist role, Song song, Artist artist) {
        this.role = role;
        this.song = song;
        this.artist = artist;
    }
}

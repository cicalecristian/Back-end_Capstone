package cristiancicale.capstone.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "reviews",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "song_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(nullable = false)
    private int rating;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "song_id", nullable = false)
    @JsonIgnore
    private Song song;

    public Review(int rating, User user, Song song) {
        this.rating = rating;
        this.user = user;
        this.song = song;
    }
}

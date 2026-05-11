package cristiancicale.capstone.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "favorites",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "song_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Favorite {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

//    @ManyToOne
//    @JoinColumn(name = "song_id", nullable = false)
//    private Song song;
}

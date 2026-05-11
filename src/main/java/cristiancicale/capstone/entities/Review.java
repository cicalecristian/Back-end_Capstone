package cristiancicale.capstone.entities;

import jakarta.persistence.*;
import lombok.*;

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
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(nullable = false)
    private int review;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

//    @ManyToOne
//    @JoinColumn(name = "song_id", nullable = false)
//    private Song song;
}

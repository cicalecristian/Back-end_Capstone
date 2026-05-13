package cristiancicale.capstone.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "reservations",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"user_id", "event_id"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(nullable = false)
    private int tickets;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Reservation(User user, Event event, int tickets) {
        this.user = user;
        this.event = event;
        this.tickets = tickets;
        this.createdAt = LocalDateTime.now();
    }
}

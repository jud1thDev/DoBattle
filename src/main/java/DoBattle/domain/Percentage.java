package DoBattle.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Percentage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "battle_id")
    private Battle battle;

    @Column(nullable = false)
    private String userIdentify;

    @Column(nullable = false)
    private LocalDate date;

    private double achievementRate;
    
    //생성자
    public Percentage(Battle battle, String identify) {
        this.battle = battle;
        this.userIdentify = identify;
        this.date = LocalDate.now();
    }
}
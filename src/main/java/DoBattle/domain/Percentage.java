package DoBattle.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

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

    public Percentage(){}
    public Percentage(Battle battle, String identify) {
        this.battle = battle;
        this.userIdentify = identify;
        this.date = LocalDate.now();
    }

    // Getter와 Setter 메서드


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Battle getBattle() {
        return battle;
    }

    public void setBattle(Battle battle) {
        this.battle = battle;
    }

    public String getUserIdentify() {
        return userIdentify;
    }

    public void setUserIdentify(String userIdentify) {
        this.userIdentify = userIdentify;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setAchievementRate(double achievementRate) {
        this.achievementRate = achievementRate;
    }

    public double getAchievementRate() {
        return achievementRate;
    }
}
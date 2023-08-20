package DoBattle.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Battle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String battleName;
    private String battleCategory;
    private LocalDate startDate;
    private LocalDate endDate;
    private String code; // 난수코드

    @ManyToOne
    private User creator;

    // Getter and Setter 메서드들
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBattleName() {
        return battleName;
    }

    public void setBattleName(String battleName) {
        this.battleName = battleName;
    }

    public String getBattleCategory() {
        return battleCategory;
    }

    public void setBattleCategory(String battleCategory) {
        this.battleCategory = battleCategory;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    // toString 메서드 - 객체의 정보를 문자열로 표현
    @Override
    public String toString() {
        return "Battle{" +
                "id=" + id +
                ", battleName='" + battleName + '\'' +
                ", battleCategory='" + battleCategory + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", creator=" + creator +
                '}';
    }
}

package DoBattle.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Battle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String battleName;
    private String battleCategory;
    private LocalDate startDate;
    private LocalDate endDate;
    private String battleCode; // 난수코드
    private String createUser;
    private String joinUser;
    private int currentParticipants; // 현재참여자

    @OneToMany(mappedBy = "battle") // battle과 tododata의 관계성
    private List<TodoData> todoDataList = new ArrayList<>();

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

    public String getBattleCode() {
        return battleCode;
    }

    public void setBattleCode(String battleCode) {
        this.battleCode = battleCode;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getJoinUser() {
        return joinUser;
    }

    public void setJoinUser(String joinUser) {
        this.joinUser = joinUser;
    }

    public int getCurrentParticipants() {
        return currentParticipants;
    }

    public void setCurrentParticipants(int currentParticipants) {
        this.currentParticipants = currentParticipants;
    }

}
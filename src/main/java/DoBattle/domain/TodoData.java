package DoBattle.domain;

import javax.persistence.*;

@Entity
public class TodoData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userIdentify;

    @Column(nullable = false)
    private String todoDataValue;

    @Column(nullable = false)
    private String value;

    private int completedCount; // 완료된 횟수
    private int incompleteCount; // 미완료된 횟수

    @ManyToOne // battle과 tododata의 관계성
    @JoinColumn(name = "battle_id")
    private Battle battle;

    // getter setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserIdentify() {
        return userIdentify;
    }

    public void setUserIdentify(String userIdentify) {
        this.userIdentify = userIdentify;
    }

    public String getTodoDataValue() {
        return todoDataValue;
    }

    public void setTodoDataValue(String todoDataValue) {
        this.todoDataValue = todoDataValue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Battle getBattle() {
        return battle;
    }

    public void setBattle(Battle battle) {
        this.battle = battle;
    }

    public int getCompletedCount() {
        return completedCount;
    }

    public void setCompletedCount(int completedCount) {
        this.completedCount = completedCount;
    }

    public int getIncompleteCount() {
        return incompleteCount;
    }

    public void setIncompleteCount(int incompleteCount) {
        this.incompleteCount = incompleteCount;
    }
}


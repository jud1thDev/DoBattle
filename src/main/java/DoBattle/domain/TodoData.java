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
    private String todoData;

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

    public String getTodoData() {
        return todoData;
    }

    public void setTodoData(String todoData) {
        this.todoData = todoData;
    }
}

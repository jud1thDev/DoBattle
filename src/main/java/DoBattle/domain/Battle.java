package DoBattle.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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
}
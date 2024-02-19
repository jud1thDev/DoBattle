package DoBattle.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
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

    @ManyToOne // battle과 tododata의 관계성
    @JoinColumn(name = "battle_id")
    private Battle battle;

    @Column
    private LocalDate date;
}
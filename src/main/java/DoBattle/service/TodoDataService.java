package DoBattle.service;

import DoBattle.domain.Battle;
import DoBattle.domain.TodoData;
import DoBattle.repository.BattleRepository;
import DoBattle.repository.TodoDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoDataService {

    @Autowired
    private TodoDataRepository todoDataRepository;

    @Autowired
    private BattleRepository battleRepository;

    public List<TodoData> getTodoDataForUser(String userIdentify) {
        return todoDataRepository.findByUserIdentify(userIdentify);
    }

    public void saveTodoData(String battleCode, String todoDataValue, String value) {
        Battle battle = battleRepository.findByBattleCode(battleCode);

        if (battle != null) {
            TodoData todo = new TodoData();
            todo.setBattle(battle);
            todo.setUserIdentify(battle.getCreateUser());
            todo.setTodoDataValue(todoDataValue);
            todo.setValue(value);

            // value(notDone or Done)값에 따라 개수세기
            if ("done".equals(value)) {
                todo.setCompletedCount(todo.getCompletedCount() + 1);
            } else {
                todo.setIncompletedCount(todo.getIncompletedCount() + 1);
            }

            todoDataRepository.save(todo);
        } else {
            System.out.println("올바른 배틀코드를 입력하세요");
        }
    }

}


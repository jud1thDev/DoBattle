package DoBattle.service;

import DoBattle.domain.Battle;
import DoBattle.domain.TodoData;
import DoBattle.domain.User;
import DoBattle.repository.BattleRepository;
import DoBattle.repository.TodoDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
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

    public void saveTodoData(Battle battle, String todoDataValue, String value, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser != null) {
            String currentUserIdentify = currentUser.getIdentify();

            TodoData todo = new TodoData();
            todo.setBattle(battle);
            todo.setUserIdentify(currentUserIdentify);
            todo.setTodoDataValue(todoDataValue);
            todo.setValue(value);

            todoDataRepository.save(todo);

            // completedCount와 incompletedCount를 업데이트
            updateCountsForBattle(battle);
        }
    }


    public void updateCountsForBattle(Battle battle) {
        if (battle != null) {
            List<TodoData> todoDataList = todoDataRepository.findByBattle_BattleCode(battle.getBattleCode());

            int completedCount = 0;
            int incompletedCount = 0;

            for (TodoData todo : todoDataList) {
                if ("done".equals(todo.getValue())) {
                    completedCount++;
                } else {
                    incompletedCount++;
                }
            }

            battle.setCompletedCount(completedCount);
            battle.setIncompletedCount(incompletedCount);
            battleRepository.save(battle);
        }
    }
}
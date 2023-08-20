package DoBattle.repository;

import DoBattle.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByIdentify(String identify); // 아이디 겹치지 않아야 하니 찾는 메소드 추가
}


package DoBattle.domain;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;
    private String identify;
    private String password;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() { return password; }

    public String getUsername() { return username; }
}

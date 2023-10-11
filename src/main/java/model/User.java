package model;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;


@Entity
@Table(name = "user")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_birthday", columnDefinition = "date")
    private Date userBirthday;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_sex", columnDefinition = "enum('Male', 'Female')")
    private UserSex userSex;

    public User(int user_id, String user_name, Date user_birthday, UserSex user_sex) {
        this.userId = user_id;
        this.userName = user_name;
        this.userBirthday = user_birthday;
        this.userSex = user_sex;
    }

    public User(String user_name, Date user_birthday, UserSex user_sex) {
        this.userName = user_name;
        this.userBirthday = user_birthday;
        this.userSex = user_sex;
    }

    public User() {

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int user_id) {
        this.userId = user_id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String user_name) {
        this.userName = user_name;
    }

    public Date getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(Date user_birthday) {
        this.userBirthday = user_birthday;
    }

    public UserSex getUserSex() {
        return userSex;
    }

    public void setUserSex(UserSex user_sex) {
        this.userSex = user_sex;
    }

    public Integer calculateFullYears() {
        return Period.between(LocalDate.from(new java.sql.Timestamp(
                userBirthday.getTime()).toLocalDateTime()), LocalDate.now()).getYears();
    }
}

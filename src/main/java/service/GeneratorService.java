package service;

import com.github.javafaker.Faker;
import model.User;
import model.UserSex;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class GeneratorService {
    public static ArrayList<User> generateUsers(int userCount) {
        Faker faker = new Faker();
        ArrayList<User> users = new ArrayList<>();
        for (int i = 1; i < userCount + 1; i++) {
            String fullName = faker.name().lastName() + " " + faker.name().firstName();
            Date date = new Date(ThreadLocalRandom.current().nextInt(30000000) * 30000L);
            UserSex sex = Math.random() < 0.5 ? UserSex.Male : UserSex.Female;
            User user = new User(fullName, date, sex);
            users.add(user);
            if (i % 10000 == 0) {
                System.out.println("Users generated: " + i);
            }
        }
        return users;
    }

    public static ArrayList<User> generateStartLetterUsers(int userCount, String startLetter) {
        Faker faker = new Faker();
        ArrayList<User> users = new ArrayList<>();
        for (int i = 1; i < userCount + 1; i++) {
            String fullName = startLetter + faker.name().lastName().substring(1) + " " + faker.name().firstName();
            Date date = new Date(ThreadLocalRandom.current().nextInt(30000000) * 30000L);
            UserSex sex = UserSex.Male;
            User user = new User(fullName, date, sex);
            users.add(user);
        }
        return users;
    }
}

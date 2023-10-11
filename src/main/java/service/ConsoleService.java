package service;

import model.User;
import model.UserSex;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;

public class ConsoleService {

    private static void showUsers(ArrayList<User> users) {
        String leftAlignFormat = "| %-40s | %-10s | %-6s | %-10d |%n";
        System.out.format("+------------------------------------------+------------+--------+------------+%n");
        System.out.format("| Full name                                | Birthday   | Sex    | Full years |%n");
        System.out.format("+------------------------------------------+------------+--------+------------+%n");
        for (User user: users) {
            System.out.format(leftAlignFormat, user.getUserName(), user.getUserBirthday().toString(),
                    user.getUserSex().name(), user.calculateFullYears());
        }
        System.out.format("+------------------------------------------+------------+--------+------------+%n");
    }
    public static void choosePath(String[] params) {
        if (params.length == 0) {
            System.out.println("Requires at least one parameter, try instead 'java -jar .\\PTMK_Task-1.0.jar 1'");
        }
        else {
            String mode = params[0];
            UserService userService = new UserService();
            switch (mode) {
                case "1":
                    // creating "user" table
                    userService.createTable();
                    System.out.println("Table user created or updated successfully!");
                    break;
                case "2":
                    // parsing params and insert into table "user" one user
                    if (params.length != 4) {
                        System.out.println("Wrong number of parameters, try instead 'java -jar .\\PTMK_Task-1.0.jar 2 \"Ivanov Petr Sergeevich\" 2009-07-12 Male'");
                    }
                    else {
                        User user = new User(params[1], Date.valueOf(params[2]), UserSex.valueOf(params[3]));
                        userService.insertUser(user);
                        System.out.println("User with parameters:");
                        for (String param: Arrays.copyOfRange(params, 1, 4)) {
                            System.out.println(param);
                        }
                        System.out.println("... has been inserted in table 'user'. Full years: " + user.calculateFullYears());
                    }
                    break;
                case "3":
                    // selecting users with unique combinations of full name and birthdate
                    ArrayList<User> uniqueUsers = userService.findUniqueNameDate();
                    System.out.println("Users with unique combinations of full name and birthdate:");
                    showUsers(uniqueUsers);
                    break;
                case "4":
                    // generating users
                    System.out.println("Starting to generate 1000000 users...");
                    ArrayList<User> users = GeneratorService.generateUsers(1000000);
                    System.out.println("Starting to inserting generated users into table...");
                    userService.insertUsersBatch(users);

                    // generating users with last name starting with letter F
                    System.out.println("Starting to generate 100 users with last name starting with letter F...");
                    users = GeneratorService.generateStartLetterUsers(100, "F");
                    System.out.println("Starting to inserting generated users into table...");
                    userService.insertUsersBatch(users);
                    break;
                case "5":
                    // selecting users with last name starting with letter F
                    long startTime = System.currentTimeMillis();
                    ArrayList<User> f_users = userService.findFirstLetterAndSex("F", UserSex.Male);
                    long endTime = System.currentTimeMillis();
                    System.out.println("Users with last name starting with letter F and with male sex:");
                    showUsers(f_users);
                    long timeElapsed = endTime - startTime;
                    System.out.println("Time elapsed (without printing): " + timeElapsed / 1000 + " seconds " + timeElapsed % 1000 + " ms.");
                    break;
                default:
                    System.out.println("Wrong parameters. Check your manual for more information'");

            }
        }
    }

    public static void showUsers() {


    }
}

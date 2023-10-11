package service;

import dao.UserDao;
import model.User;
import model.UserSex;

import java.util.ArrayList;

public class UserService {
    private UserDao userDao = new UserDao();
    public void createTable() {userDao.createTable();}
    public void insertUser(User user) {userDao.insertUser(user);}
    public void insertUsersBatch(ArrayList<User> users) {userDao.insertUsersBatch(users);}
    public ArrayList<User> findUniqueNameDate() {
        return userDao.findUniqueNameDate();
    }
    public ArrayList<User> findFirstLetterAndSex(String firstLetter, UserSex sex) {
        return userDao.findUsersFirstLetterAndSex(firstLetter, sex);
    }
}

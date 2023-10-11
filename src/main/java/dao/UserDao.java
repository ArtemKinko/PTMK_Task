package dao;


import config.HibernateSessionFactoryUtil;
import model.User;
import model.UserSex;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDao {
    public void createTable() {
        HibernateSessionFactoryUtil.getSessionFactory().
                openSession().createSQLQuery("select NULL limit 0");
    }

    public void insertUser(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (RuntimeException e) {
            transaction.rollback();
            System.out.println("Exception:" + e.getMessage());
        }
        finally {
            session.close();
        }
    }

    public void insertUsersBatch(ArrayList<User> users) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                session.save(user);
                if (i % 20 == 0) {
                    session.flush();
                    session.clear();
                }
                if (i % 5000 == 0) {
                    System.out.println("Users inserted: " + i);
                }
            }
            transaction.commit();
            System.out.println("All users inserted!");
        } catch (RuntimeException e) {
            transaction.rollback();
            System.out.println("Exception:" + e.getMessage());
        }
        finally {
            session.close();
        }
    }

    public ArrayList<User> findUniqueNameDate() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        ArrayList<User> users = null;
        try {
            List<Object[]> rawUsers = session.createSQLQuery("select u1.user_id, u1.user_name, u1.user_birthday, u2.user_sex from (select user_name, user_birthday, MIN(user_id) as user_id from user group by user_name, user_birthday) as u1 join user u2 where u1.user_id = u2.user_id order by user_name").list();
            users = new ArrayList<>();
            for (Object[] rawUser : rawUsers) {
                users.add(new User((int) rawUser[0], (String) rawUser[1], (Date) rawUser[2], UserSex.valueOf((String) rawUser[3])));
            }
        } catch (RuntimeException e) {
            System.out.println("Exception:" + e.getMessage());
        } finally {
            session.close();
        }
        return users;
    }

    public ArrayList<User> findUsersFirstLetterAndSex(String firstLetter, UserSex sex) {
        System.out.println(sex.name());
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        ArrayList<User> users = null;
        try {
             Query<Object[]> query = session.createSQLQuery("select user_id, user_name, user_birthday, user_sex from user where user_name like :letter and user_sex = :sex");
             List<Object[]> rawUsers = query.setParameter("letter", firstLetter + "%").setParameter("sex", sex.name()).list();
//            Query<Object[]> query = session.createSQLQuery("call fletter()");
//            List<Object[]> rawUsers = query.list();

            users = new ArrayList<>();
            for (Object[] rawUser : rawUsers) {
                users.add(new User((int) rawUser[0], (String) rawUser[1], (Date) rawUser[2], UserSex.valueOf((String) rawUser[3])));
            }
        } catch (RuntimeException e) {
            System.out.println("Exception:" + e.getMessage());
        } finally {
            session.close();
        }
        return users;
    }

}

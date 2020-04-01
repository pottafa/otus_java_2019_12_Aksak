package ru.otus.homework;

import org.hibernate.SessionFactory;
import ru.otus.homework.core.dao.UserDao;
import ru.otus.homework.core.model.AddressDataSet;
import ru.otus.homework.core.model.PhoneDataSet;
import ru.otus.homework.core.model.User;
import ru.otus.homework.core.service.DBServiceUser;
import ru.otus.homework.core.service.DbServiceUserImpl;
import ru.otus.homework.hibernate.HibernateUtils;
import ru.otus.homework.hibernate.dao.UserDaoHibernate;
import ru.otus.homework.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * @author sergey
 * created on 03.02.19.
 */
public class HIbernateTest {

    public static void main(String[] args) {

        SessionFactory factory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class, AddressDataSet.class, PhoneDataSet.class);
        SessionManagerHibernate managerHibernate = new SessionManagerHibernate(factory);
        UserDao dao = new UserDaoHibernate(managerHibernate);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(dao);

        List<PhoneDataSet> alexPhones = new ArrayList<>();
        alexPhones.add(new PhoneDataSet("12345678"));
        alexPhones.add(new PhoneDataSet("87654321"));

        AddressDataSet alexAddress = new AddressDataSet("Moscow, Some Street, Some building");

        User alex = new User("Алекс", 30, alexAddress, alexPhones );
        long id = dbServiceUser.saveUser(alex);
        Optional<User> mayBeCreatedUser = dbServiceUser.getUser(id);

    }
}


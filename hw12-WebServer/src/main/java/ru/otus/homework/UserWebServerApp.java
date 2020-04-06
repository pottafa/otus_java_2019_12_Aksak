package ru.otus.homework;

import org.hibernate.SessionFactory;
import ru.otus.homework.dataBase.core.service.DBServiceUser;
import ru.otus.homework.dataBase.core.service.DbServiceUserImpl;
import ru.otus.homework.dataBase.core.dao.UserDao;
import ru.otus.homework.dataBase.hibernate.HibernateUtils;
import ru.otus.homework.dataBase.hibernate.dao.UserDaoHibernate;
import ru.otus.homework.dataBase.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.homework.dataBase.core.model.AddressDataSet;
import ru.otus.homework.dataBase.core.model.PhoneDataSet;
import ru.otus.homework.dataBase.core.model.User;
import ru.otus.homework.server.UsersWebServer;
import ru.otus.homework.server.UsersWebServerImpl;
import ru.otus.homework.services.TemplateProcessor;
import ru.otus.homework.services.TemplateProcessorImpl;
import ru.otus.homework.services.UserAuthService;
import ru.otus.homework.services.UserAuthServiceImpl;

public class UserWebServerApp {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

    public static void main(String[] args) throws Exception {
        SessionFactory factory = HibernateUtils.buildSessionFactory("hibernate1.cfg.xml", User.class, AddressDataSet.class, PhoneDataSet.class);
        SessionManagerHibernate managerHibernate = new SessionManagerHibernate(factory);
        UserDao dao = new UserDaoHibernate(managerHibernate);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(dao);
        User user = new User("Admin", 27, "Admin", "otus");
        dbServiceUser.saveUser(user);

        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        UserAuthService authService = new UserAuthServiceImpl(dbServiceUser);

        UsersWebServer usersWebServer = new UsersWebServerImpl(WEB_SERVER_PORT
                , dbServiceUser, templateProcessor, authService);

        usersWebServer.start();
        usersWebServer.join();


    }
}

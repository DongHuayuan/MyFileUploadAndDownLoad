package tech.six6.service.impl;

import org.apache.ibatis.session.SqlSession;
import tech.six6.dao.UserDao;
import tech.six6.entity.User;
import tech.six6.service.UserService;
import tech.six6.util.SqlsessionFactoryUtil;

public class UserServiceImpl implements UserService {

    SqlSession sqlSession = SqlsessionFactoryUtil.getSqlSessionFactory().openSession();

    UserDao userDao = sqlSession.getMapper(UserDao.class);

    @Override
    public User login(String username, String password) {
        return userDao.login(username, password);
    }
}

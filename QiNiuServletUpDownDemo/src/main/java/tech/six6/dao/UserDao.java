package tech.six6.dao;

import org.apache.ibatis.annotations.Param;
import tech.six6.entity.User;

public interface UserDao {

    int register(User user);

    User login(@Param("username") String username, @Param("password") String password);

}

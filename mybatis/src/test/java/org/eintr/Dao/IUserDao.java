package org.eintr.Dao;

import org.eintr.po.User;

import java.util.List;

public interface IUserDao {
    User queryUserInfoById(Long id);

    List<User> queryUserList(User user);

    String queryUserName(String uId);
}

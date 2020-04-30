package com.loghme.models.mappers.User;

import com.loghme.models.domain.User.User;

import java.sql.SQLException;

interface IUserMapper {
    User find(int userId) throws SQLException;
    User findByEmail(String email) throws SQLException;
    void insert(User user) throws SQLException;
    void updateCredit(int userId, double credit) throws SQLException;
}

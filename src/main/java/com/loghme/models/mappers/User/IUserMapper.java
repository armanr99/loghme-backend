package com.loghme.models.mappers.User;

import com.loghme.database.Mapper.IMapper;
import com.loghme.models.domain.User.User;

import java.sql.SQLException;

interface IUserMapper extends IMapper<User> {
    User find(int userId) throws SQLException;
    void insert(User user) throws SQLException;
    void updateCredit(int userId, double credit) throws SQLException;
}

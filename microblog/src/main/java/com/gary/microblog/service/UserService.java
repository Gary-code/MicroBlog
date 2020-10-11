package com.gary.microblog.service;

import com.gary.microblog.entity.User;

public interface UserService {

    User checkUser(String username,String password);
}

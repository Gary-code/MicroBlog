/**
 * Authored by Gary on 2020/07/25.
 **/

package com.gary.microblog.service;

import com.gary.microblog.dao.UserRepository;
import com.gary.microblog.entity.User;
import com.gary.microblog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user =userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}

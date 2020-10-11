package com.gary.microblog.dao;

import com.gary.microblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    //根据规则查询数据库,一定要按规则来写
    User findByUsernameAndPassword(String username,String password);

}

package com.canjura.finalproject.repository.user;

import com.canjura.finalproject.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    User findUserByEmail(String email);

    @Transactional
    void deleteUserByEmail(String email);

}

package com.Bug_Tracker.repository;

import com.Bug_Tracker.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

// spring is smart enough to know to build a query based off of findUserByUsername etc
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUsername(String username);

    User findUserByEmail(String email);
}

package com.Bug_Tracker.repository;

import com.Bug_Tracker.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
// spring is smart enough to know to build a query based off of findUserByUsername etc
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUsername(String username);

    User findUserByEmail(String email);
}

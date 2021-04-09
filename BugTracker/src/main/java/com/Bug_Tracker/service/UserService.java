package com.Bug_Tracker.service;

//import com.Bug_Tracker.Model.Admin;
import com.Bug_Tracker.Model.User;
import com.Bug_Tracker.exception.domain.EmailExistException;
import com.Bug_Tracker.exception.domain.UsernameExistException;
import com.Bug_Tracker.exception.domain.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

public interface UserService {
    User register(String firstName, String lastName, String username, String password, String email) throws UsernameNotFoundException,UsernameExistException, EmailExistException;
  //  Admin register(String firstName, String lastName, String username, String password, String email) ;


    User findUserByEmail(String email);
    User findUserByUsername(String username);
    List<User> getUsers();
    void deleteUser(Long id);
    User updateRole(String role, String username ) throws IOException;
   // List<User> getUsers();

    //User addNewUser(String firstName, String lastName, String username, String email, String role, boolean isActive, String password) throws UsernameNotFoundException, UsernameExistException, EmailExistException, IOException;

   // User updateRole(String role, String username) throws IOException;

   // void deleteUser(Long id);

    // void deleteUser(Long id) throws IOException;


}

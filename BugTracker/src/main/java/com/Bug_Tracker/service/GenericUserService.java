package com.Bug_Tracker.service;

import com.Bug_Tracker.Model.User;
import com.Bug_Tracker.exception.domain.EmailExistException;
import com.Bug_Tracker.exception.domain.UsernameExistException;
import com.Bug_Tracker.exception.domain.UsernameNotFoundException;

import java.util.List;

public interface GenericUserService<T> {
    T register(String firstName, String lastName, String username, String password, String email); //throws UsernameExistException, EmailExistException;

    List<T> getUsers();

    T findUserByUsername(String username);

    //User addNewUser(String firstName, String lastName, String username, String email, String role, boolean isActive, String password) throws UsernameNotFoundException, UsernameExistException, EmailExistException, IOException;

    // User updateUser(String username) throws UsernameNotFoundException, UsernameExistException, IOException;

    void deleteUser(Long id);
}

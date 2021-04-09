package com.Bug_Tracker.service;

import com.Bug_Tracker.Model.Bug;
import com.Bug_Tracker.exception.domain.*;
import java.io.IOException;
import java.util.List;

public interface BugService {

    List<Bug> getBugs();

    Bug findBugByBugId(String id);

    Bug addNewBug(String bugName,String description, String priority, String bugType, String systemBug, boolean isActive) throws IOException;//, BugnameNotFoundException, BugnameExistException;

    Bug updateBug(String id,String newBugName,String newDescription, String newPriority, String newBugType, String newSystemBug, boolean newIsActive);// throws BugnameNotFoundException, BugnameExistException;

    void deleteBug(Long id) throws IOException;

}

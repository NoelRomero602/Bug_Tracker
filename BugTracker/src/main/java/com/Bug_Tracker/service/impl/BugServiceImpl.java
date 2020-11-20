package com.Bug_Tracker.service.impl;

import com.Bug_Tracker.domain.Bug;
import com.Bug_Tracker.exception.domain.BugnameExistException;
import com.Bug_Tracker.exception.domain.BugnameNotFoundException;
import com.Bug_Tracker.repository.BugRepository;
import com.Bug_Tracker.service.BugService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
@Transactional
@Service
public class BugServiceImpl implements BugService {

    private String generateBugId() {
        return RandomStringUtils.randomNumeric(10);
    }

    private BugRepository bugRepository;

    @Autowired
    public BugServiceImpl(BugRepository bugRepository) {
        this.bugRepository = bugRepository;
    }

    @Override
    public List<Bug> getBugs() {
        return bugRepository.findAll();
    }

    @Override
    public Bug findBugByBugname(String bugname) {
         return bugRepository.findBugByBugName(bugname);
    }


    @Override
    public Bug addNewBug(String bugName,String description, String priority, String bugType, String systemBug, boolean isActive) throws BugnameNotFoundException, BugnameExistException {
        validateNewBugname(null,bugName);
        Bug bug = new Bug();
        bug.setBugId(generateBugId());
        bug.setBugDescription(description);
        bug.setBugPriority(priority);
        bug.setBugDate(new Date());
        bug.setBugType(bugType);
        bug.setActive(isActive);
        bug.setSystemBug(systemBug);
        bug.setBugName(bugName);
        bugRepository.save(bug);
        return bug;
    }

    @Override
    public Bug updateBug(String currentBugname,String newBugname,String newDescription, String newPriority, String newBugType, String newSystemBug, boolean newIsActive) throws BugnameNotFoundException, BugnameExistException
    { Bug bug = validateNewBugname(currentBugname,newBugname);
        bug.setBugDescription(newDescription);
        bug.setSystemBug(newSystemBug);
        bug.setBugType(newBugType);
        bug.setActive(newIsActive);
        bug.setBugPriority(newPriority);
        bug.setBugName(newBugname);
        bugRepository.save(bug);
        return bug;
    }


    private Bug validateNewBugname(String currentBugname, String newBugname) throws BugnameNotFoundException, BugnameExistException
     {   Bug bugByNewBugname = findBugByBugname(newBugname);
        if(StringUtils.isNotBlank(currentBugname)) {
            Bug currentBug = findBugByBugname(currentBugname);
            if(currentBug == null) {
                throw new BugnameNotFoundException("No bug found by bugname" + currentBugname);
            }
            if(bugByNewBugname != null && !currentBug.getId().equals(bugByNewBugname.getId())) {
                throw new BugnameExistException("Bugname already exists");
            }
            return currentBug;
        } else {

            if(bugByNewBugname != null) {
                throw new BugnameExistException("Bugname already exists");
            }
            return null;
        }
    }


    @Override
    public void deleteBug(Long id) {
        bugRepository.deleteById(id);

    }

}

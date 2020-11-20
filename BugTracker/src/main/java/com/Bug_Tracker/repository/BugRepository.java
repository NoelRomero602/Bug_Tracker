package com.Bug_Tracker.repository;

import com.Bug_Tracker.domain.Bug;
import org.springframework.data.jpa.repository.JpaRepository;
public interface BugRepository extends JpaRepository<Bug, Long> {


     Bug findBugByBugName(String bugName);

}
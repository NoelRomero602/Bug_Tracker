package com.Bug_Tracker.repository;

import com.Bug_Tracker.Model.Bug;
import org.aspectj.lang.annotation.control.CodeGenerationHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface BugRepository extends JpaRepository<Bug, Long> {


     Bug findBugByBugId(String bugId);

}
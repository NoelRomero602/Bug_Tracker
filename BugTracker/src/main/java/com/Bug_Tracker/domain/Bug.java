package com.Bug_Tracker.domain;

import javax.persistence.*;
import java.util.Date;
@Entity
public class Bug {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String bugId;
    private String bugDescription;
    private String systemBug;
    private String bugPriority;
    private Date bugDate;
    private boolean isActive;
    private String bugType;
    private String bugName;

    public Bug(){}
    public Bug(String bugName,String bugId, String bugDescription, String systemBug, String bugPriority, Date bugDate, boolean isActive, String bugType) {
        this.bugId = bugId;
        this.bugDescription = bugDescription;
        this.systemBug = systemBug;
        this.bugPriority = bugPriority;
        this.bugDate = bugDate;
        this.isActive = isActive;
        this.bugType = bugType;
        this.bugName = bugName;
    }

    public String getBugName() {
        return bugName;
    }

    public void setBugName(String bugName) {
        this.bugName = bugName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBugId() {
        return bugId;
    }

    public void setBugId(String bugId) {
        this.bugId = bugId;
    }

    public String getBugDescription() {
        return bugDescription;
    }

    public void setBugDescription(String bugDescription) {
        this.bugDescription = bugDescription;
    }

    public String getSystemBug() {
        return systemBug;
    }

    public void setSystemBug(String systemBug) {
        this.systemBug = systemBug;
    }

    public String getBugPriority() {
        return bugPriority;
    }

    public void setBugPriority(String bugPriority) {
        this.bugPriority = bugPriority;
    }

    public Date getBugDate() {
        return bugDate;
    }

    public void setBugDate(Date bugDate) {
        this.bugDate = bugDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getBugType() {
        return bugType;
    }

    public void setBugType(String bugType) {
        this.bugType = bugType;
    }
}
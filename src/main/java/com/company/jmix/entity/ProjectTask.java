package com.company.jmix.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.UUID;

@JmixEntity
@Table(name = "PROJECT_TASK", indexes = {
        @Index(name = "IDX_PROJECT_TASK_PROJECT", columnList = "PROJECT_ID")
})
@Entity
public class ProjectTask {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @InstanceName
    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;


    @PositiveOrZero
    @Column(name = "ESTIMATE_EFFORTS", nullable = false)
    private Integer estimateEfforts;

    @JoinColumn(name = "PROJECT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    @JoinColumn(name = "ASSIGNEE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private User assignee;
/*
    @DependsOnProperties({"project", "name"})
    @JmixProperty
    public String getCaption() {
        return String.format("[%s] %s", project.getName(), name);
    }

    @DependsOnProperties({"startDate", "estimateEfforts", "endDate"})
    @JmixProperty
    public LocalDateTime getEstimatedEndDate() {
        if(endDate != null){
            return endDate;
        } else {
            return startDate.plusHours(estimateEfforts);
        }
    }  /**/

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public User getAssignee() {
        return assignee;
    }

    public Integer getEstimateEfforts() {
        return estimateEfforts;
    }

    public void setEstimateEfforts(Integer estimateEfforts) {
        this.estimateEfforts = estimateEfforts;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
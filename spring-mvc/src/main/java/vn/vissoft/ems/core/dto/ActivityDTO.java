package vn.vissoft.ems.core.dto;

import java.util.Date;

public class ActivityDTO {

  private String owner;
  private String task;
  private String project;
  private Date completedTime;
  private StatusEnum status = StatusEnum.HOLD;
  private Date dueDate;

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public String getTask() {
    return task;
  }

  public void setTask(String task) {
    this.task = task;
  }

  public String getProject() {
    return project;
  }

  public void setProject(String project) {
    this.project = project;
  }

  public Date getCompletedTime() {
    return completedTime;
  }

  public void setCompletedTime(Date completedTime) {
    this.completedTime = completedTime;
  }

  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public Date getDueDate() {
    return dueDate;
  }

  public void setDueDate(Date dueDate) {
    this.dueDate = dueDate;
  }
}

package vn.vissoft.ems.core.controller;

import org.zkoss.zul.ListModelList;
import vn.vissoft.ems.core.dto.ActivityDTO;
import vn.vissoft.ems.core.repo.ProjectDao;

public class ActivityVM {

  private ListModelList<ActivityDTO> activityList;

  public ActivityVM() {
    activityList = new ListModelList<>(ProjectDao.getRecentActivityList());
  }

  public ListModelList<ActivityDTO> getActivityList() {
    return activityList;
  }
}

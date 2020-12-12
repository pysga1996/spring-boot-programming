package vn.vissoft.ems.core.controller;

import org.zkoss.zul.ListModelList;
import vn.vissoft.ems.core.dto.ActivityDTO;
import vn.vissoft.ems.core.dto.StatusEnum;
import vn.vissoft.ems.core.repo.ProjectDao;

public class ProgressVM {

  private ListModelList<ActivityDTO> progressList;
  private ListModelList<StatusEnum> statusModel = new ListModelList<>(StatusEnum.values());

  public ProgressVM() {
    progressList = new ListModelList<>(ProjectDao.getActivityList());
  }

  public ListModelList<ActivityDTO> getProgressList() {
    return progressList;
  }

  public ListModelList<StatusEnum> getStatusModel() {
    return statusModel;
  }
}

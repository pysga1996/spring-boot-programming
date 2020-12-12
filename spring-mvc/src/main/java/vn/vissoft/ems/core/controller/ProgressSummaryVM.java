package vn.vissoft.ems.core.controller;

import org.zkoss.zul.ListModelList;
import vn.vissoft.ems.core.dto.ProgressDTO;
import vn.vissoft.ems.core.repo.ProjectDao;

public class ProgressSummaryVM {

  private ListModelList<ProgressDTO> progressSummary;

  public ProgressSummaryVM() {
    progressSummary = new ListModelList<>(ProjectDao.getProgressSummary());
  }

  public ListModelList<ProgressDTO> getProgressSummary() {
    return progressSummary;
  }
}

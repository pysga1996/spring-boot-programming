package vn.vissoft.ems.core.controller;

import org.zkoss.zul.ListModelList;
import vn.vissoft.ems.core.repo.ProjectDao;

public class TodoVM {

  private ListModelList<String> todoList;

  public TodoVM() {
    todoList = new ListModelList<>(ProjectDao.getTodoList());
    todoList.setMultiple(true);
    todoList.addToSelection(todoList.get(2));
  }

  public ListModelList<String> getTodoList() {
    return todoList;
  }
}

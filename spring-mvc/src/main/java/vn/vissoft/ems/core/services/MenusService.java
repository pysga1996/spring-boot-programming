package vn.vissoft.ems.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vissoft.ems.core.dto.MenuDTO;
import vn.vissoft.ems.core.model.Menu;
import vn.vissoft.ems.core.repo.MenusRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenusService {

  @Autowired
  private MenusRepository menusRepository;

  public List<MenuDTO> findAllByIsActive(Boolean isActive) {
    List<Menu> menuList = menusRepository
        .findAllByIsActiveAndParentIsNullOrderByPriorityAsc(isActive);
    if (menuList == null) {
      return null;
    }
    List<MenuDTO> lstRes = new ArrayList<>();
    for (Menu item : menuList) {
      lstRes.add(item.toDTO());
    }
    return lstRes;
  }

}

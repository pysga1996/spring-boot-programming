package vn.vissoft.ems.core.dto;

import lombok.Data;
import org.modelmapper.ModelMapper;
import vn.vissoft.ems.core.model.Menu;

import java.util.ArrayList;
import java.util.List;

@Data
public class MenuDTO {

  private Long menuId;
  private String labelKey;
  private List<MenuDTO> subMenus = new ArrayList<>();
  private String icon;
  private String path = NavigationDTO.BLANK_ZUL;
  private int counter;

  public MenuDTO() {
  }

  public MenuDTO(String labelKey) {
    this.labelKey = labelKey;
  }

  public MenuDTO(String labelKey, String icon) {
    this.labelKey = labelKey;
    this.icon = icon;
  }

  public Menu toEntity() {
    ModelMapper modelMapper = new ModelMapper();
    Menu menu = modelMapper.map(this, Menu.class);

    return menu;
  }

}

package vn.vissoft.ems.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.modelmapper.ModelMapper;
import vn.vissoft.ems.core.dto.MenuDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "menus")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "menu_id")
  private Integer menuId;
  @Column(name = "permission_id")
  private Integer permissionId;
  @Column(name = "label_key")
  private String labelKey;
  @Column(name = "description")
  private String description;
  @Column(name = "icon")
  private String icon;
  @Column(name = "path")
  private String path;
  @Column(name = "is_active")
  private Boolean isActive;
  @Column(name = "priority")
  private Integer priority;

  @OneToOne
  @JoinColumn(name = "parent_id")
  private Menu parent;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "parent", cascade = CascadeType.ALL)
  public List<Menu> subMenus;

  @Column(name = "created_at")
  @CreationTimestamp
  private Date createdAt;
  @Column(name = "updated_at")
  @UpdateTimestamp
  private Date updatedAt;

  public List<Menu> getSubMenus() {
    if (this.subMenus != null) {
      List<Menu> lst = new ArrayList<>();
      for (Menu sub : this.subMenus) {
        if (sub.getIsActive() != null && sub.getIsActive()) {
          lst.add(sub);
        }
      }
      this.subMenus = lst;
    }
    return this.subMenus;
  }

  public MenuDTO toDTO() {
    ModelMapper modelMapper = new ModelMapper();
    // config <=> load lazy
//    modelMapper.getConfiguration()
//            .setPropertyCondition(context ->
//                    !(context.getSource() instanceof PersistentCollection)
//            );
    MenuDTO menuDTO = modelMapper.map(this, MenuDTO.class);
    menuDTO.setCounter(menuDTO.getSubMenus() == null ? 0 : menuDTO.getSubMenus().size());

    return menuDTO;
  }

}

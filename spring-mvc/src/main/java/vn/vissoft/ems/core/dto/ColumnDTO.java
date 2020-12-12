package vn.vissoft.ems.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColumnDTO {

  private String title;
  private String colInDB;
  private String colInModel;
  private String align;
  private String width;
  private boolean sortASC;
  private boolean disabled;
}

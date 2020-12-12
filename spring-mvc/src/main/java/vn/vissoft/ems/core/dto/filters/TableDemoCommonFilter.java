package vn.vissoft.ems.core.dto.filters;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableDemoCommonFilter {

  private String category = "", name = "", nutrients = "";

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category == null ? "" : category.trim();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name == null ? "" : name.trim();
  }

  public String getNutrients() {
    return nutrients;
  }

  public void setNutrients(String nutrients) {
    this.nutrients = nutrients == null ? "" : nutrients.trim();
  }
}

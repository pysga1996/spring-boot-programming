package vn.vissoft.ems.core.controller;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import vn.vissoft.ems.core.model.Food;
import vn.vissoft.ems.core.model.FoodData;
import vn.vissoft.ems.core.model.FoodFilter;

import java.util.List;

public class FoodVM {

  private static final String footerMessage = "A Total of %d Food Items";
  List<Food> currentFood = FoodData.getAllFoods();
  private FoodFilter foodFilter = new FoodFilter();

  public ListModel<Food> getFoodModel() {
    return new ListModelList<Food>(currentFood);
  }

  @Command
  @NotifyChange({"foodModel", "footer"})
  public void changeFilter() {
    currentFood = FoodData.getFilterFoods(foodFilter);
  }

  public FoodFilter getFoodFilter() {
    return foodFilter;
  }
}

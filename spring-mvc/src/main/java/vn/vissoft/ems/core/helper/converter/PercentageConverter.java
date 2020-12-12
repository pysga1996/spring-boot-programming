package vn.vissoft.ems.core.helper.converter;


import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;

public class PercentageConverter implements Converter<String, Double, Component> {

  @Override
  public String coerceToUi(Double aDouble, Component component, BindContext bindContext) {
    return aDouble * 100 + "%";
  }

  @Override
  public Double coerceToBean(String s, Component component, BindContext bindContext) {
    //no need in our case
    return null;
  }
}

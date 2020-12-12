package vn.vissoft.ems.core.helper.converter;


import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;
import vn.vissoft.ems.core.dto.BsColorEnum;

/**
 * Convert integer to a bootstrap color
 */
public class IntColorConverter implements Converter<String, Integer, Component> {

  @Override
  public String coerceToUi(Integer index, Component component, BindContext bindContext) {
    return BsColorEnum.values()[index % BsColorEnum.values().length].getCssClass();
  }

  @Override
  public Integer coerceToBean(String s, Component component, BindContext bindContext) {
    //no need in our case
    return null;
  }
}

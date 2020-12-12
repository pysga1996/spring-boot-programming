package vn.vissoft.ems.core.helper.converter;


import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;
import vn.vissoft.ems.core.dto.TypeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Convert {@link TypeEnum} to a font awesome icon class
 */
public class TypeIconConverter implements Converter<String, TypeEnum, Component> {

  static private Map<TypeEnum, String> iconMap = new HashMap() {{
    put(TypeEnum.Customer, "z-icon-group");
    put(TypeEnum.Order, "z-icon-list");
    put(TypeEnum.Task, "z-icon-tasks");
    put(TypeEnum.Request, "z-icon-phone");
  }};

  @Override
  public String coerceToUi(TypeEnum type, Component component, BindContext bindContext) {
    return iconMap.get(type);
  }

  @Override
  public TypeEnum coerceToBean(String s, Component component, BindContext bindContext) {
    //no need in our case
    return null;
  }
}

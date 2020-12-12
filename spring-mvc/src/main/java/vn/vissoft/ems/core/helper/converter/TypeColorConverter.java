package vn.vissoft.ems.core.helper.converter;


import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;
import vn.vissoft.ems.core.dto.BsColorEnum;
import vn.vissoft.ems.core.dto.TypeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Convert {@link TypeEnum} to a font awesome icon class
 */
public class TypeColorConverter implements Converter<String, TypeEnum, Component> {

  static private Map<TypeEnum, String> borderColorMap = new HashMap() {{
    put(TypeEnum.Customer, BsColorEnum.PRIMARY.getCssClass());
    put(TypeEnum.Order, BsColorEnum.WARNING.getCssClass());
    put(TypeEnum.Task, BsColorEnum.DANGER.getCssClass());
    put(TypeEnum.Request, BsColorEnum.SUCCESS.getCssClass());
  }};

  @Override
  public String coerceToUi(TypeEnum type, Component component, BindContext bindContext) {
    return borderColorMap.get(type);
  }

  @Override
  public TypeEnum coerceToBean(String s, Component component, BindContext bindContext) {
    //no need in our case
    return null;
  }
}

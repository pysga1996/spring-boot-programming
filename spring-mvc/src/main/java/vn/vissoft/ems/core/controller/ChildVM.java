package vn.vissoft.ems.core.controller;

import lombok.Data;
import org.zkoss.bind.annotation.Init;

//@VariableResolver(DelegatingVariableResolver.class)
@Data
public class ChildVM {

  private String text;

  @Init
  public void Init() {
    setText("ngoc");
  }
}

package vn.vissoft.ems.core.controller;

import org.zkoss.bind.annotation.Init;
import vn.vissoft.ems.core.dto.StateDTO;
import vn.vissoft.ems.core.dto.TypeEnum;

import java.util.LinkedList;
import java.util.List;

public class StateVM {

  private List<StateDTO> states;

  @Init
  public void init() {
    queryStates();
  }

  private void queryStates() {
    states = new LinkedList<>();
    for (int i = 0; i < 4; i++) {
      StateDTO state = new StateDTO();
      state.setType(TypeEnum.values()[i % 4]);
      state.setValue(1317 * (i + 1));
      state.setRatio(0.329);
      states.add(state);
    }
  }

  public List<StateDTO> getStates() {
    return states;
  }

}

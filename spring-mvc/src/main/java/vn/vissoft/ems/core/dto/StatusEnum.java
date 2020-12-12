package vn.vissoft.ems.core.dto;

public enum StatusEnum {
  HOLD("On Hold"), PROGRESS("In Progress"), OUTDATED("Outdated");

  private String text;

  StatusEnum(String text) {
    this.text = text;
  }


  @Override
  public String toString() {
    return this.text;
  }
}

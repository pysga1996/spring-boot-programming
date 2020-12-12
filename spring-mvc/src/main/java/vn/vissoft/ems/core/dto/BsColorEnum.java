package vn.vissoft.ems.core.dto;

/**
 * custom color code corresponding to bootstrap
 */
public enum BsColorEnum {
  PRIMARY("#0093F9"), SUCCESS("#15CAB4"), WARNING("#FFA516"), DANGER("#FF4051"), INFO(
      "#9665ED"), SECONDARY("#B2DEFD");

  private String hexCode;

  BsColorEnum(String hexCode) {
    this.hexCode = hexCode;
  }

  public String getCssClass() {
    return toString().toLowerCase();
  }

  public String getHexCode() {
    return hexCode;
  }

  public static BsColorEnum getColor(int index) {
    return values()[index % values().length];
  }
}

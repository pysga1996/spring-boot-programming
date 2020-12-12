package vn.vissoft.ems.core.model;

import javax.persistence.*;

@Entity
@Table(name = "data_charts")
public class Chart {

  private Integer id;
  private String legend;
  private String category;
  private Float value;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Column(name = "legend")
  public String getLegend() {
    return legend;
  }

  public void setLegend(String legend) {
    this.legend = legend;
  }

  @Column(name = "value")
  public Float getValue() {
    return value;
  }

  @Column(name = "category")
  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public void setValue(Float value) {
    this.value = value;
  }
}

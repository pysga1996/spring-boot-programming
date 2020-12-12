package vn.vissoft.ems.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import vn.vissoft.ems.core.model.Attributes;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttributesDTO {

  private Integer attributeId;
  private String key;
  private String value;
  private String type;
  private Boolean isActive;

  public Attributes toEntity() {
    ModelMapper modelMapper = new ModelMapper();
    Attributes att = modelMapper.map(this, Attributes.class);
    return att;
  }
}

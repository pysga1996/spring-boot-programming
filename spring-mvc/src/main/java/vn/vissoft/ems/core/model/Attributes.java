package vn.vissoft.ems.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import vn.vissoft.ems.core.dto.AttributesDTO;

import javax.persistence.*;

@Entity
@Table(name = "attributes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attributes {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "attribute_id")
  private Integer attributeId;
  @Column(name = "key")
  private String key;
  @Column(name = "value")
  private String value;
  @Column(name = "type")
  private String type;
  @Column(name = "is_active")
  private Boolean isActive;

  public AttributesDTO toDTO() {
    ModelMapper modelMapper = new ModelMapper();
    AttributesDTO attDTO = modelMapper.map(this, AttributesDTO.class);
    return attDTO;
  }
}

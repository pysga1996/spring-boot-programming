package vn.vissoft.ems.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vissoft.ems.core.helper.AppConst;
import vn.vissoft.ems.core.model.Attributes;
import vn.vissoft.ems.core.repo.AttributesRepository;

import java.util.Base64;

@Service
public class AttributesService {

  @Autowired
  private AttributesRepository attributesRepository;

  public Attributes findByKeyIgnoreCase(String key) {
    return attributesRepository.findByKeyIgnoreCase(key);
  }

  public byte[] getFavicon() {
    Attributes att = attributesRepository
        .findByKeyIgnoreCaseAndIsActiveTrue(AppConst.ATTRIBUTES.ATT_FAVICON_ICO);
    if (att != null) {
      String base64Image = att.getValue();
      if (base64Image != null) {
        return Base64.getDecoder().decode(base64Image);
      }
    }

    return null;
  }

  public String getAppTitle() {
    Attributes att = attributesRepository
        .findByKeyIgnoreCaseAndIsActiveTrue(AppConst.ATTRIBUTES.ATT_APP_TITLE);
    if (att != null) {
      return att.getValue();
    }
    return "";
  }
}

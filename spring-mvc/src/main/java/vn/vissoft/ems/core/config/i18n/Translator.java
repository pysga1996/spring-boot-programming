package vn.vissoft.ems.core.config.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class Translator {

  private static ReloadableResourceBundleMessageSource messageSource;

  @Autowired
  Translator(ReloadableResourceBundleMessageSource messageSource) {
    Translator.messageSource = messageSource;
  }

  public static String toLocale(String msg) {
    return messageSource.getMessage(msg, null, LocaleContextHolder.getLocale());
  }
}

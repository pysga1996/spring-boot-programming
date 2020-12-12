package vn.vissoft.ems.core;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.zkoss.zats.junit.AutoClient;
import org.zkoss.zats.junit.AutoEnvironment;
import org.zkoss.zats.mimic.ComponentAgent;
import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zul.Label;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EMSApplicationTest {

  @ClassRule
  public static AutoEnvironment env = new AutoEnvironment("src/test/webapp/WEB-INF",
      "src/main/webapp");

  @Rule
  public AutoClient client = env.autoClient();

  private DesktopAgent desktopAgent;

}

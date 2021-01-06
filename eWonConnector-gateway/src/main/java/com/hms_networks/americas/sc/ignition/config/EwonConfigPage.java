package com.hms_networks.americas.sc.ignition.config;

import com.inductiveautomation.ignition.gateway.model.IgnitionWebApp;
import com.inductiveautomation.ignition.gateway.web.components.RecordEditForm;
import com.inductiveautomation.ignition.gateway.web.models.ConfigCategory;
import com.inductiveautomation.ignition.gateway.web.models.DefaultConfigTab;
import com.inductiveautomation.ignition.gateway.web.models.IConfigTab;
import com.inductiveautomation.ignition.gateway.web.pages.IConfigPage;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.wicket.Application;

/** Class representation of Ewon Connector config page */
public class EwonConfigPage extends RecordEditForm {
  /** Model for Ewon Connector config tab/page */
  public static final IConfigTab CONFIG_TAB =
      DefaultConfigTab.builder()
          .category(ConfigCategory.TAGS)
          .name("ewon")
          .i18n("ewon.config.menutitle")
          .page(EwonConfigPage.class)
          .terms("ewon", "data mailbox", "dm")
          .build();

  /**
   * Create EwonConfigPage from Ignition web page
   *
   * @param configPage IConfigPage used to create EwonConfigPage
   */
  public EwonConfigPage(IConfigPage configPage) {
    super(
        configPage,
        null,
        null,
        ((IgnitionWebApp) Application.get())
            .getContext()
            .getPersistenceInterface()
            .find(EwonConnectorSettings.META, 0L));
  }

  /**
   * Get Ewon Connector config page menu location
   *
   * @return menu location
   */
  @Override
  public Pair<String, String> getMenuLocation() {
    return CONFIG_TAB.getMenuLocation();
  }
}

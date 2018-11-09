package org.imdc.ewon.config;

import com.inductiveautomation.ignition.gateway.model.IgnitionWebApp;
import com.inductiveautomation.ignition.gateway.web.components.RecordEditForm;
import com.inductiveautomation.ignition.gateway.web.models.ConfigCategory;
import com.inductiveautomation.ignition.gateway.web.models.DefaultConfigTab;
import com.inductiveautomation.ignition.gateway.web.models.IConfigTab;
import com.inductiveautomation.ignition.gateway.web.pages.IConfigPage;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.wicket.Application;

public class EwonConfigPage extends RecordEditForm {
	public static final IConfigTab CONFIG_TAB = DefaultConfigTab.builder()
			.category(ConfigCategory.TAGS)
			.name("ewon")
			.i18n("ewon.config.menutitle")
			.page(EwonConfigPage.class)
			.terms("ewon", "data mailbox", "dm")
			.build();
	
	public EwonConfigPage(IConfigPage configPage) {
		super(
			configPage,
			null,
			null,
			((IgnitionWebApp) Application.get()).getContext()
				.getPersistenceInterface()
				.find(EwonConnectorSettings.META, 0L)
		);
	}
	@Override
	public Pair<String, String> getMenuLocation() {
		return CONFIG_TAB.getMenuLocation();
	}
}

package org.imdc.ewon.config;

import com.inductiveautomation.ignition.gateway.history.HistoryFlavor;
import com.inductiveautomation.ignition.gateway.localdb.persistence.FormMeta;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import com.inductiveautomation.ignition.gateway.web.components.RecordEditMode;
import com.inductiveautomation.ignition.gateway.web.components.editors.AbstractEditor;
import com.inductiveautomation.ignition.gateway.web.models.IRecordFieldComponent;
import com.inductiveautomation.ignition.gateway.web.models.LenientResourceModel;
import org.apache.wicket.Application;
import org.apache.wicket.markup.html.form.DropDownChoice;
import simpleorm.dataset.SFieldMeta;
import simpleorm.dataset.SRecordInstance;

import java.util.List;

public class TagHistorySourceEditor extends AbstractEditor {

    @SuppressWarnings("unchecked")
    public TagHistorySourceEditor(String id, FormMeta formMeta, RecordEditMode editMode, SRecordInstance record) {
        super(id, formMeta, editMode, record);

        TagHistoryDropdownChoice dropdown = new TagHistoryDropdownChoice("editor", record);

        formMeta.installValidators(dropdown);

        dropdown.setLabel(new LenientResourceModel(formMeta.getFieldNameKey()));

        add(dropdown);

    }

    private class TagHistoryDropdownChoice extends DropDownChoice<String> implements IRecordFieldComponent {

        @SuppressWarnings("unchecked")
        public TagHistoryDropdownChoice(String id, SRecordInstance record) {
            super(id);

            GatewayContext context = (GatewayContext) Application.get();

            List<String> stores = context.getHistoryManager().getStores(HistoryFlavor.SQLTAG);
            setChoices(stores);
        }

        public SFieldMeta getFieldMeta() {
            return getFormMeta().getField();
        }
    }
}
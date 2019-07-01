package org.imdc.ewon.config;

import com.inductiveautomation.ignition.gateway.localdb.persistence.FormMeta;
import com.inductiveautomation.ignition.gateway.web.components.RecordEditMode;
import com.inductiveautomation.ignition.gateway.web.components.editors.IEditorSource;
import org.apache.wicket.Component;
import simpleorm.dataset.SRecordInstance;

public class TagHistoryListEditorSource implements IEditorSource {
   static final TagHistoryListEditorSource _instance = new TagHistoryListEditorSource();

   public static TagHistoryListEditorSource getSharedInstance() {
      return _instance;
   }

   public TagHistoryListEditorSource() {

   }

   @Override
   public Component newEditorComponent(String id, RecordEditMode editMode, SRecordInstance record, FormMeta formMeta) {
      return new TagHistorySourceEditor(id, formMeta, editMode, record);
   }
}

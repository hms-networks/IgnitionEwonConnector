package com.hms_networks.config;

import com.inductiveautomation.ignition.gateway.localdb.persistence.FormMeta;
import com.inductiveautomation.ignition.gateway.web.components.RecordEditMode;
import com.inductiveautomation.ignition.gateway.web.components.editors.IEditorSource;
import org.apache.wicket.Component;
import simpleorm.dataset.SRecordInstance;

/**
 * IEditorSource implementation for Ewon Connector tag history provider source list.
 * @see IEditorSource
 */
public class TagHistoryListEditorSource implements IEditorSource {
   /**
    * Shared constant instance of this TagHistoryListEditorSource.
    */
   static final TagHistoryListEditorSource _instance = new TagHistoryListEditorSource();

   /**
    * Get the shared instance of this TagHistoryListEditorSource.
    * @return shared instance
    */
   public static TagHistoryListEditorSource getSharedInstance() {
      return _instance;
   }

   /**
    * Default constructor for TagHistoryListEditorSource. Performs no operations.
    */
   public TagHistoryListEditorSource() {

   }

   /**
    * Creates a new TagHistorySourceEditor with given information.
    * @param id identifier
    * @param editMode edit mode
    * @param record record instance
    * @param formMeta form meta
    * @return created TagHistorySourceEditor
    */
   @Override
   public Component newEditorComponent(String id, RecordEditMode editMode, SRecordInstance record,
         FormMeta formMeta) {
      return new TagHistorySourceEditor(id, formMeta, editMode, record);
   }
}

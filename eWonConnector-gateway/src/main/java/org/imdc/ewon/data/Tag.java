package org.imdc.ewon.data;

public class Tag {
   int id;
   String name;
   String dataType;
   String description;
   String alarmHint;
   Object value;
   String quality;
   int ewonTagId;
   DataPoint[] history;

   public int getId() {
      return id;
   }

   public String getName() {
      return name;
   }

   public String getDataType() {
      return dataType;
   }

   public String getDescription() {
      return description;
   }

   public String getAlarmHint() {
      return alarmHint;
   }

   public Object getValue() {
      return value;
   }

   public String getQuality() {
      return quality;
   }

   public int getEwonTagId() {
      return ewonTagId;
   }

   public DataPoint[] getHistory() {
      return history;
   }
}

package org.imdc.ewon.data;

/**
 * Object representing a Tag
 */
public class Tag {

   /**
    * Tag identifier
    */
   int id;

   /**
    * Tag name
    */
   String name;

   /**
    * Tag data type
    */
   String dataType;

   /**
    * Tag description
    */
   String description;

   /**
    * Tag alarm hint
    */
   String alarmHint;

   /**
    * Tag value
    */
   Object value;

   /**
    * Tag data quality
    */
   String quality;

   /**
    * Tag identifier on Ewon
    */
   int ewonTagId;

   /**
    * Tag history data points
    */
   DataPoint[] history;

   /**
    * Get tag identifier
    * @return tag identifier
    */
   public int getId() {
      return id;
   }

   /**
    * Get tag name
    * @return tag name
    */
   public String getName() {
      return name;
   }

   /**
    * Get tag data type
    * @return tag data type
    */
   public String getDataType() {
      return dataType;
   }

   /**
    * Get tag description
    * @return tag description
    */
   public String getDescription() {
      return description;
   }

   /**
    * Get tag alarm hint
    * @return tag alarm hint
    */
   public String getAlarmHint() {
      return alarmHint;
   }

   /**
    * Get tag value
    * @return tag value
    */
   public Object getValue() {
      return value;
   }

   /**
    * Get tag data quality
    * @return tag data quality
    */
   public String getQuality() {
      return quality;
   }

   /**
    * Get tag identifier on Ewon
    * @return tag identifier on Ewon
    */
   public int getEwonTagId() {
      return ewonTagId;
   }

   /**
    * Get array of tag history data points
    * @return array of tag history data points
    */
   public DataPoint[] getHistory() {
      return history;
   }
}

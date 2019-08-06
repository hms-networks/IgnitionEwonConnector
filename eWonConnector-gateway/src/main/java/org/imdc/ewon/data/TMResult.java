package org.imdc.ewon.data;

import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * Class representation of a result from Talk2M
 */
public class TMResult {
   /**
    * Hash map of tag values in result
    */
   HashMap<String, String> TagValues;

   /**
    * Number of columns in Talk2M response
    */
   static final int NUM_COLUMNS = 6;

   /**
    * Index of tag name in Talk2M response
    */
   static final int TAG_NAME_INDEX = 1;

   /**
    * Index of tag value in Talk2M response
    */
   static final int TAG_VALUE_INDEX = 2;

   /**
    * Split character for Talk2M response
    */
   static final String SPLIT_CHAR = ";";

   /**
    * This constructor parses a Talk2M response and stores the Tag Names and Tag Values in a hashmap
    *
    * @param data T2M response string
    */
   public TMResult(String data) {
      String line = "";
      TagValues = new HashMap<String, String>();

      try (BufferedReader br = new BufferedReader(new StringReader(data))) {

         // read the first line, column header information that can be discarded
         br.readLine();

         // Read the live tag values and store them into the HashMap
         while ((line = br.readLine()) != null) {
            String[] TagInfo = line.split(SPLIT_CHAR);
            if (TagInfo.length == NUM_COLUMNS) {
               TagValues.put(TagInfo[TAG_NAME_INDEX].replaceAll("\"", ""),
                     TagInfo[TAG_VALUE_INDEX]);
            }
         }

      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   /**
    * This function looks up the value for a given tag name
    *
    * @param tagName Tag Name
    * @return Tag Value
    */
   public String getTagValue(String tagName) {
      return TagValues.get(tagName);
   }
}

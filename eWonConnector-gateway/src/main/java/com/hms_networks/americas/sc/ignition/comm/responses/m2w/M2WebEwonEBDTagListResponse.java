package com.hms_networks.americas.sc.ignition.comm.responses.m2w;

import com.opencsv.bean.CsvToBeanBuilder;
import java.io.StringReader;
import java.util.List;

/**
 * Object for an M2Web Ewon EBD tag list response.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public class M2WebEwonEBDTagListResponse extends M2WebEwonEBDResponse {

  /**
   * The separator character used to separate columns/fields in the EBD tag list response.
   *
   * @since 1.0.0
   */
  private static final char EWON_EBD_TAGLIST_SEPARATOR_CHAR = ';';

  /**
   * The list of tags from the EBD tag list response.
   *
   * @since 1.0.0
   */
  private final List<M2WebEwonEBDTag> tags;

  /**
   * Private constructor for an M2Web Ewon EBD tag list response.
   *
   * @param tags The list of tags from the EBD tag list response.
   * @since 1.0.0
   */
  private M2WebEwonEBDTagListResponse(List<M2WebEwonEBDTag> tags) {
    this.tags = tags;
  }

  /**
   * Gets the list of tags from the EBD tag list response.
   *
   * @return The list of tags from the EBD tag list response.
   * @since 1.0.0
   */
  public List<M2WebEwonEBDTag> getTags() {
    return tags;
  }

  /**
   * Gets an instance of {@link M2WebEwonEBDTagListResponse} from a response string.
   *
   * @param responseString The response string to parse.
   * @return An instance of {@link M2WebEwonEBDTagListResponse} parsed from the response string.
   * @throws IllegalStateException If a necessary parameter was not specified. Currently this means
   *     that both the mapping strategy and the bean type are not set, so it is impossible to
   *     determine a mapping strategy.
   * @since 1.0.0
   */
  public static M2WebEwonEBDTagListResponse getFromString(String responseString)
      throws IllegalStateException {
    // Parse CSV response into list of tag objects
    M2WebEwonEBDTagListResponse response;
    try {
      List<M2WebEwonEBDTag> tags =
          new CsvToBeanBuilder<M2WebEwonEBDTag>(new StringReader(responseString))
              .withSeparator(EWON_EBD_TAGLIST_SEPARATOR_CHAR)
              .withType(M2WebEwonEBDTag.class)
              .build()
              .parse();

      response = new M2WebEwonEBDTagListResponse(tags);
    } catch (Exception e1) {
      try {
        response = getFromJson(responseString, M2WebEwonEBDTagListResponse.class);
      } catch (Exception e2) {
        throw e1;
      }
    }

    return response;
  }
}

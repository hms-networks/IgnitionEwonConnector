package com.hms_networks.americas.sc.ignition.comm.responses.m2w;

import com.opencsv.bean.CsvToBeanBuilder;
import java.io.StringReader;
import java.util.List;

/**
 * Object for an M2Web Ewon EBD instant values response.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public class M2WebEwonEBDInstantValuesResponse {

  /**
   * The separator character used to separate columns/fields in the EBD instant values response.
   *
   * @since 1.0.0
   */
  private static final char EWON_EBD_INSTANTVALUES_SEPARATOR_CHAR = ';';

  /**
   * The list of instant values from the EBD instant values response.
   *
   * @since 1.0.0
   */
  private final List<M2WebEwonEBDInstantValue> instantValues;

  /**
   * Private constructor for an M2Web Ewon EBD instant values response.
   *
   * @param instantValues The list of instant values from the EBD instant values response.
   * @since 1.0.0
   */
  private M2WebEwonEBDInstantValuesResponse(List<M2WebEwonEBDInstantValue> instantValues) {
    this.instantValues = instantValues;
  }

  /**
   * Gets the list of instant values from the EBD tag list response.
   *
   * @return The list of instant values from the EBD tag list response.
   * @since 1.0.0
   */
  public List<M2WebEwonEBDInstantValue> getInstantValues() {
    return instantValues;
  }

  /**
   * Gets an instance of {@link M2WebEwonEBDInstantValuesResponse} from a response string.
   *
   * @param responseString The response string to parse.
   * @return An instance of {@link M2WebEwonEBDInstantValuesResponse} parsed from the response
   *     string.
   * @throws IllegalStateException If a necessary parameter was not specified. Currently this means
   *     that both the mapping strategy and the bean type are not set, so it is impossible to
   *     determine a mapping strategy.
   * @since 1.0.0
   */
  public static M2WebEwonEBDInstantValuesResponse getFromString(String responseString)
      throws IllegalStateException {
    // Parse CSV response into list of tag objects
    List<M2WebEwonEBDInstantValue> instantValues =
        new CsvToBeanBuilder<M2WebEwonEBDInstantValue>(new StringReader(responseString))
            .withSeparator(EWON_EBD_INSTANTVALUES_SEPARATOR_CHAR)
            .withType(M2WebEwonEBDInstantValue.class)
            .build()
            .parse();

    return new M2WebEwonEBDInstantValuesResponse(instantValues);
  }
}

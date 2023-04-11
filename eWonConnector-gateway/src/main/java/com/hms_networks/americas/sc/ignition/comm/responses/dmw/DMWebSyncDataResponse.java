package com.hms_networks.americas.sc.ignition.comm.responses.dmw;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * JSON object for a DMWeb syncdata response. This class extends the {@link DMWebGetDataResponse}
 * class and adds the additional {@link DMWebEwon} data from the getewons request.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public class DMWebSyncDataResponse extends DMWebGetDataResponse {

  /**
   * The transaction ID from the syncdata response.
   *
   * @since 1.0.0
   */
  private long transactionId;

  /**
   * Gets the transaction ID from the syncdata response.
   *
   * @since 1.0.0
   */
  public long getTransactionId() {
    return transactionId;
  }

  /**
   * Gets an instance of {@link DMWebSyncDataResponse} from a JSON string.
   *
   * @param json The JSON string to parse.
   * @return An instance of {@link DMWebSyncDataResponse} parsed from the JSON string.
   * @throws JsonSyntaxException if the JSON string is not valid or does not match the {@link
   *     DMWebSyncDataResponse} class.
   * @since 1.0.0
   */
  public static DMWebSyncDataResponse getFromJson(String json) throws JsonSyntaxException {
    return new Gson().fromJson(json, DMWebSyncDataResponse.class);
  }
}

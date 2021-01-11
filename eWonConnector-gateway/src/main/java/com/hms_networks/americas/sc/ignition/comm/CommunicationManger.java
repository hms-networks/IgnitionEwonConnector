package com.hms_networks.americas.sc.ignition.comm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.hms_networks.americas.sc.ignition.EwonConsts;
import com.hms_networks.americas.sc.ignition.EwonUtil;
import com.hms_networks.americas.sc.ignition.config.SyncMode;
import com.hms_networks.americas.sc.ignition.data.EwonData;
import com.hms_networks.americas.sc.ignition.data.EwonsData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;
import com.inductiveautomation.ignition.common.FormatUtil;

/** Ewon Connector communication manager */
public class CommunicationManger {
  /** Communication manager logger */
  private Logger logger = LoggerFactory.getLogger("Ewon.CommManager");

  /*/**
   * HTTP client
   */
  // private final OkHttpClient client;

  /** JSON string and object serialization library */
  private final Gson gson = new Gson();

  /** Authentication information */
  private AuthInfo authInfo;

  /** Synchronization mode */
  private SyncMode mode = SyncMode.GetData;

  /** Communication manager default constructor. Performs no operations. */
  public CommunicationManger() {}

  /**
   * Set communication authentication information.
   *
   * @param info authentication information to use
   */
  public void setAuthInfo(AuthInfo info) {
    this.authInfo = info;
  }

  /**
   * Create a full HTTP call with given information.
   *
   * @param type type of call (i.e. Talk2M, DataMailbox)
   * @param function function of call (i.e. GetData, GetEwons)
   * @param params HTTP URL parameters
   * @return created HTTP call as string
   */
  protected String buildCall(String type, String function, String... params) {
    // Build base HTTP call
    StringBuilder sb = new StringBuilder(type);
    sb.append(function).append("?");

    // Add authentication information to HTTP call
    sb.append(authInfo.toGetString());

    // Add each parameter to HTTP call
    if (params != null) {
      for (int i = 0; i < params.length; i += 2) {
        sb.append("&").append(params[i]);
        if (params[i + 1] != null) {
          sb.append("=").append(params[i + 1]);
        }
      }
    }

    // Get string from string builder, log and return
    String ret = sb.toString();
    return ret;
  }

  /**
   * Create a DataMailbox call with given information.
   *
   * @param function DataMailbox call function
   * @param params DataMailbox call parameters
   * @return created DataMailbox call
   */
  protected String buildDMCall(String function, String... params) {
    return buildCall(EwonConsts.URL_DM, function, params);
  }

  /**
   * Create a Talk2M call with given information.
   *
   * @param directory Talk2M call directory
   * @param function Talk2M call function
   * @param params Talk2M call parameters
   * @return created Talk2M call
   */
  protected String buildT2MCall(String directory, String function, String... params) {
    return buildCall((EwonConsts.URL_T2M + directory), function, params);
  }

  /**
   * Log the results of a call.
   *
   * @param start call start timestamp
   * @param call call body as String
   * @param body result body as String
   */
  protected void logResults(long start, String call, String body) {
    logger.debug("[{}] Call finished in {}", call, FormatUtil.formatDurationSince(start));
    logger.trace("[{}] Results: {}", call, body);
  }

  /**
   * Query all Ewon devices.
   *
   * @return resulting data for Ewon devices
   * @throws Exception if a query call fails
   */
  public EwonsData queryEwonDevices() throws Exception {
    long start = System.currentTimeMillis();
    String body = EwonUtil.httpGet(buildDMCall(EwonConsts.DM_CALL_GETEWONS));
    logResults(start, EwonConsts.DM_CALL_GETEWONS, body);
    return gson.fromJson(body, EwonsData.class);
  }

  /**
   * Query an Ewon device with given ID.
   *
   * @param id Ewon identifier
   * @return resulting Ewon data
   * @throws Exception if query call fail
   */
  public EwonData queryEwon(Integer id) throws Exception {
    long start = System.currentTimeMillis();
    String body =
        EwonUtil.httpGet(
            buildDMCall(EwonConsts.DM_CALL_GETEWON, EwonConsts.DM_PARAM_ID, id.toString()));
    logResults(start, EwonConsts.DM_CALL_GETEWON, body);
    return gson.fromJson(body, EwonData.class);
  }

  /**
   * Get Ewons data using given information.
   *
   * @param ewonId Ewon identifier
   * @param tagId tag identifier
   * @param limit limit
   * @param fromTime from time
   * @return resulting Ewons data
   * @throws Exception if DataMailbox call fails
   */
  public EwonsData getData(Integer ewonId, Integer tagId, Integer limit, Date fromTime)
      throws Exception {
    // Store start timestamp
    long start = System.currentTimeMillis();

    // Create list for parameters
    List<String> params = new ArrayList<>();

    // Verify integrity of Ewon identifer and add to parameter list
    if (ewonId != null) {
      params.add(EwonConsts.DM_PARAM_EWONID);
      params.add(ewonId.toString());
    }

    // Verify integrity of tag identifier and add to parameter list
    if (tagId != null) {
      params.add(EwonConsts.DM_PARAM_TAGID);
      params.add(tagId.toString());
    }

    // Verify integrity of limit and add to parameter list
    if (limit != null) {
      params.add(EwonConsts.DM_PARAM_LIMIT);
      params.add(limit.toString());
    }

    // Verify integrity of from time and add to parameter list
    if (fromTime != null) {
      params.add(EwonConsts.DM_PARAM_FROM);
      params.add(EwonUtil.toString(fromTime).replace(":", "%3A"));
    }

    // Build and perform DataMailbox call with given parameters
    String body =
        EwonUtil.httpGet(
            buildDMCall(
                EwonConsts.DM_CALL_GETDATA,
                params.size() > 0 ? params.toArray(new String[params.size()]) : null));

    // Log results from DataMailbox call and return deserialized data
    logResults(start, EwonConsts.DM_CALL_GETDATA, body);
    return gson.fromJson(body, EwonsData.class);
  }

  /**
   * Synchronizes data with given last transaction identifier. If <code>transactionId</code> equals
   * null, continues without.
   *
   * @param transactionId last transaction identifier
   * @return resulting Ewons data
   * @throws Exception if DataMailbox call fails
   */
  public EwonsData syncData(Long transactionId) throws Exception {
    // Store start timestamp
    long start = System.currentTimeMillis();

    // Build and perform DataMailbox call
    String body =
        EwonUtil.httpGet(
            transactionId == null
                ? buildDMCall(
                    EwonConsts.DM_CALL_SYNCDATA, EwonConsts.DM_PARAM_CREATE_TRANSACTION, null)
                : buildDMCall(
                    EwonConsts.DM_CALL_SYNCDATA,
                    EwonConsts.DM_PARAM_CREATE_TRANSACTION,
                    null,
                    EwonConsts.DM_PARAM_LAST_TID,
                    transactionId.toString()));

    // Log results from DataMailbox call and return deserialized data
    logResults(start, EwonConsts.DM_CALL_SYNCDATA, body);
    return gson.fromJson(body, EwonsData.class);
  }

  /**
   * Write tag value for given tag name on given device using Talk2M
   *
   * @param device Ewon device
   * @param tagName tag name
   * @param tagValue tag value
   * @throws Exception if Talk2M call fails
   */
  public void writeTag(String device, String tagName, String tagValue) throws Exception {
    // Create list for parameters
    List<String> params = new ArrayList<>();

    // Build base Talk2M call string
    String directory = EwonConsts.T2M_DIR_GET + device + "/" + EwonConsts.T2M_DIR_RCGI;

    // Add tag name to parameters list
    params.add(EwonConsts.T2M_PARAM_TAGNAME1);
    params.add(tagName);

    // Add tag value to parameters list
    params.add(EwonConsts.T2M_PARAM_TAGVALUE1);
    params.add(tagValue);

    // Add Talk2M API key (from authentication information) to parameters list
    params.add(EwonConsts.T2M_M2W_DEVKEY);
    params.add(authInfo.getDevId());

    // Build and perform Talk2M call
    EwonUtil.httpGet(
        buildT2MCall(
            directory,
            EwonConsts.T2M_CALL_UPDATETAGFORM,
            params.toArray(new String[params.size()])));
  }

  /**
   * Get live data for given device using Talk2M
   *
   * @param device device
   * @return resulting live data
   * @throws Exception if Talk2M call fails
   */
  public String getLiveData(String device) throws Exception {
    // Create list for parameters
    List<String> params = new ArrayList<>();

    // Build base Talk2M call string
    String directory = EwonConsts.T2M_DIR_GET + device + "/" + EwonConsts.T2M_DIR_RCGI;

    // Add Talk2M API key (from authentication information) to parameters list
    params.add(EwonConsts.T2M_M2W_DEVKEY);
    params.add(authInfo.getDevId());

    // Build Talk2M call
    params.add(EwonConsts.T2M_PARAM_EXPORTBLOCK);
    params.add(EwonConsts.T2M_PARAM_TAGVALUES);

    // Execute built Talk2M call
    return EwonUtil.httpGet(
        buildT2MCall(
            directory, EwonConsts.T2M_CALL_PARAMFORM, params.toArray(new String[params.size()])));
  }

  /**
   * Perform synchronization of Ewons data
   *
   * @param token last transaction identifier/from time
   * @return resulting Ewons data
   * @throws Exception if DataMailbox call fails
   */
  public EwonsData sync(Object token) throws Exception {
    if (mode == SyncMode.GetData) {
      return getData(null, null, null, (Date) token);
    } else {
      return syncData((Long) token);
    }
  }
}

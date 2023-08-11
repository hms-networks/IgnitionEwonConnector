package com.hms_networks.americas.sc.ignition.data.tagwrite;

import com.hms_networks.americas.sc.ignition.comm.M2WebCommunicationManager;
import com.hms_networks.americas.sc.ignition.comm.responses.m2w.M2WebEwon;
import com.hms_networks.americas.sc.ignition.comm.responses.m2w.M2WebEwonEBDTag;
import com.hms_networks.americas.sc.ignition.comm.responses.m2w.M2WebEwonUpdateTagValuesResponse;
import com.hms_networks.americas.sc.ignition.config.EwonConnectorSettings;
import com.inductiveautomation.ignition.common.model.values.QualityCode;
import com.inductiveautomation.ignition.common.tags.model.TagPath;
import com.inductiveautomation.ignition.gateway.tags.managed.ManagedTagProvider;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.collections4.KeyValue;
import org.apache.commons.collections4.keyvalue.DefaultKeyValue;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for managing buffered tag writes in the Ignition Ewon Connector. This class buffers tag
 * writes for a configurable amount of time to prevent flooding the Ewon with tag write requests.
 *
 * @since 2.0.0
 * @version 1.0.0
 * @author HMS Networks, MU Americas Solution Center
 */
public class BufferedTagWriteManager {

  /**
   * Log handler for {@link BufferedTagWriteManager}.
   *
   * @since 1.0.0
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(BufferedTagWriteManager.class);

  /**
   * The minimum buffer length (in milliseconds) for tag writes. If the buffer length is set to a
   * value less than this, tag write buffering will be disabled.
   *
   * @since 1.0.0
   */
  private static final long MINIMUM_WRITE_BUFFER_LENGTH_MS = 100;

  /**
   * The buffer length (in milliseconds) for tag writes. If the buffer length is set to {@link
   * EwonConnectorSettings#TAG_WRITE_BUFFER_LENGTH_MS_DISABLED}, tag writes will not be buffered.
   *
   * @since 1.0.0
   */
  private static long writeBufferLengthMilliseconds =
      EwonConnectorSettings.TAG_WRITE_BUFFER_LENGTH_MS_DISABLED;

  /**
   * The queue of buffered tag writes. This queue is sorted by the buffered write time, with the
   * oldest write at the front of the queue.
   *
   * @since 1.0.0
   */
  private static final Queue<BufferedTagWrite> BUFFERED_TAG_WRITE_QUEUE = new PriorityQueue<>();

  /**
   * The thread that processes buffered tag writes. This thread is started when the first buffered
   * tag write is added to the queue, and is stopped when the queue is empty. This thread is
   * responsible for waiting the configured buffer length, and then writing all tags in the queue
   * that are ready to be written.
   *
   * @since 1.0.0
   */
  private static Thread bufferedTagWriteProcessingThread = null;

  /**
   * Processes a tag write using the specified parameters. If the tag write buffer length is
   * disabled, the tag will be written immediately. Otherwise, the tag write will be buffered.
   *
   * <p>If the tag write buffer length is below the minimum buffer length defined in {@link
   * #MINIMUM_WRITE_BUFFER_LENGTH_MS}, the tag write buffer length will be disabled.
   *
   * @param managedTagProvider the managed tag provider where the tag is located
   * @param connectorSettings the connector settings
   * @param m2WebEwon the M2Web Ewon object for the Ewon where the tag is located
   * @param m2WebEwonEBDTag the M2Web Ewon EBD tag object for the tag to write
   * @param tagPath the managed tag provider tag path
   * @param tagValue the tag value to write
   * @since 1.0.0
   */
  public static void writeTag(
      ManagedTagProvider managedTagProvider,
      EwonConnectorSettings connectorSettings,
      M2WebEwon m2WebEwon,
      M2WebEwonEBDTag m2WebEwonEBDTag,
      TagPath tagPath,
      Object tagValue) {
    // Update write buffer length
    writeBufferLengthMilliseconds = connectorSettings.getTagWriteBufferLengthMs();
    if (writeBufferLengthMilliseconds != EwonConnectorSettings.TAG_WRITE_BUFFER_LENGTH_MS_DISABLED
        && writeBufferLengthMilliseconds < MINIMUM_WRITE_BUFFER_LENGTH_MS) {
      writeBufferLengthMilliseconds = EwonConnectorSettings.TAG_WRITE_BUFFER_LENGTH_MS_DISABLED;
    }

    // Write tag immediately if buffer length is disabled, otherwise buffer tag write
    if (writeBufferLengthMilliseconds
        == EwonConnectorSettings.TAG_WRITE_BUFFER_LENGTH_MS_DISABLED) {
      writeTagImmediately(
          managedTagProvider, connectorSettings, m2WebEwon, m2WebEwonEBDTag, tagPath, tagValue);
    } else {
      writeTagBuffered(
          managedTagProvider, connectorSettings, m2WebEwon, m2WebEwonEBDTag, tagPath, tagValue);
    }
  }

  /**
   * Processes an immediate tag write using the specified parameters. This method will write the tag
   * immediately, without buffering.
   *
   * @param managedTagProvider the managed tag provider where the tag is located
   * @param connectorSettings the connector settings
   * @param m2WebEwon the M2Web Ewon object for the Ewon where the tag is located
   * @param m2WebEwonEBDTag the M2Web Ewon EBD tag object for the tag to write
   * @param tagPath the managed tag provider tag path
   * @param tagValue the tag value to write
   * @since 1.0.0
   */
  private static void writeTagImmediately(
      ManagedTagProvider managedTagProvider,
      EwonConnectorSettings connectorSettings,
      M2WebEwon m2WebEwon,
      M2WebEwonEBDTag m2WebEwonEBDTag,
      TagPath tagPath,
      Object tagValue) {
    // Create tag write callback
    FutureCallback<M2WebEwonUpdateTagValuesResponse> updateTagValuesResponseFutureCallback =
        new FutureCallback<>() {
          @Override
          public void completed(M2WebEwonUpdateTagValuesResponse m2WebEwonUpdateTagValuesResponse) {
            // Update tag value in managed tag provider
            managedTagProvider.updateValue(tagPath.toString(), tagValue, QualityCode.Good);

            // Log debug message
            if (connectorSettings.isDebugEnabled()) {
              LOGGER.debug(
                  "Tag write performed (unbuffered) for tag [{}] with value [{}].",
                  tagPath,
                  tagValue.toString());
            }
          }

          @Override
          public void failed(Exception e) {
            // Update tag value in managed tag provider
            managedTagProvider.updateValue(
                tagPath.toString(), tagValue, QualityCode.Error_Exception);

            // Log error message
            LOGGER.error(
                "Tag write failed (unbuffered) for tag [{"
                    + tagPath
                    + "}] with value [{"
                    + tagValue.toString()
                    + "}].",
                e);
          }

          @Override
          public void cancelled() {
            // Update tag value in managed tag provider
            managedTagProvider.updateValue(
                tagPath.toString(), tagValue, QualityCode.Error_ScriptEval);

            // Log error message
            LOGGER.error(
                "Tag write cancelled (unbuffered) for tag [{}] with value [{}].",
                tagPath,
                tagValue.toString());
          }
        };

    // Write tag immediately
    List<KeyValue<String, Object>> tagNameValuePairs =
        List.of(new DefaultKeyValue<>(m2WebEwonEBDTag.getName(), tagValue));
    M2WebCommunicationManager.updateEwonTagValues(
        connectorSettings.getAuthInfo(),
        m2WebEwon.getName(),
        tagNameValuePairs,
        updateTagValuesResponseFutureCallback);
  }

  /**
   * Processes a buffered tag write using the specified parameters. This method will add the tag
   * write to the buffered tag write queue, and schedule the buffered tag write processing thread to
   * process the queue.
   *
   * @param managedTagProvider the managed tag provider where the tag is located
   * @param connectorSettings the connector settings
   * @param m2WebEwon the M2Web Ewon object for the Ewon where the tag is located
   * @param m2WebEwonEBDTag the M2Web Ewon EBD tag object for the tag to write
   * @param tagPath the managed tag provider tag path
   * @param tagValue the tag value to write
   * @since 1.0.0
   */
  private static void writeTagBuffered(
      ManagedTagProvider managedTagProvider,
      EwonConnectorSettings connectorSettings,
      M2WebEwon m2WebEwon,
      M2WebEwonEBDTag m2WebEwonEBDTag,
      TagPath tagPath,
      Object tagValue) {

    // Add buffered tag write to queue and schedule processing
    synchronized (BUFFERED_TAG_WRITE_QUEUE) {
      // Add buffered tag write to queue
      BUFFERED_TAG_WRITE_QUEUE.add(
          new BufferedTagWrite(m2WebEwon, m2WebEwonEBDTag, tagPath, tagValue));

      // Schedule buffered tag write processing
      scheduleBufferedWriteProcessing(managedTagProvider, connectorSettings);
    }

    // Log debug message
    if (connectorSettings.isDebugEnabled()) {
      LOGGER.debug(
          "Tag write scheduled (buffered) for tag [{}] with value [{}].",
          tagPath.toString(),
          tagValue.toString());
    }
  }

  /**
   * Schedules the buffered tag write processing thread to process the buffered tag write queue.
   * This method will only schedule the processing thread if it is not already running. If the
   * processing thread is already running, this method will do nothing, as the processing thread
   * will process the updated queue automatically.
   *
   * @param managedTagProvider the managed tag provider where the tag is located
   * @param connectorSettings the connector settings
   * @since 1.0.0
   */
  private static synchronized void scheduleBufferedWriteProcessing(
      ManagedTagProvider managedTagProvider, EwonConnectorSettings connectorSettings) {
    // If buffered tag write processing thread is not running, create and start it
    if (bufferedTagWriteProcessingThread == null || !bufferedTagWriteProcessingThread.isAlive()) {
      // Create new thread
      bufferedTagWriteProcessingThread =
          new Thread(
              () -> {
                // Create boolean to track if thread should continue running
                boolean continueRunning = true;

                // Loop until queue is empty
                long totalBufferedWriteCount = 0;
                while (continueRunning) {
                  // Wait for buffer length
                  try {
                    Thread.sleep(writeBufferLengthMilliseconds);
                  } catch (InterruptedException e) {
                    LOGGER.error(
                        "Buffered tag write processing thread interrupted while sleeping.", e);
                  }

                  // Create variable to store buffered tag writes and get initial value
                  BufferedTagWrite bufferedTagWrite;
                  synchronized (BUFFERED_TAG_WRITE_QUEUE) {
                    bufferedTagWrite = BUFFERED_TAG_WRITE_QUEUE.poll();
                  }

                  // Iterate through buffered tag writes and map Ewons to their tags
                  long bufferedWriteCount = 0;
                  Map<String, Map<TagPath, KeyValue<String, Object>>> ewonBufferedTagWriteMap =
                      new HashMap<>();
                  while (bufferedTagWrite != null) {
                    // Increment buffered write count
                    totalBufferedWriteCount++;
                    bufferedWriteCount++;

                    // Get Ewon name and add list of tag name/value pairs to map if not already
                    String ewonName = bufferedTagWrite.getM2WebEwon().getName();
                    if (!ewonBufferedTagWriteMap.containsKey(ewonName)) {
                      ewonBufferedTagWriteMap.put(ewonName, new HashMap<>());
                    }

                    // Add tag name/value pair to map (overwrite older if already exists)
                    if (ewonBufferedTagWriteMap
                        .get(ewonName)
                        .containsKey(bufferedTagWrite.getTagPath())) {
                      // Get old buffered tag write value
                      Object oldBufferedWriteValue =
                          ewonBufferedTagWriteMap
                              .get(ewonName)
                              .get(bufferedTagWrite.getTagPath())
                              .getValue();

                      // Overwrite old buffered tag write value
                      ewonBufferedTagWriteMap
                          .get(ewonName)
                          .put(
                              bufferedTagWrite.getTagPath(),
                              new DefaultKeyValue<>(
                                  bufferedTagWrite.getM2WebEwonEBDTag().getName(),
                                  bufferedTagWrite.getTagValue()));

                      // Log warning message
                      LOGGER.warn(
                          "Buffered tag write value [{}] for tag [{}] on Ewon [{}] overwritten by"
                              + " newer buffered tag write value [{}].",
                          oldBufferedWriteValue.toString(),
                          bufferedTagWrite.getM2WebEwonEBDTag().getName(),
                          ewonName,
                          bufferedTagWrite.getTagValue().toString());
                    } else {
                      // Add new buffered tag write value
                      ewonBufferedTagWriteMap
                          .get(ewonName)
                          .put(
                              bufferedTagWrite.getTagPath(),
                              new DefaultKeyValue<>(
                                  bufferedTagWrite.getM2WebEwonEBDTag().getName(),
                                  bufferedTagWrite.getTagValue()));
                    }

                    // Remove buffered tag write from queue and get next value
                    synchronized (BUFFERED_TAG_WRITE_QUEUE) {
                      bufferedTagWrite = BUFFERED_TAG_WRITE_QUEUE.poll();
                    }
                  }

                  // Iterate through Ewons and write buffered tag values
                  for (String ewonName : ewonBufferedTagWriteMap.keySet()) {
                    // Get map of tag name/value pairs
                    Map<TagPath, KeyValue<String, Object>> tagNameValuePairMap =
                        ewonBufferedTagWriteMap.get(ewonName);

                    // Create tag write callback
                    FutureCallback<M2WebEwonUpdateTagValuesResponse>
                        updateTagValuesResponseFutureCallback =
                            new FutureCallback<>() {
                              @Override
                              public void completed(
                                  M2WebEwonUpdateTagValuesResponse
                                      m2WebEwonUpdateTagValuesResponse) {
                                // Update tag values in managed tag provider
                                for (Map.Entry<TagPath, KeyValue<String, Object>>
                                    tagNameValuePairMapEntry : tagNameValuePairMap.entrySet()) {
                                  // Get tag path and value
                                  String tagPath = tagNameValuePairMapEntry.getKey().toString();
                                  Object tagValue = tagNameValuePairMapEntry.getValue().getValue();

                                  // Update tag value in managed tag provider
                                  managedTagProvider.updateValue(
                                      tagPath, tagValue, QualityCode.Good);
                                }

                                // Log debug message
                                if (connectorSettings.isDebugEnabled()) {
                                  LOGGER.debug(
                                      "Tag write (buffered) to Ewon ["
                                          + ewonName
                                          + "] performed for the following tag(s): "
                                          + tagNameValuePairMap.values().stream()
                                              .map(KeyValue::getKey)
                                              .collect(Collectors.joining(", ")));
                                }
                              }

                              @Override
                              public void failed(Exception e) {
                                // Update tag values in managed tag provider
                                for (Map.Entry<TagPath, KeyValue<String, Object>>
                                    tagNameValuePairMapEntry : tagNameValuePairMap.entrySet()) {
                                  // Get tag path and value
                                  String tagPath = tagNameValuePairMapEntry.getKey().toString();
                                  Object tagValue = tagNameValuePairMapEntry.getValue().getValue();

                                  // Update tag value in managed tag provider
                                  managedTagProvider.updateValue(
                                      tagPath, tagValue, QualityCode.Error_Exception);
                                }

                                // Log error message
                                LOGGER.error(
                                    "Tag write (buffered) to Ewon ["
                                        + ewonName
                                        + "] failed for the following tag(s): "
                                        + tagNameValuePairMap.values().stream()
                                            .map(KeyValue::getKey)
                                            .collect(Collectors.joining(", ")),
                                    e);
                              }

                              @Override
                              public void cancelled() {
                                // Update tag values in managed tag provider
                                for (Map.Entry<TagPath, KeyValue<String, Object>>
                                    tagNameValuePairMapEntry : tagNameValuePairMap.entrySet()) {
                                  // Get tag path and value
                                  String tagPath = tagNameValuePairMapEntry.getKey().toString();
                                  Object tagValue = tagNameValuePairMapEntry.getValue().getValue();

                                  // Update tag value in managed tag provider
                                  managedTagProvider.updateValue(
                                      tagPath, tagValue, QualityCode.Error_ScriptEval);
                                }

                                // Log error message
                                LOGGER.error(
                                    "Tag write (buffered) to Ewon ["
                                        + ewonName
                                        + "] were cancelled for the following tag(s): "
                                        + tagNameValuePairMap.values().stream()
                                            .map(KeyValue::getKey)
                                            .collect(Collectors.joining(", ")));
                              }
                            };

                    // Update tag values
                    M2WebCommunicationManager.updateEwonTagValues(
                        connectorSettings.getAuthInfo(),
                        ewonName,
                        tagNameValuePairMap.values(),
                        updateTagValuesResponseFutureCallback);
                  }

                  // Continue running if queue is not empty
                  synchronized (BUFFERED_TAG_WRITE_QUEUE) {
                    continueRunning = !BUFFERED_TAG_WRITE_QUEUE.isEmpty();
                  }

                  // Log debug message
                  if (connectorSettings.isDebugEnabled()) {
                    LOGGER.debug(
                        "Buffered tag write processing completed. [{}] tag writes performed.",
                        bufferedWriteCount);
                  }
                }

                // Log debug message
                if (connectorSettings.isDebugEnabled()) {
                  LOGGER.debug(
                      "Buffered tag write processing thread stopping. [{}] total tag writes"
                          + " performed.",
                      totalBufferedWriteCount);
                }
              });

      // Start buffered tag write processing thread
      bufferedTagWriteProcessingThread.start();
    }
  }

  /**
   * Cancels buffered tag write processing, if running.
   *
   * @since 1.0.0
   */
  public static void cancelBufferedTagWriteProcessing() {
    // Cancel buffered tag write processing thread
    if (bufferedTagWriteProcessingThread != null) {
      bufferedTagWriteProcessingThread.interrupt();
    }
  }
}

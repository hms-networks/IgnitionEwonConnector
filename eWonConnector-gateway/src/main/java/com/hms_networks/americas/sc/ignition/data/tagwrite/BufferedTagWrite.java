package com.hms_networks.americas.sc.ignition.data.tagwrite;

import com.hms_networks.americas.sc.ignition.comm.responses.m2w.M2WebEwon;
import com.hms_networks.americas.sc.ignition.comm.responses.m2w.M2WebEwonEBDTag;
import com.inductiveautomation.ignition.common.tags.model.TagPath;
import org.jetbrains.annotations.NotNull;

/**
 * Class for storing a buffered tag write in the Ignition Ewon Connector. This class tracks the
 * applicable {@link M2WebEwon}, {@link M2WebEwonEBDTag}, {@link TagPath}, and tag value.
 *
 * @since 2.0.0
 * @version 1.0.0
 * @see BufferedTagWriteManager
 * @author HMS Networks, MU Americas Solution Center
 */
public class BufferedTagWrite implements Comparable<BufferedTagWrite> {

  /**
   * The {@link M2WebEwon} for the buffered tag write.
   *
   * @since 1.0.0
   */
  private final M2WebEwon m2WebEwon;

  /**
   * The {@link M2WebEwonEBDTag} for the buffered tag write.
   *
   * @since 1.0.0
   */
  private final M2WebEwonEBDTag m2WebEwonEBDTag;

  /**
   * The {@link TagPath} for the buffered tag write.
   *
   * @since 1.0.0
   */
  private final TagPath tagPath;

  /**
   * The tag value for the buffered tag write.
   *
   * @since 1.0.0
   */
  private final Object tagValue;

  /**
   * The time (in milliseconds) that the buffered tag write was created.
   *
   * @since 1.0.0
   */
  private final long bufferedWriteTime;

  /**
   * Constructor for a new buffered tag write.
   *
   * @param m2WebEwon the {@link M2WebEwon} for the buffered tag write
   * @param m2WebEwonEBDTag the {@link M2WebEwonEBDTag} for the buffered tag write
   * @param tagPath the {@link TagPath} for the buffered tag write
   * @param tagValue the tag value for the buffered tag write
   * @since 1.0.0
   */
  public BufferedTagWrite(
      M2WebEwon m2WebEwon, M2WebEwonEBDTag m2WebEwonEBDTag, TagPath tagPath, Object tagValue) {
    this.m2WebEwon = m2WebEwon;
    this.m2WebEwonEBDTag = m2WebEwonEBDTag;
    this.tagPath = tagPath;
    this.tagValue = tagValue;
    this.bufferedWriteTime = System.currentTimeMillis();
  }

  /**
   * Returns the {@link M2WebEwon} for the buffered tag write.
   *
   * @return {@link M2WebEwon} for the buffered tag write
   * @since 1.0.0
   */
  public M2WebEwon getM2WebEwon() {
    return m2WebEwon;
  }

  /**
   * Returns the {@link M2WebEwonEBDTag} for the buffered tag write.
   *
   * @return {@link M2WebEwonEBDTag} for the buffered tag write
   * @since 1.0.0
   */
  public M2WebEwonEBDTag getM2WebEwonEBDTag() {
    return m2WebEwonEBDTag;
  }

  /**
   * Returns the {@link TagPath} for the buffered tag write.
   *
   * @return {@link TagPath} for the buffered tag write
   * @since 1.0.0
   */
  public TagPath getTagPath() {
    return tagPath;
  }

  /**
   * Returns the tag value for the buffered tag write.
   *
   * @return tag value for the buffered tag write
   * @since 1.0.0
   */
  public Object getTagValue() {
    return tagValue;
  }

  /**
   * Compares this {@link BufferedTagWrite} with the specified {@link BufferedTagWrite} for order.
   * Returns a negative integer, zero, or a positive integer as this {@link BufferedTagWrite} is
   * older than, equal to, or newer than the specified {@link BufferedTagWrite}.
   *
   * @param bufferedTagWrite the {@link BufferedTagWrite} to be compared.
   * @return a negative integer, zero, or a positive integer as this {@link BufferedTagWrite} is
   *     older than, equal to, or newer than the specified {@link BufferedTagWrite}.
   * @throws NullPointerException if the specified {@link BufferedTagWrite} is null
   * @throws ClassCastException if the specified {@link BufferedTagWrite}'s type is incorrect and
   *     prevents it from being compared to this {@link BufferedTagWrite}.
   * @since 1.0.0
   */
  @Override
  public int compareTo(@NotNull BufferedTagWrite bufferedTagWrite) {
    return (int) (this.bufferedWriteTime - bufferedTagWrite.bufferedWriteTime);
  }
}

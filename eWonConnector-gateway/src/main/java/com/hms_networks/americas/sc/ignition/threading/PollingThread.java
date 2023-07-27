package com.hms_networks.americas.sc.ignition.threading;

import com.hms_networks.americas.sc.ignition.IgnitionEwonConnectorHook;
import com.hms_networks.americas.sc.ignition.config.EwonConnectorSettings;
import java.util.concurrent.TimeUnit;

/**
 * A thread that polls at a specified interval. The polling logic is defined by the implementation
 * of the {@link PollingThread#run()} method. The polling interval can be changed at runtime by
 * calling the {@link PollingThread#setPollingInterval(long, TimeUnit)} method.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public abstract class PollingThread implements Runnable {

  /**
   * The thread name.
   *
   * @since 1.0.0
   */
  private final String threadName;

  /**
   * The thread polling interval. The unit is defined by {@link #pollingIntervalTimeUnit}.
   *
   * @since 1.0.0
   */
  private long pollingInterval;

  /**
   * The thread polling interval and polling interval start delay time unit.
   *
   * @since 1.0.0
   */
  private TimeUnit pollingIntervalTimeUnit;

  /**
   * The flag indicating if the thread is running/has been registered to run.
   *
   * @since 1.0.0
   */
  private boolean isRegistered = false;

  /**
   * The gateway module hook.
   *
   * @since 1.0.0
   */
  protected final IgnitionEwonConnectorHook gatewayHook;

  /**
   * The Ewon connector settings.
   *
   * @since 1.0.0
   */
  protected final EwonConnectorSettings connectorSettings;

  /**
   * Creates a new polling thread with the specified thread polling interval.
   *
   * @param threadName The thread name.
   * @param pollingInterval The thread polling interval.
   * @param pollingIntervalTimeUnit The thread polling interval time unit.
   * @param gatewayHook The gateway module hook.
   * @param connectorSettings The Ewon connector settings.
   * @since 1.0.0
   */
  public PollingThread(
      String threadName,
      long pollingInterval,
      TimeUnit pollingIntervalTimeUnit,
      IgnitionEwonConnectorHook gatewayHook,
      EwonConnectorSettings connectorSettings) {
    this.threadName = threadName;
    this.pollingInterval = pollingInterval;
    this.pollingIntervalTimeUnit = pollingIntervalTimeUnit;
    this.gatewayHook = gatewayHook;
    this.connectorSettings = connectorSettings;
  }

  /**
   * Starts the polling thread using the specified polling interval, and polling interval start
   * delay.
   *
   * @throws IllegalStateException if the polling thread is already running or the polling thread
   *     executor service has been shutdown.
   * @since 1.0.0
   */
  public void start() {
    // Check if the thread is already running
    if (isRegistered) {
      throw new IllegalStateException("Polling thread is already registered.");
    }

    // Schedule the thread to run at the specified interval
    gatewayHook
        .getGatewayContext()
        .getExecutionManager()
        .register(
            IgnitionEwonConnectorHook.BUNDLE_PREFIX_EWON,
            threadName,
            this,
            (int) pollingIntervalTimeUnit.toMillis(pollingInterval));
    isRegistered = true;
  }

  /**
   * Stops the polling thread by unregistering it from the execution manager.
   *
   * @since 1.0.0
   */
  public void stop() {
    // Cancel the thread (if started)
    if (isRegistered) {
      gatewayHook
          .getGatewayContext()
          .getExecutionManager()
          .unRegister(IgnitionEwonConnectorHook.BUNDLE_PREFIX_EWON, threadName);
      isRegistered = false;
    }
  }

  /**
   * Gets the thread polling interval.
   *
   * @return The thread polling interval.
   * @throws IllegalStateException if the polling thread executor service has been shutdown.
   * @since 1.0.0
   */
  public long getPollingInterval() {
    return pollingInterval;
  }

  /**
   * Gets the thread polling interval time unit.
   *
   * @return The thread polling interval time unit.
   * @throws IllegalStateException if the polling thread executor service has been shutdown.
   * @since 1.0.0
   */
  public TimeUnit getPollingIntervalTimeUnit() {
    return pollingIntervalTimeUnit;
  }

  /**
   * Sets the thread polling interval.
   *
   * @param pollingInterval The thread polling interval.
   * @param pollingIntervalTimeUnit The thread polling interval time unit.
   * @throws IllegalStateException if the polling thread executor service has been shutdown.
   * @since 1.0.0
   */
  public void setPollingInterval(long pollingInterval, TimeUnit pollingIntervalTimeUnit) {
    // Set the polling interval
    this.pollingInterval = pollingInterval;
    this.pollingIntervalTimeUnit = pollingIntervalTimeUnit;

    // Restart the thread if it is already running
    stop();
    start();
  }
}

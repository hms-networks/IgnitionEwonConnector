package com.hms_networks.americas.sc.ignition.threading;

import com.hms_networks.americas.sc.ignition.config.EwonConnectorSettings;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
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
   * The default thread polling interval start delay. The unit defined by {@link
   * #pollingIntervalTimeUnit} is applicable, but the value is always 0.
   *
   * @since 1.0.0
   */
  public static final long DEFAULT_POLLING_INTERVAL_START_DELAY = 0;

  /**
   * The thread executor service.
   *
   * @since 1.0.0
   */
  private final ScheduledExecutorService executorService;

  /**
   * The thread polling interval. The unit is defined by {@link #pollingIntervalTimeUnit}.
   *
   * @since 1.0.0
   */
  private long pollingInterval;

  /**
   * The thread polling interval start delay. The unit is defined by {@link
   * #pollingIntervalTimeUnit}.
   *
   * @since 1.0.0
   */
  private long pollingIntervalStartDelay;

  /**
   * The thread polling interval and polling interval start delay time unit.
   *
   * @since 1.0.0
   */
  private TimeUnit pollingIntervalTimeUnit;

  /**
   * The scheduled future for the polling thread.
   *
   * @since 1.0.0
   */
  private ScheduledFuture<?> scheduledFuture = null;

  /**
   * The Ewon connector settings.
   *
   * @since 1.0.0
   */
  protected final EwonConnectorSettings connectorSettings;

  /**
   * Creates a new polling thread with the specified thread polling interval. The default polling
   * interval start delay is used ({@link #DEFAULT_POLLING_INTERVAL_START_DELAY}).
   *
   * @param pollingInterval The thread polling interval.
   * @param pollingIntervalTimeUnit The thread polling interval time unit.
   * @param connectorSettings The Ewon connector settings.
   * @since 1.0.0
   */
  public PollingThread(
      long pollingInterval,
      TimeUnit pollingIntervalTimeUnit,
      EwonConnectorSettings connectorSettings) {
    this(
        pollingInterval,
        DEFAULT_POLLING_INTERVAL_START_DELAY,
        pollingIntervalTimeUnit,
        connectorSettings);
  }

  /**
   * Creates a new polling thread with the specified thread polling interval and polling interval
   * start delay. The start delay is the amount of time to delay before the first poll interval
   * starts.
   *
   * @param pollingInterval The thread polling interval.
   * @param pollingIntervalStartDelay The thread polling interval start delay.
   * @param pollingIntervalTimeUnit The thread polling interval time unit.
   * @param connectorSettings The Ewon connector settings.
   * @since 1.0.0
   */
  public PollingThread(
      long pollingInterval,
      long pollingIntervalStartDelay,
      TimeUnit pollingIntervalTimeUnit,
      EwonConnectorSettings connectorSettings) {
    this.pollingInterval = pollingInterval;
    this.pollingIntervalStartDelay = pollingIntervalStartDelay;
    this.pollingIntervalTimeUnit = pollingIntervalTimeUnit;
    this.connectorSettings = connectorSettings;
    executorService = Executors.newSingleThreadScheduledExecutor();
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
    // Check if the executor service is shutdown
    checkShutdown();

    // Check if the thread is already running
    if (isRunning()) {
      throw new IllegalStateException("Polling thread is already running.");
    }

    // Schedule the thread to run at the specified interval
    scheduledFuture =
        executorService.scheduleAtFixedRate(
            this, pollingIntervalStartDelay, pollingInterval, pollingIntervalTimeUnit);
  }

  /**
   * Stops the polling thread using the specified interrupt behavior.
   *
   * @param mayInterruptIfRunning true if in-progress polls should be interrupted; otherwise,
   *     in-progress polls are allowed to complete
   * @throws IllegalStateException if the polling thread executor service has been shutdown.
   * @since 1.0.0
   */
  public void stop(boolean mayInterruptIfRunning) {
    // Check if the executor service is shutdown
    checkShutdown();

    // Cancel the thread (if started)
    if (scheduledFuture != null) {
      scheduledFuture.cancel(mayInterruptIfRunning);
    }
  }

  /**
   * Stops the polling thread gracefully. In-progress polls are allowed to complete.
   *
   * @throws IllegalStateException if the polling thread executor service has been shutdown.
   * @since 1.0.0
   */
  public void stopGracefully() {
    // Cancel the thread gracefully
    boolean mayInterruptIfRunning = false;
    stop(mayInterruptIfRunning);
  }

  /**
   * Stops the polling thread immediately. In-progress polls are interrupted.
   *
   * @throws IllegalStateException if the polling thread executor service has been shutdown.
   * @since 1.0.0
   */
  public void stopImmediately() {
    // Cancel the thread immediately
    boolean mayInterruptIfRunning = true;
    stop(mayInterruptIfRunning);
  }

  /**
   * Stops the polling thread using the specified interrupt behavior, and shuts down the executor
   * service.
   *
   * <p>This method should be called when the thread is no longer needed, as it will prevent the
   * thread from being restarted.
   *
   * @param mayInterruptIfRunning true if in-progress polls should be interrupted; otherwise,
   *     in-progress polls are allowed to complete
   * @throws IllegalStateException if the polling thread executor service has already been shutdown.
   * @since 1.0.0
   */
  public void shutdown(boolean mayInterruptIfRunning) {
    // Check if the executor service is shutdown
    if (executorService.isShutdown()) {
      throw new IllegalStateException("Polling thread executor service is already shutdown.");
    }

    // Shutdown the thread (if running)
    if (isRunning()) {
      stop(mayInterruptIfRunning);
    }

    // Shutdown the executor service
    executorService.shutdown();
  }

  /**
   * Stops the polling thread gracefully, and shuts down the executor service. In-progress polls are
   * allowed to complete.
   *
   * <p>This method should be called when the thread is no longer needed, as it will prevent the
   * thread from being restarted.
   *
   * @throws IllegalStateException if the polling thread executor service has already been shutdown.
   * @since 1.0.0
   */
  public void shutdownGracefully() {
    // Shutdown the thread gracefully
    boolean mayInterruptIfRunning = false;
    shutdown(mayInterruptIfRunning);
  }

  /**
   * Stops the polling thread immediately, and shuts down the executor service. In-progress polls
   * are interrupted.
   *
   * <p>This method should be called when the thread is no longer needed, as it will prevent the
   * thread from being restarted.
   *
   * @throws IllegalStateException if the polling thread executor service has already been shutdown.
   * @since 1.0.0
   */
  public void shutdownImmediately() {
    // Shutdown the thread immediately
    boolean mayInterruptIfRunning = true;
    shutdown(mayInterruptIfRunning);
  }

  /**
   * Gets the thread running status.
   *
   * @return The thread running status.
   * @throws IllegalStateException if the polling thread executor service has been shutdown.
   * @since 1.0.0
   */
  public boolean isRunning() {
    // Check if the executor service is shutdown
    checkShutdown();

    return scheduledFuture != null && !scheduledFuture.isCancelled();
  }

  /**
   * Gets the thread polling interval.
   *
   * @return The thread polling interval.
   * @throws IllegalStateException if the polling thread executor service has been shutdown.
   * @since 1.0.0
   */
  public long getPollingInterval() {
    // Check if the executor service is shutdown
    checkShutdown();

    return pollingInterval;
  }

  /**
   * Gets the thread polling interval start delay.
   *
   * @return The thread polling interval start delay.
   * @throws IllegalStateException if the polling thread executor service has been shutdown.
   * @since 1.0.0
   */
  public long getPollingIntervalStartDelay() {
    // Check if the executor service is shutdown
    checkShutdown();

    return pollingIntervalStartDelay;
  }

  /**
   * Gets the thread polling interval time unit.
   *
   * @return The thread polling interval time unit.
   * @throws IllegalStateException if the polling thread executor service has been shutdown.
   * @since 1.0.0
   */
  public TimeUnit getPollingIntervalTimeUnit() {
    // Check if the executor service is shutdown
    checkShutdown();

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
    // Check if the executor service is shutdown
    checkShutdown();

    // Set the polling interval
    this.pollingInterval = pollingInterval;
    this.pollingIntervalTimeUnit = pollingIntervalTimeUnit;

    // Restart the thread if it is already running
    stopGracefully();
    start();
  }

  /**
   * Sets the thread polling interval and start delay. The start delay is the amount of time to
   * delay before the first poll interval starts.
   *
   * @param pollingInterval The thread polling interval.
   * @param pollingIntervalStartDelay The thread polling interval start delay.
   * @param pollingIntervalTimeUnit The thread polling interval time unit.
   * @throws IllegalStateException if the polling thread executor service has been shutdown.
   * @since 1.0.0
   */
  public void setPollingInterval(
      long pollingInterval, long pollingIntervalStartDelay, TimeUnit pollingIntervalTimeUnit) {
    // Check if the executor service is shutdown
    checkShutdown();

    // Set the polling interval and start delay
    this.pollingInterval = pollingInterval;
    this.pollingIntervalStartDelay = pollingIntervalStartDelay;
    this.pollingIntervalTimeUnit = pollingIntervalTimeUnit;

    // Restart the thread if it is already running
    stopGracefully();
    start();
  }

  /**
   * Checks if the polling thread executor service has been shutdown and throws an exception if it
   * has.
   *
   * @throws IllegalStateException if the polling thread executor service has been shutdown.
   * @since 1.0.0
   */
  private void checkShutdown() {
    // Check if the executor service is shutdown
    if (executorService.isShutdown()) {
      throw new IllegalStateException("Polling thread executor service is shutdown.");
    }
  }
}

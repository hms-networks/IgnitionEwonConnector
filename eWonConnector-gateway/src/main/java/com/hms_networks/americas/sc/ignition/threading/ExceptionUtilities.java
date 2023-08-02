package com.hms_networks.americas.sc.ignition.threading;

import java.util.Arrays;
import java.util.stream.Collectors;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;

/**
 * Utility class for working with {@link Exception} and {@link Throwable} objects.
 *
 * @since 2.0.0
 * @version 1.0.0
 * @author HMS Networks, MU Americas Solution Center
 */
public class ExceptionUtilities {

  /**
   * Separator used to delimit exception trace messages.
   *
   * @since 1.0.0
   */
  private static final String EXCEPTION_TRACE_MESSAGE_SEPARATOR = " -> ";

  /**
   * Prints the exception trace messages for the specified {@link Exception} to the specified {@link
   * Logger}. The exception trace messages are printed as a single string, with each message
   * separated by the {@link #EXCEPTION_TRACE_MESSAGE_SEPARATOR}.
   *
   * @param logger logger to print exception trace messages to
   * @param e exception to print trace messages for
   * @apiNote This method is useful for showing a more friendly exception trace (can be printed
   *     alongside the full exception trace), and is especially helpful where the Ignition logging
   *     subsystem may have full exception traces disabled or suppressed. It is not intended to
   *     replace the full exception trace, but rather to supplement it.
   * @since 1.0.0
   * @see ExceptionUtils#getThrowables(Throwable)
   * @see Throwable#getMessage()
   * @see Logger#error(String)
   * @see #EXCEPTION_TRACE_MESSAGE_SEPARATOR
   */
  public static void printExceptionTraceMessages(Logger logger, Exception e) {
    logger.error(
        "Exception Trace Messages: "
            + Arrays.stream(ExceptionUtils.getThrowables(e))
                .map(Throwable::getMessage)
                .filter(message -> message != null && !message.isEmpty())
                .collect(Collectors.joining(EXCEPTION_TRACE_MESSAGE_SEPARATOR)));
  }
}

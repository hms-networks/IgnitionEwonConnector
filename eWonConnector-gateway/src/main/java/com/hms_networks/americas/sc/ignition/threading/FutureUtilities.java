package com.hms_networks.americas.sc.ignition.threading;

import com.google.gson.JsonSyntaxException;
import com.hms_networks.americas.sc.ignition.comm.responses.Talk2MResponse;
import java.util.concurrent.*;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.jetbrains.annotations.NotNull;

/**
 * Utility class for working with {@link Future} objects.
 *
 * @since 2.0.0
 * @version 1.0.0
 * @author HMS Networks, MU Americas Solution Center
 */
public class FutureUtilities {

  /**
   * Gets a wrapped future that returns the specified class type from the specified future.
   *
   * @param simpleHttpResponseFuture the future to wrap
   * @param clazz the class type to return
   * @return the wrapped future
   * @param <T> the class type to return
   * @since 1.0.0
   */
  public static <T extends Talk2MResponse> Future<T> getWrappedFuture(
      final Future<SimpleHttpResponse> simpleHttpResponseFuture, final Class<T> clazz) {
    return new Future<>() {
      @Override
      public boolean cancel(boolean mayInterruptIfRunning) {
        return simpleHttpResponseFuture.cancel(mayInterruptIfRunning);
      }

      @Override
      public boolean isCancelled() {
        return simpleHttpResponseFuture.isCancelled();
      }

      @Override
      public boolean isDone() {
        return simpleHttpResponseFuture.isDone();
      }

      @Override
      public T get()
          throws CancellationException,
              InterruptedException,
              ExecutionException,
              JsonSyntaxException {
        SimpleHttpResponse simpleHttpResponse = simpleHttpResponseFuture.get();
        return Talk2MResponse.getFromJson(simpleHttpResponse.getBodyText(), clazz);
      }

      @Override
      public T get(long timeout, @NotNull TimeUnit unit)
          throws CancellationException,
              InterruptedException,
              ExecutionException,
              TimeoutException,
              JsonSyntaxException {
        SimpleHttpResponse simpleHttpResponse = simpleHttpResponseFuture.get(timeout, unit);
        return Talk2MResponse.getFromJson(simpleHttpResponse.getBodyText(), clazz);
      }
    };
  }
}

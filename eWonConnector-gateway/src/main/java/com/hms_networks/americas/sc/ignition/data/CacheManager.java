package com.hms_networks.americas.sc.ignition.data;

import com.hms_networks.americas.sc.ignition.comm.responses.m2w.M2WebEwon;
import com.hms_networks.americas.sc.ignition.comm.responses.m2w.M2WebEwonEBDTag;
import java.util.*;

/**
 * Class for managing Ignition Ewon Connector caches, including M2Web Ewon gateways ({@link
 * M2WebEwon}s), M2Web Ewon gateway ({@link M2WebEwon}) tag sets, and M2Web Ewon gateway ({@link
 * M2WebEwon}) unavailable status. This class provides methods for caching, updating, and retrieving
 * cached data.
 *
 * <p>Cache data is retrieved using the M2Web API(s), but is used to facilitate both M2Web and DMWeb
 * functionality within the connector.
 *
 * @since 2.0.0
 * @version 1.0.0
 * @author HMS Networks, MU Americas Solution Center
 */
public class CacheManager {

  /**
   * The cached map of M2Web Ewon gateways ({@link M2WebEwon}s).
   *
   * @since 1.0.0
   */
  private static final Map<String, M2WebEwon> CACHED_M2WEB_EWON_MAP = new HashMap<>();

  /**
   * The cached map of M2Web Ewon gateway ({@link M2WebEwon}) names and their tag sets.
   *
   * @since 1.0.0
   */
  private static final Map<String, Map<String, M2WebEwonEBDTag>> CACHED_M2WEB_EWON_TAG_MAP =
      new HashMap<>();

  /**
   * The cached map of M2Web Ewon gateway ({@link M2WebEwon}) names and their most recent
   * unavailable status. This map is used to prevent unnecessary duplicate logging of unavailable
   * status when debug logging is not enabled.
   *
   * @since 1.0.0
   */
  private static final Map<String, Boolean> CACHED_M2WEB_EWON_UNAVAILABLE_STATUS_MAP =
      new HashMap<>();

  /**
   * Gets the cached collection of M2Web Ewon gateways ({@link M2WebEwon}s).
   *
   * @return the cached collection of M2Web Ewon gateways ({@link M2WebEwon}s)
   * @since 1.0.0
   */
  public static Collection<M2WebEwon> getCachedM2WebEwons() {
    synchronized (CACHED_M2WEB_EWON_MAP) {
      return CACHED_M2WEB_EWON_MAP.values();
    }
  }

  /**
   * Gets the cached M2Web Ewon gateway ({@link M2WebEwon}) with the specified name. If the
   * specified M2Web Ewon gateway ({@link M2WebEwon}) is not cached, null will be returned.
   *
   * @param name the name of the M2Web Ewon gateway ({@link M2WebEwon}) to get the cached instance
   *     for
   * @return the cached M2Web Ewon gateway ({@link M2WebEwon}) with the specified name
   * @since 1.0.0
   */
  public static M2WebEwon getCachedM2WebEwon(String name) {
    synchronized (CACHED_M2WEB_EWON_MAP) {
      return CACHED_M2WEB_EWON_MAP.get(name);
    }
  }

  /**
   * Gets the cached map of M2Web Ewon tags ({@link M2WebEwonEBDTag}s) for the specified M2Web Ewon
   * gateway ({@link M2WebEwon}). If the specified M2Web Ewon gateway ({@link M2WebEwon}) is not
   * cached, an empty map will be returned.
   *
   * @param m2WebEwonName the name of the M2Web Ewon gateway ({@link M2WebEwon}) to get the cached
   *     tag set for
   * @return the cached map of M2Web Ewon tags ({@link M2WebEwonEBDTag}s) for the specified M2Web
   *     Ewon gateway ({@link M2WebEwon})
   * @since 1.0.0
   */
  public static Map<String, M2WebEwonEBDTag> getCachedM2WebEwonTagMap(String m2WebEwonName) {
    synchronized (CACHED_M2WEB_EWON_TAG_MAP) {
      return CACHED_M2WEB_EWON_TAG_MAP.getOrDefault(m2WebEwonName, new HashMap<>());
    }
  }

  /**
   * Updates the cached set of M2Web Ewon gateways ({@link M2WebEwon}s) with the contents of the
   * specified collection. The cached tag sets for each M2Web Ewon gateway will be cleared and must
   * be re-populated.
   *
   * @param m2WebEwons the collection of M2Web Ewon gateways ({@link M2WebEwon}s) to update the
   *     cached set with
   * @since 1.0.0
   */
  public static void updateCachedM2WebEwonSet(Collection<M2WebEwon> m2WebEwons) {
    synchronized (CACHED_M2WEB_EWON_MAP) {
      CACHED_M2WEB_EWON_MAP.clear();
      m2WebEwons.forEach(m2WebEwon -> CACHED_M2WEB_EWON_MAP.put(m2WebEwon.getName(), m2WebEwon));
    }
  }

  /**
   * Updates the cached set of M2Web Ewon gateway tags ({@link M2WebEwonEBDTag}s) for the specified
   * M2Web Ewon gateway ({@link M2WebEwon}).
   *
   * @param m2WebEwonName the name of the M2Web Ewon gateway ({@link M2WebEwon}) to update the
   *     cached tag set for
   * @param m2WebEwonEBDTags the collection of M2Web Ewon gateway tags ({@link M2WebEwonEBDTag}s) to
   *     update the cached tag set with
   * @since 1.0.0
   */
  public static void updateCachedM2WebEwonTagSet(
      String m2WebEwonName, Collection<M2WebEwonEBDTag> m2WebEwonEBDTags) {
    synchronized (CACHED_M2WEB_EWON_TAG_MAP) {
      if (!CACHED_M2WEB_EWON_TAG_MAP.containsKey(m2WebEwonName)) {
        CACHED_M2WEB_EWON_TAG_MAP.put(m2WebEwonName, new HashMap<>());
      } else {
        CACHED_M2WEB_EWON_TAG_MAP.get(m2WebEwonName).clear();
      }
      for (M2WebEwonEBDTag m2WebEwonEBDTag : m2WebEwonEBDTags) {
        CACHED_M2WEB_EWON_TAG_MAP
            .get(m2WebEwonName)
            .put(m2WebEwonEBDTag.getName(), m2WebEwonEBDTag);
      }
    }
  }

  /**
   * Updates the cached M2Web Ewon gateway ({@link M2WebEwon}) unavailable status for the specified
   * {@link M2WebEwon}.
   *
   * @param m2WebEwon the M2Web Ewon gateway ({@link M2WebEwon}) to update the cached unavailable
   *     status for
   * @param unavailableStatus the new unavailable status for the M2Web Ewon gateway ({@link
   *     M2WebEwon})
   * @since 1.0.0
   */
  public static void updateCachedM2WebEwonUnavailableStatus(
      M2WebEwon m2WebEwon, boolean unavailableStatus) {
    updateCachedM2WebEwonUnavailableStatus(m2WebEwon.getName(), unavailableStatus);
  }

  /**
   * Updates the cached M2Web Ewon gateway ({@link M2WebEwon}) unavailable status for the Ewon with
   * the specified name.
   *
   * @param m2WebEwonName the name of the M2Web Ewon gateway ({@link M2WebEwon}) to update the
   *     cached unavailable status for
   * @param unavailableStatus the new unavailable status for the M2Web Ewon gateway ({@link
   *     M2WebEwon})
   * @since 1.0.0
   */
  public static void updateCachedM2WebEwonUnavailableStatus(
      String m2WebEwonName, boolean unavailableStatus) {
    synchronized (CACHED_M2WEB_EWON_UNAVAILABLE_STATUS_MAP) {
      CACHED_M2WEB_EWON_UNAVAILABLE_STATUS_MAP.put(m2WebEwonName, unavailableStatus);
    }
  }

  /**
   * Gets the cached unavailable status for the specified M2Web Ewon gateway ({@link M2WebEwon}). If
   * the M2Web Ewon gateway ({@link M2WebEwon}) is not in the cache, then {@code false} is returned.
   *
   * @param m2WebEwon the M2Web Ewon gateway ({@link M2WebEwon}) to get the cached unavailable
   *     status for
   * @return the cached unavailable status for the specified M2Web Ewon gateway ({@link M2WebEwon}).
   *     If the M2Web Ewon gateway ({@link M2WebEwon}) is not in the cache, then {@code false} is
   *     returned.
   * @since 1.0.0
   */
  public static boolean getCachedM2WebEwonUnavailableStatus(M2WebEwon m2WebEwon) {
    return getCachedM2WebEwonUnavailableStatus(m2WebEwon.getName());
  }

  /**
   * Gets the cached unavailable status for the M2Web Ewon gateway ({@link M2WebEwon}) with the
   * specified name. If the M2Web Ewon gateway ({@link M2WebEwon}) is not in the cache, then {@code
   * false} is returned.
   *
   * @param m2WebEwonName the name of the M2Web Ewon gateway ({@link M2WebEwon}) to get the cached
   *     unavailable status for
   * @return the cached unavailable status for the M2Web Ewon gateway ({@link M2WebEwon}) with the
   *     specified name. If the M2Web Ewon gateway ({@link M2WebEwon}) is not in the cache, then
   *     {@code false} is returned.
   * @since 1.0.0
   */
  public static boolean getCachedM2WebEwonUnavailableStatus(String m2WebEwonName) {
    synchronized (CACHED_M2WEB_EWON_UNAVAILABLE_STATUS_MAP) {
      return CACHED_M2WEB_EWON_UNAVAILABLE_STATUS_MAP.getOrDefault(m2WebEwonName, false);
    }
  }

  /**
   * Clears all currently cached data. This should be called when the application is shutting down.
   *
   * @since 1.0.0
   */
  public static void clearCaches() {
    synchronized (CACHED_M2WEB_EWON_MAP) {
      CACHED_M2WEB_EWON_MAP.clear();
    }
    synchronized (CACHED_M2WEB_EWON_TAG_MAP) {
      CACHED_M2WEB_EWON_TAG_MAP.clear();
    }
    synchronized (CACHED_M2WEB_EWON_UNAVAILABLE_STATUS_MAP) {
      CACHED_M2WEB_EWON_UNAVAILABLE_STATUS_MAP.clear();
    }
  }
}

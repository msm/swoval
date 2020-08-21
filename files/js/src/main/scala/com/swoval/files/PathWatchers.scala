// Do not edit this file manually. It is autogenerated.

package com.swoval.files

import com.swoval.files.FileTreeDataViews.Converter
import com.swoval.logging.Logger
import com.swoval.logging.Loggers
import com.swoval.runtime.Platform
import java.io.IOException
import java.nio.file.Path
import java.util.concurrent.TimeUnit
import scala.beans.{ BeanProperty, BooleanBeanProperty }

object PathWatchers {

  /**
   * Create a PathWatcher for the runtime platform.
   *
   * @param followLinks toggles whether or not the targets of symbolic links should be monitored
   * @return PathWatcher for the runtime platform
   *     initialized
   */
  def get(followLinks: Boolean): PathWatcher[PathWatchers.Event] =
    get(followLinks, new DirectoryRegistryImpl(), Loggers.getLogger)

  /**
   * Create a path watcher that periodically polls the file system to detect changes
   *
   * @param converter calculates the last modified time in milliseconds for the path watcher. This
   *     exists so that the converter can be replaced with a higher resolution calculation of the
   *     file system last modified time than is provided by the jvm, e.g.
   *     sbt.IO.getLastModifiedTimeOrZero.
   * @param followLinks toggles whether or not the targets of symbolic links should be monitored
   * @param pollInterval minimum duration between when polling ends and the next poll begins
   * @param timeUnit the time unit for which the pollInterval corresponds
   * @return the polling path watcher.
   */
  def polling(
      converter: Converter[java.lang.Long],
      followLinks: Boolean,
      pollInterval: java.lang.Long,
      timeUnit: TimeUnit
  ): PathWatcher[PathWatchers.Event] =
    new PollingPathWatcher(converter, followLinks, pollInterval, timeUnit)

  /**
   * Create a path watcher that periodically polls the file system to detect changes
   *
   * @param followLinks toggles whether or not the targets of symbolic links should be monitored
   * @param pollInterval minimum duration between when polling ends and the next poll begins
   * @param timeUnit the time unit for which the pollInterval corresponds
   * @return the polling path watcher.
   */
  def polling(
      followLinks: Boolean,
      pollInterval: java.lang.Long,
      timeUnit: TimeUnit
  ): PathWatcher[PathWatchers.Event] =
    new PollingPathWatcher(followLinks, pollInterval, timeUnit)

  /**
   * Create a PathWatcher for the runtime platform.
   *
   * @param followLinks toggles whether or not the targets of symbolic links should be monitored
   * @param registry The registry of directories to monitor
   * @param logger the logger
   * @return PathWatcher for the runtime platform
   *     initialized
   */
  def get(followLinks: Boolean, registry: DirectoryRegistry, logger: Logger): PathWatcher[Event] =
    if (Platform.isMac) ApplePathWatchers.get(followLinks, registry, logger)
    else PlatformWatcher.make(followLinks, registry, logger)

  /**
   * Create a PathWatcher for the runtime platform.
   *
   * @param registry The registry of directories to monitor
   * @return PathWatcher for the runtime platform
   */
  def get(
      followLinks: Boolean,
      service: RegisterableWatchService,
      registry: DirectoryRegistry,
      logger: Logger
  ): PathWatcher[Event] =
    PlatformWatcher.make(followLinks, service, registry, logger)

  class Overflow(@BeanProperty val path: Path)

  object Event {

    object Kind {

      /**
       * A new file was created.
       */
      val Create: Kind = new Kind("Create")

      /**
       * The file was deleted.
       */
      val Delete: Kind = new Kind("Delete")

      /**
       * An error occurred processing the event.
       */
      val Error: Kind = new Kind("Error")

      /**
       * An existing file was modified.
       */
      val Modify: Kind = new Kind("Modify")

      /**
       * The watching service overflowed so it may be necessary to poll.
       */
      val Overflow: Kind = new Kind("Overflow")

    }

    /**
     * An enum like class to indicate the type of file event. It isn't an actual enum because the
     * scala.js codegen has problems with enum types.
     */
    class Kind(private val name: String) {

      override def toString(): String = name

      override def equals(other: Any): Boolean =
        other match {
          case other: Kind => other.name == this.name
          case _           => false

        }

      override def hashCode(): Int = name.hashCode

    }

  }

  /**
   * Container for [[PathWatcher]] events.
   */
  class Event(@BeanProperty val typedPath: TypedPath, @BeanProperty val kind: Event.Kind) {

    override def equals(other: Any): Boolean =
      other match {
        case other: Event => {
          val that: Event = other
          this.typedPath == that.typedPath && this.kind == that.kind
        }
        case _ => false

      }

    override def hashCode(): Int = typedPath.hashCode ^ kind.hashCode

    override def toString(): String =
      "Event(" + typedPath.getPath + ", " + kind + ")"

  }

}

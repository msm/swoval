// Do not edit this file manually. It is autogenerated.

package com.swoval.files

trait WatchedDirectory extends AutoCloseable {

  /**
   * Cancel the watch on this directory. Handle all non-fatal exceptions.
   */
  override def close(): Unit

}

object WatchedDirectories {

  var INVALID: WatchedDirectory = new WatchedDirectory() {
    override def close(): Unit = {}

    override def toString(): String = "Invalid"
  }

}

// Do not edit this file manually. It is autogenerated.

package com.swoval.functional

object Filters {

  /**
   * An instance of [[com.swoval.functional.Filter]] that accepts any object.
   */
  val AllPass: Filter[AnyRef] = new Filter[AnyRef]() {
    override def accept(o: AnyRef): Boolean = true
  }

}

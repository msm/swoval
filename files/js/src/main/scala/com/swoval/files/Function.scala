// Do not edit this file manually. It is autogenerated.

package com.swoval.files

/**
 * Functional interface for a function that takes one argument.
 */
trait Function[T, R] {

  /**
   * Returns the result of applying the function to the input parameter.
   *
   * @param t the input
   * @return the result of applying the function.
   */
  def apply(t: T): R

}

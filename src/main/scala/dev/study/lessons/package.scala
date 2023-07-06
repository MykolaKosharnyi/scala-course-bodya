package dev.study

package object lessons {
  implicit class RichEither[L, R](either: Either[L, R]) {
    def orElse[L1 >: L, R1 >: R](alternative: => Either[L1, R1]) = if (either.isRight) either else alternative
  }
}

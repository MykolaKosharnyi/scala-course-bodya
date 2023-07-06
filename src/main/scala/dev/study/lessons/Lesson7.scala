package dev.study.lessons

object Lesson7 extends App {
  val eitherRight /*: Right[Nothing, Int]*/ : Either[String, Int] = Right(1)
  val eitherLeft: Either[String, Int] = Left("Some string")
  val anotherRight: Either[String, Int] = Right(4)

  val eitherComputeResult = (for {
    r1 <- eitherRight
    r2 <- eitherLeft orElse Right(8)
    r3 <- anotherRight
  } yield r1 + r2 + r3) match {
    case Left(x) => x
    case Right(intValue) => intValue
  }

  println(eitherComputeResult)


}

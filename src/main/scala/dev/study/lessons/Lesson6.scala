package dev.study.lessons

import java.io.Closeable
import scala.io.{BufferedSource, Source}

object Lesson6 extends App {
 //control structure
/*

  def usingSource[A, R <: Closeable](closeable: R)(body: R=> A): A = try body(closeable) finally closeable.close()

  def getFile(source: String): BufferedSource = Source.fromFile(source)
  //val result2: String = usingSource(getFile("")) { source => source.getLines().mkString("") }

  val sourceBusinessLogic = (source: BufferedSource) => source.getLines().mkString("")
  val result2: String = usingSource(getFile("")) {sourceBusinessLogic}

  // monada Either
  Either
  */

  val eitherRight /*: Right[Nothing, Int]*/ : Either[String, Int] = Right(1)

  // ??? == Nothing
  //Nothing може наслідувати будь-що, але всі наслідуються від Any
  //дозволяє породити будь-який тип данних

  val eitherLeft: Either[String, Int] = Left("Some string")
  val anotherRight: Either[String, Int] = Right(4)

  val eitherComputeResult = (for {
    r1 <- eitherRight
    r2 <- eitherLeft
    r3 <- anotherRight
  } yield r1 + r2 + r3 ) match {
    case Left(_) => 0
    case Right(intValue) => intValue
  }

  println(eitherComputeResult)

  val eitherComputeResult1 = (for {
    r1 <- eitherRight
    r2 <- eitherLeft.left.map(_ => 5)
    r3 <- anotherRight
  } yield r1 + r2 + r3) match {
    case Left(x) => x
    case Right(intValue) => intValue
  }

  println(eitherComputeResult1)

  val pr = eitherLeft.left.map(_ => 0)

  val eitherComputeResult2 = (for {
    r1 <- eitherRight
    r2 <- (eitherLeft swap) map (_ => 100)
    r3 <- anotherRight
  } yield r1 + r2 + r3) match {
    case Left(x) => x
    case Right(intValue) => intValue
  }

  println(eitherComputeResult2)
/*
  ************************
  *** PATTERN MATCHING ***
  ************************
  */

  val intSeq: Seq[Int] = 1 to 10

  //1 Simple constant pattern matching
  val maybeInt: AnyVal = 10

  maybeInt match {
    case 1 => println(1)
    case 10.0F => println(10)
    case _ => println
  }

  val maybeInt1: AnyVal = true

  maybeInt1 match {
    case 1 => println(1)
    case 10.0F => println(10)
    case _ => println
  }

  val maybeInt2: AnyVal = true

  maybeInt2 match {
    case 1 => println(1)
    case 10.0F => println(10)
    case true => println("bool")
    case _ => println
  }

  // 2 case class unapply
  case class Person(name: String, age: Int)
  val person: Any = Person("Andriy", 20)

  person match {
    case Person("Mykyta", age) =>
    case _ =>
  }

  // 3 Tuple matching
  val simpleTuple: (Int, String) = (1, "2")
  val (myInt, myString) = simpleTuple

  simpleTuple match {
    case (i, str) =>
    case _ =>
  }

  val simpleTuplePerson: (String, Int) = ("Andriy", 22)
  val toPerson = (Person.apply _) tupled
    println(toPerson(simpleTuplePerson))
}

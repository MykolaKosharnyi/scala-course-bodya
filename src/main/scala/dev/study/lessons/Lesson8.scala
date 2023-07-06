package dev.study.lessons

import dev.study.lessons.Lesson6.person

import scala.::
import scala.util.matching.Regex

object Lesson8 extends App {
//continue pattern matching

  val anotherTuple: (Int, String) = 1 -> "2" // this is also Tuple and immutable Map

  val simpleTuple: (Int, String) = (1, "2")
  val (myInt, myString) = simpleTuple

  simpleTuple match {
    case (myInt, myString) =>
    case _  =>
  }

  // 4 sequence pattern matching
  var intSeq = Seq

/*  intSeq match {
    case List(a, _*) => println(s"head: $a")//interested than first is 'a'
    case head :: tail => println("")
  }*/

  val stringSeqEmpty: List[String] = Nil
  val stringSeq: List[String] = "first" :: Nil
  val stringSeq2 =  "second" :: stringSeq
  val stringSeq3: List[String] = "second" :: "first" :: Nil
  val stringSeq4: List[String] = Nil.::("first").::("second")

  //stringSeq3 and stringSeq4 the same

  stringSeq3 match {
    case List(a, _*) => println(s"head: $a")
    case head :: tail => println(s"head: $head, tail: $tail")
    case a => println(a)
  }

  //def tailRecursiveLength(list: List[String], accumulator: Long): Long

  stringSeq match {
    //case List(a, _*) => println(s"head: $a")
    case head :: second :: tail => println(s"head: $head, second: $second, tail: $tail")
    case head :: tail => println(s"head: $head, tail: $tail")
    case Nil =>
  }

  val optVal: Option[String] = None

  optVal match {
    case Some(value) => println(value) //if only this case - it would be compile error
    case None =>
  }


  // 5 Regex Pattern matching

  val regexDigit: Regex = """([0-9]+)""".r
  val regexAlphabetical: Regex = """([A-Za-z]+)""".r

  "12" match {
    case regexDigit(digit) => println(digit)
    case regexAlphabetical(a) => println(a)
    case another => println(s"not matched: $another")
  }

  // 6 variable binding pattern matching

  case class Person(name: String, age: Int)

  val person: Any = Person("Andriy", 20)

  person match {
    case person@Person(name, age) => println(s"Person: $person with name: $name $age years old")
    case _ =>
  }

  // 7 type pattern matching

  person match {
    case person: Person => println(s"Person: $person")
    case _ =>
  }

  // 8 guards (filters)

  person match {
    case person: Person if person.age > 21 => println(s"Person: $person")
    case youngPerson => println(s"very young person: $youngPerson")
  }

  person match {
    case person@Person(_, age) if age > 18 => println(s"Person: $person")
    case youngPerson => println(s"very young person: $youngPerson")
  }

}

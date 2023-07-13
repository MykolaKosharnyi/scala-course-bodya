package dev.study.lessons

import scala.annotation.tailrec

object Lesson11_continue_function extends App {
  /*
  * high order function - функція, що приймає як аргументи інші функції або повертає іншу функцію як результат
  * */

  // partial functions
  val division = new PartialFunction[Int, Int] {

    override def isDefinedAt(x: Int): Boolean = x != 0

    override def apply(v1: Int): Int = 100 / v1
  }

  println(s"result_1: ${division(10)}")
  //println(s"result_2: ${division(0)}")

  val division_guard: PartialFunction[Int, Int] = {
    case x if x != 0 => 100 / x
  }

  println(s"result_3: ${division_guard(10)}")
  //println(s"result_4: ${division_guard(0)}")


  val divisionNonZero: PartialFunction[Int, Either[Int, Int]] = {
    case x if x != 0 => Right(100 / x) //it's not pattern matching, it's two methods
  }

  val alternative: PartialFunction[Int, Either[Int, Int]] = {
    case _ => Left(0)
  }

  val andThenFunction: PartialFunction[Either[Int, Int], String] = {
    case x => x.toString
  }

  //if not alternative - Match error, not matter about 'andThen'
  //val divisionWithEither: PartialFunction[Int, Either[Int, Int]] =  divisionNonZero orElse alternative
  val divisionWithEither =  (divisionNonZero orElse alternative) andThen andThenFunction  // after 'andThen' always will be executed
  println(s"result_5: ${divisionWithEither(10)}")
  println(s"result_6: ${divisionWithEither(0)}")


  type MyDomainType = PartialFunction[Int, Either[Int, Int]]
  def chainPartialFunction(domains: List[MyDomainType]): Option[MyDomainType] = {

    @tailrec
    def chainDomains(domains: List[MyDomainType], acc: MyDomainType): MyDomainType = domains match {
      case Nil => acc
      case head :: tail => chainDomains(tail, acc orElse head)
    }


    domains.headOption match {
      case Some(firstDomain) => Option(chainDomains(domains.tail, firstDomain))
      case None => None
    }
  }

  val domainList = List(divisionNonZero, alternative)
  val chainDomainFunction: MyDomainType = chainPartialFunction(domainList) getOrElse alternative

  println(s"result_7: ${chainDomainFunction(0)}")


  // lifting

  val liftedFunction: Int => Option[Either[Int, Int]] = divisionNonZero.lift //read what is 'lift'
  //val liftedFunction: Int => Option[Either[Int, Int]] = (divisionNonZero orElse alternative) lift
  println(s"result_8: ${liftedFunction(10)}")
  println(s"result_9: ${liftedFunction(0)}")


  val seqInt = Seq(1,2)
  val param3: Option[Int] = seqInt.lift(3) // it's more save that just 'seqInt(3)'


  /*****   FUNCTION1   *****/
  val square = (a: Int) => a * a
  val adder = (b: Int) => b + 2
  val composed = square compose adder
  val chained = square andThen adder

  println(s"composed: ${composed(10)}")
  println(s"chained: ${chained(10)}")
}

package dev.study.lessons

import scala.annotation.tailrec

/*
*  TYPE FUNCTION
* */
object Lesson10 extends App {
  // function also objects


  type LibraryComputationType = Int => Int

  def sum(a: Int, b: Int) = a + b

  // 1 when all parameters not available
  val sumFunc: (Int, Int) => Int = sum _ //partial applied function

  // a few nanoseconds later

  // computation done
  sumFunc(1,2)


  // 2 partial parameter assignment
  /*
  * can do maximum lazy computation
  * */
  val partialSumApplying = sumFunc(1, _: Int) //partial applied function


  // a few nanoseconds later in another sub function

  val r2: Int = partialSumApplying(2)

  def adder(d: Int, func: LibraryComputationType): Int = func(d * 2)

  println(adder(5, partialSumApplying))

  //partial applied function - частковий асайнинг параметрів

  //TODO глянути відмінність partial applied і currying function
  //викликається тільки один параметр фукції

    /////// currying

  /*
  * Каррування або каррінг — метод обчислення функції
  * від багатьох аргументів, перетворенням її
  * в послідовність функцій одного аргумента.
  * */

  def sumTriple(a: Int, b: Int, c: Int): Int = a + b + c

  val sumTripleFunc: (Int, Int, Int) => Int = sumTriple _

  val sumTripleCurrFunc: Int => Int => Int => Int  = (sumTriple _).curried

  //sumTripleCurrFunc(0)(1)(2)
  val another2: Int => Int => Int = sumTripleCurrFunc(0)
  val another3: Int => Int = another2(1)

  val resultOfCurring: Int = another3(5)

  ///// high order function

  def biFunction(a: Int, b: Int, computationFunc: (Int, Int) => Int) = {
    //some computation
    println((a, b))
    computationFunc(a, b)
  }


  val partialFunctionTriple = sumTriple(10, _: Int, _: Int)
  val sumResult = biFunction(1, 2, _ + _)
  val division = biFunction(1,2, _ / _)
  val multiplying = biFunction(1,2, _*_)

  val anotherResult = biFunction(1, 2, partialFunctionTriple)


  //// recursion
  // inner method or function
  def factorial(number: Int): Int = {

    @tailrec
    def factorialCompute(n: Int, acc: Int = 1): Int = {
      if (n <= 1) acc else factorialCompute(n - 1, acc * n)
    }

    factorialCompute(number)
  }

  //1 to 10 foreach println(s"${_} : ${factorial _}")
  println(factorial(6))

}

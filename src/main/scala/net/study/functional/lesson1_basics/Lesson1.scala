package net.study.functional.lesson1_basics

import java.util.UUID
import java.util.concurrent.{Executors, TimeUnit}

object Lesson1 {
  def main(args: Array[String]): Unit = {
   //statement - нічого не вертає
   //expression

   println("\n--- FIRST LESSON ---")

   val myString = "name" + 1 //like final in java
   // myString = "2" - can't do that, to have opportunity to use it should use 'var'

   var myChangeableValue = "first value"
   myChangeableValue = "second value"

    def sum(first: Int, second: Int) = first + second //method - creates object every time when called
    val sumFuncFromMethod: (Int, Int) => Int = sum

    val sumFunc = (first: Int, second: Int) => first + second //function - creates only ones, by first call

    var result = sum(1, 2)
    println("result: " + result)
    println(sum(Int.MaxValue, Int.MaxValue))

   println("\n--- SECOND LESSON ---")

   //var - can be reassingmen any time

   val generateFunc: () => UUID = { //function
    val random = UUID.randomUUID()
    () => random
   }

   println(generateFunc())
   println(generateFunc())

   def generateUUID: () => UUID = { //method
    val random = UUID.randomUUID()
    () => random
   }

   println(generateUUID())
   println(generateUUID())

   // laze val
   println("--- lazy block ---")
   lazy val myLazyString: String = { // created only when first time called
    println("I am ready")
    "Hello!"
   }

   //lazy can't be used with 'var'

   val executor = Executors.newSingleThreadScheduledExecutor()
   executor.schedule(new Runnable {
    override def run(): Unit = println(myLazyString)
   }, 5, TimeUnit.SECONDS)


   val range = 1 to 10
   range.foreach(println(_))
   range.foreach(println)

   println((1 until 10).foldLeft(0)((acc, c) => acc + c))
   println((1 until 10).foldLeft(0)(_ + _))
   println((1 until 10).foldLeft(0)(sum))
   println((1 until 10).sum)

   //процедура вищого порядку
   def calculateAndSend(first: Int, second: Int)(postFunc: Int => Unit): Unit = {
    postFunc(second + first)
   }

     //first example
   calculateAndSend(1, 2) {
    result => println(result)
   }

   //second example
   val postProcessResult: Int => Unit = r => println(r)
   calculateAndSend(1, 2) {postProcessResult}

   //тернарного оператора немає

   val myString1 = ""
   // ідіоматична ініціалізація
   val result1: Int = if (myString1.nonEmpty) 1 else 0

   val unit: Unit = ()
   val nullAble: Int = null.asInstanceOf[Int]
   val intOptional: Option[Int] = None

   //null зводити до мінімуму і працювати через optional

   // монади, вбудовані функції

   // з джави не прешло - break (в скалі як метод)

   //tail recursion - не забиває пам'ять

   //




  }
}

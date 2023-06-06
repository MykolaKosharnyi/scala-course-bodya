package net.study.functional.lesson1_basics

object Lesson1 {
  def main(args: Array[String]): Unit = {
   val myString = "name" + 1 //like final in java
   // myString = "2" - can't do that, to have opportunity to use it should use 'var'

   var myChangeableValue = "first value"
   myChangeableValue = "second value"

    def sum(first: Int, second: Int) = first + second //method - creates object every time when called
    val sumFuncFromMethod: (Int, Int) => Int = sum

    val sumFunc = (first: Int, second: Int) => first + second //function - creates only ones

    var result = sum(1, 2)
    println("result: " + result)
    println(sum(Int.MaxValue, Int.MaxValue))
  }
}

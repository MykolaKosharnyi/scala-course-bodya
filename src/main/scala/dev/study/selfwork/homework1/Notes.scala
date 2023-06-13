package dev.study.selfwork.homework1

object Notes {
  def main(args: Array[String]): Unit = {
    /*
    Scala has two types of variables:

    val is an immutable variable — like final in Java — and should be preferred
    var creates a mutable variable, and should only be used when there is a specific reason to use it
    Examples:
     */
    val x = 1 //immutable
    var y = 0 //mutable


//     val x = if (a < b) a else b

/*    val result = i match {
      case 1 => "one"
      case 2 => "two"
      case _ => "not 1 or 2"
    }*/

    //The match expression isn’t limited to just integers,
    // it can be used with any data type, including booleans
/*    val booleanAsString = bool match {
      case true => "true"
      case false => "false"
    }*/


    // an example of match being used as the body of a method,
    // and matching against many different types
/*    def getClassAsString(x: Any): String = x match {
      case s: String => s + " is a String"
      case i: Int => "Int"
      case f: Float => "Float"
      case l: List[_] => "List"
      case p: Person => "Person"
      case _ => "Unknown"
    }*/

    // try/catch
/*    try {
      writeToFile(text)
    } catch {
      case fnfe: FileNotFoundException => println(fnfe)
      case ioe: IOException => println(ioe)
    }*/


    // for loops
    println("\nfirst loop example")
    for (arg <- args) println(arg)

    // "x to y" syntax
    println("\nsecond loop example")
    for (i <- 0 to 5) println(i)

    // "x to y by" syntax
    println("\nthird loop example")
    for (i <- 0 to 10 by 2) println(i)

    println("\nforth loop example")
    val x1 = for (i <- 1 to 5) yield i * 2
    for (i <- x1) println(i)

    println("\n'5 loop example'")
    val fruits = List("apple", "banana", "lime", "orange")

    val fruitLengths = for {
      f <- fruits
      if f.length > 4
    } yield f.length
    for (i <- fruitLengths) println(i)


    //Classes
    println("\n Classes examples")
    class Person(var firstName: String, var lastName: String) {
      def printFullName(): Unit = println(s"$firstName $lastName")
    }
    val p = new Person("Julia", "Kern")
    println(p.firstName)
    p.lastName = "Manes"
    p.printFullName()
  }
}

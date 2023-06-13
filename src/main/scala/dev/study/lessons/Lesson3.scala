package dev.study.lessons

//for expression
object Lesson3 extends App {
  //  iterable -
  //  traversable -

  // монадний і контейнерний підхід
  //  стрім по дефолту також коллекція

  //by Name, by Value - send value in method signature
  //by default immutable collections
  val seqInt = Seq(1, 2, 3, 4, 5)
  seqInt.foreach(println)

  for(x <- seqInt) println(x)

  //for each - side effect

  val result = seqInt.map(x => x * x) // creates new collections

  //for comprehension

  val result2 = for {
    n <- seqInt
  } yield n * n

  //map use iterator

  val flattenResult = seqInt.flatMap { n =>
    //println(s"--- multiply by $n ---")
    result.map(innerNode => s"$n * $innerNode = ${n * innerNode}")
  }

  flattenResult.foreach(println)

  val forComprehensionResult = for { // працює з дійсними компонентами
    a <- seqInt
    b <- seqInt
    c <- seqInt
  } yield {
    s"Vp ($a * $b * $c) = ${a * b * c}"
  }

  forComprehensionResult.foreach(println)

  val param1: Option[Int] = Some(1)
  //val param2 = Some(2)
  val param2: Option[Int] = None
  val param3: Option[Int] = Some(3)

  val forOptionComprehensionResult = for {
    a <- param1
    b <- param2
    c <- param3
  } yield  {
    a * b * c
  }

  forOptionComprehensionResult.foreach(println)

  //контейнери повинні бути одного типу
  //значення повинні існувати



  val param11: Option[Long] = Some(1L)
  val param21: Option[Int] = Some(2)
  val param31: Option[String] = Some("3")

  val forOptionComprehensionResult1: Option[(Long, Int, String)] = for {
    a <- param11
    b <- param21
    c <- param31
  } yield {
    (a, b, c + "d")
  }

  forOptionComprehensionResult1.foreach(println)

  // RULES FOR FLATTENING
  val seqInt2 = Seq(1, 2, 3, 4, 5)
  val multiplier = (x : Int) => x * x

  println((seqInt2 map multiplier) == seqInt2.flatMap(x => Seq(multiplier(x))))
  println(seqInt2 map multiplier)

  val filterResult = seqInt2 filter(_ % 2 == 0) map multiplier //два цикла
  filterResult.foreach(println)

  val withFilterResult = seqInt2 withFilter (_ % 2 == 0) map multiplier // один цикл
  withFilterResult.foreach(println)

  println(filterResult == withFilterResult)

  val forOptionComprehensionWithGuardResult = for {
    x <- seqInt2 if x % 2 == 0
  } yield multiplier(x)

  println(forOptionComprehensionWithGuardResult == withFilterResult)

  val forOptionComprehensionWithGuards: Seq[String] = for {
    a <- seqInt if a % 2 == 0
    b <- seqInt if b % 2 != 0
    c <- seqInt if c % 2 == 0
  } yield {
    s"Vp ($a * $b * $c) = ${a * b * c}"
  }

  forOptionComprehensionWithGuards.foreach(println)


}

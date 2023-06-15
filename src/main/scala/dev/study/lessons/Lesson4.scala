package dev.study.lessons

//CASE CLASSES AND MONADED
object Lesson4 extends App {
  // в функціональному стилі стараються працювати з монадами
  //case classes
  // homework flattering
  //якщо робимо іммютабле, то ми робимо гарантію що ми не зможемо змінити стан обєкту

  //контракт говорить що ми повинні завжди передавати іммютабель ібєкти
  case class Person (name: String, age: Int = 30, isMarried: Boolean = true, surName: String)

  private val personA1 = Person("Sasha", 20, isMarried = true, "")
  private val personB1 = Person("Sasha", 20, isMarried = false, "")

  println(personA1 == personB1) //object comparison
  println(personA1 equals personB1) //object comparison
  println(personA1 eq personB1) //link comparison
  println(personA1 eq personA1) //link comparison

  Person.apply("A", 23, isMarried = false, "") //завдяки компаньон обджекту

  private val personA = Person("Sasha", 20, surName ="Petya")
  private val personB = Person("Ivan", 21, isMarried = false, "Igor")

  private val personC = personA.copy(age = 21)
  private def analyze(person: Person): String = person match {
    case Person(name, 20, isMarried, surName) => s"name=$name, age=20"
    case Person("Ivan", age, isMarried, surName) => s"name=Ivan, age=$age"
    //case Person(name, age) => s"name=$name, age=$age"
    case a => s"another person = $a"
    //case _ => "no person"
  }

  println(analyze(personA))
  println(analyze(personB))
  println(analyze(personC))

  val personD = Person("Oleg", surName = "Ivanov")
  val personD1 = Person(name = "Oleg", surName = "Ivanov")


  //OPTIONS
  //simple but effective monada
  //monage - це контейнер з внутрішнім контекстом, яка завжди має мінімум два методи
    //flapMap, Unit - apply

  //родити саму себе, зафлетити саму себе


  //робота з можливим контекстом, який може існувати
  private val maybeInt: Option[Int] = Option(1) //сприймається як колллекція

  val maybeInt11 = Some(1)
  val maybeInt22 = None

  // точно как же можна проаналізувати за допомогою паттерн матчингу

  val resultContext = maybeInt match {
    case Some(a) => a
    case None => 0
  }

  private val maybeInt2: Option[Int] = None

  val mergeMonad: Option[Int] = maybeInt2 orElse maybeInt

  (maybeInt2 orElse Some({
    println("Calculate alternative")
    1
  })) match {
    case Some(value) => println(s"to do smth $value")
    case None => println("to do smth none")
  }

//  val result = maybeInt.getOrElse({
//    println("Calculate alternative")
//    1
//  })

}

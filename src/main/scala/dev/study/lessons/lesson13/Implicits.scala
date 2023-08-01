package dev.study.lessons.lesson13

object Implicits extends App {
  //implicit methods / functions

  implicit def sum(x: Int, y: Int) = x + y

  def computation(x: Int, y: Int)(implicit biFunc: (Int, Int) => Int) = biFunc(x,y)

  println(computation(2, 3))

  //imports, introduction into OOP
  // lesson9_packages in Bogdan code

  //ad hoc polymorphism  - useful for writing comparator
  //sub type polymorphism, learn different type of polymorphism

  //discover inheritance, look at diamond problems
}

package dev.study.lessons.lesson12

import dev.study.lessons.someWord
import dev.study.lessons.myGlobalTime

import java.util.Date
import java.util.concurrent.Executors
import scala.concurrent.{ExecutionContext, ExecutionContextExecutorService, Future}

object Implicits extends App {
  //import scala.concurrent.ExecutionContext.Implicits.global
  implicit val executionContext: ExecutionContextExecutorService = ExecutionContext.fromExecutorService(Executors.newSingleThreadExecutor())

  //implicit val someWord = "Hello"

  def writeWord(personName: String)(implicit word: String, date: Date) = println(personName + " " + word)

  writeWord("John")

  val myFuture = Future{println("Hello!")}
}

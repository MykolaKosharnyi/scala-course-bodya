package dev.study.lessons

import com.some.mypack.inner.myanotherPack.MyClass

object Lesson12_packages extends App {
  val x = MyClass

}

package com.some.mypack {
  package inner.myanotherPack {

    case class MyClass(x: Int)
  }
}
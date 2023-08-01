package dev.study.lessons.lesson12

class PrimaryClass {
  private val privateProperty = "privateProperty"

  private[this] val privateThisProperty = "privateThisProperty" //only on instance which created
  private[lesson12] val privatePackageProperty = "privatePackageProperty"

  protected val protectedOnlyProperty = "protectedOnlyProperty"
  protected[lesson12] val protectedPackageProperty = "protectedPackageProperty"
  protected[this] val protectedThisProperty = "protectedThisProperty"

  val publicProperty = "publicProperty"

  val primaryClass = new PrimaryClass

  primaryClass.privateProperty
  //primaryClass.privateThisProperty -- impossible to call
}

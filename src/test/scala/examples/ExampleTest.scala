package examples

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, OptionValues, WordSpec}

@RunWith(classOf[JUnitRunner])
class ExampleTest extends WordSpec
  with OptionValues
  with Matchers {

  import dev.study.lessons.Lesson9._

  //what we testing

  //unit of testing
  "TestClass(Lesson9)" when {
    //method of testing
    "sum" should {
      //behavior assertion
      "calculate right positive result" in {

        sum(1, 2) shouldEqual 3
      }

      "calculate right negative result" in {

        sum(-1, -2) shouldEqual -3
      }
    }

    "divide" should {
      "calculate right division result" in {
        devide(9, 3) shouldEqual 3
      }

      "fail with ArithmeticException with intercept" in {
        val catchResult: ArithmeticException = intercept[ArithmeticException] {
          devide(3, 0)
        }

        println(catchResult)

        catchResult.getMessage shouldEqual "/ by zero"
      }

      "fail with ArithmeticException with assertion" in {
        assertThrows[ArithmeticException] {
          devide(3, 0)
        }
      }
    }

  }

}

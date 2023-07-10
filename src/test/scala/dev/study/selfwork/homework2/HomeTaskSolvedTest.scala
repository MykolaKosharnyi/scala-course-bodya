package dev.study.selfwork.homework2

import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

/*
    TESTS FOR FIRST PRACTICAL HOMEWORK
 */

@RunWith(classOf[JUnitRunner])
class HomeTaskSolvedTest extends WordSpec with Matchers {

  import dev.study.selfwork.homework2.HomeTaskSolved._

  //what we testing

  //unit of testing
  "PaymentInfo service" when {
    //method of testing
    "call getPaymentSum" should {
      //behavior assertion
      "return present input sum" in {
        val inputDto = PaymentInfoDto(1, Some("customerA"), Some(1500), None, Some("payment for Iphone 15"))
        getPaymentSum(inputDto.paymentId, inputDto.sum) shouldEqual inputDto.sum
      }

      "calculate payment because input sum not present (id = 1)" in {
        val inputDto = PaymentInfoDto(1, Some("customerA"), None, None, Some("payment for Iphone 15"))
        getPaymentSum(inputDto.paymentId, inputDto.sum) shouldEqual None
      }

      "calculate payment because input sum not present (id = 11)" in {
        val inputDto = PaymentInfoDto(11, Some("customerA"), None, None, Some("payment for Iphone 15"))
        getPaymentSum(inputDto.paymentId, inputDto.sum) shouldEqual Some(1100)
      }
    }

    "calculateAlternativeTax" should {
      "calculate result if sum > 100 (200)" in {
        calculateAlternativeTax(200) shouldEqual Some(40)
      }

      "calculate result if sum < 100 (50)" in {
        calculateAlternativeTax(50) shouldEqual Some(0)
      }

      "calculate result if sum == 100 (100)" in {
        calculateAlternativeTax(50) shouldEqual Some(0)
      }
    }

    "getTax" should {
      "return input value on present tax" in {
        getTax(Some(10), 200) shouldEqual Some(10)
      }

      "return generated value on not present tax (sum 200)" in {
        getTax(None, 200) shouldEqual Some(40)
      }

      "return generated value on not present tax (sum 50)" in {
        getTax(None, 50) shouldEqual Some(0)
      }
    }

    "getDesc" should {
      "return input value on it presence" in {
        getDesc(Some("input desc")) shouldEqual Some("input desc")
      }

      "return generated desc on empty input" in {
        getDesc(None) shouldEqual Some("technical")
      }
    }

    "getPaymentInfo" should {
      "correctly generate payment info" in {

        result shouldEqual List(
          PaymentInfo(1,1500,300,"payment for Iphone 15"),
          PaymentInfo(3,99,0,"payment for headphone"),
          PaymentInfo(4,1000,200,"technical"),
          PaymentInfo(5,2500,500,"technical"),
          PaymentInfo(6,600,120,"payment for Oculus quest 2"),
          PaymentInfo(7,1500,300,"payment for Iphone 15"),
          PaymentInfo(8,-400,0,"roll back transaction"),
          PaymentInfo(9,900,180,"some payment")
        )
      }

    }
  }

}
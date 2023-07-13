package dev.study.selfwork.homework3

import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

import java.io.ByteArrayInputStream
import scala.io.BufferedSource

@RunWith(classOf[JUnitRunner])
class BodyaSolutionTest extends WordSpec with Matchers {

  import dev.study.selfwork.homework3.BodyaSolution._

  //what we testing

  //unit of testing
  "ExceptionTest service" when {
    import scala.util.{Failure, Success}
    val fileSource = "src/test/resources/homework3/test.txt"

    // TESTS FOR LEVEL 1
    "toSubscriber" should {
      "return split value" in {
        toSubscriber("0673052785;1") shouldEqual Some(SubscriberInfo("0673052785", 1, false))
      }

      "return None" in {
        toSubscriber("0673052785341") shouldEqual None
      }
    }

    "toSubscribers" should {
      "incorrect string" in {
        val s: String = "hello, world"
        val inputStream = new ByteArrayInputStream(s.getBytes)
        val bufferedSource: BufferedSource = new BufferedSource(inputStream)

        toSubscribers(bufferedSource) shouldEqual List()
        // Close the bufferedSource when done
        bufferedSource.close()

      }

      "correct string" in {
        val s: String =
          """0673052785;1
            |0673052786;0
            |0673052787;1
            |0673052788;1
            |0673052789;0
            |""".stripMargin
        val inputStream = new ByteArrayInputStream(s.getBytes)
        val bufferedSource: BufferedSource = new BufferedSource(inputStream)

        toSubscribers(bufferedSource) shouldEqual List(
          SubscriberInfo("0673052785",1,false),
          SubscriberInfo("0673052786",0,false),
          SubscriberInfo("0673052787",1,false),
          SubscriberInfo("0673052788",1,false),
          SubscriberInfo("0673052789",0,false)
        )

        // Close the bufferedSource when done
        bufferedSource.close()

      }
    }

    "enrichSubscriber" should {
      //behavior assertion
      "search result not present" in {
        val subscriberInfo: SubscriberInfo = SubscriberInfo("0673052785",1,false)
        enrichSubscriber(subscriberInfo, None) shouldEqual Some(subscriberInfo)
      }

      "search result eq 0" in {
        val subscriberInfo: SubscriberInfo = SubscriberInfo("0673052785", 1, false)
        enrichSubscriber(subscriberInfo, Some(0)) shouldEqual Some(subscriberInfo)
      }

      "search result eq 1" in {
        val subscriberInfo: SubscriberInfo = SubscriberInfo("0673052785", 1, false)
        enrichSubscriber(subscriberInfo, Some(1)) shouldEqual Some(SubscriberInfo("0673052785", 1, true))
      }
    }

    // TESTS FOR LEVEL 2
    "tryGetSubscribers" should {

      "throw NetworkException" in {
        val result = tryGetSubscribers(true, "anyString")
        val catchResult: NetworkException = intercept[NetworkException]  {
          result.get
        }

        catchResult.x shouldEqual "SFTP server network exception"
      }

      "successfully read data" in {
        val result = tryGetSubscribers(false, fileSource)

        result match {
          case Success(value) => value shouldEqual List(
              SubscriberInfo("0673052785", 1, false),
              SubscriberInfo("0673052786", 0, false),
              SubscriberInfo("0673052787", 1, false),
              SubscriberInfo("0673052788", 1, false),
              SubscriberInfo("0673052789", 0, false)
            )

          case Failure(_) => fail
        }
      }
    }

    "tryGetEnrichmentSource" should {

      val poorSubscribers = List(
        SubscriberInfo("0673052785", 1, false),
        SubscriberInfo("0673052786", 0, false),
        SubscriberInfo("0673052787", 1, false),
        SubscriberInfo("0673052788", 1, false),
        SubscriberInfo("0673052789", 0, false)
      )

      "throw TemporaryUnavailableException (both resources unavailable)" in {

        val result = tryGetEnrichmentSource(true, true, poorSubscribers map(_.msisdn))
        result match {
          case Success(_) => fail
          case Failure(exception) => exception shouldEqual TemporaryUnavailableException("Temporary Unavailable Exception")
        }

      //or test like this
        val catchResult: TemporaryUnavailableException = intercept[TemporaryUnavailableException] {
          result.get
        }

        catchResult.string shouldEqual "Temporary Unavailable Exception"
      }

      val successResult = Map(
        "0673052786" -> 1,
        "0673052787" -> 0,
        "0673052788" -> 1,
        "0673052789" -> 0,
        "0673052785" -> 0
      )

      "success (first source unavailable)" in {
        val result = tryGetEnrichmentSource(true, false, poorSubscribers map (_.msisdn))
        result match {
          case Success(value) => value shouldEqual successResult
          case Failure(_) => fail
        }
      }

      "success (both source available)" in {
        val result = tryGetEnrichmentSource(false, false, poorSubscribers map (_.msisdn))
        result match {
          case Success(value) => value shouldEqual successResult
          case Failure(_) => fail
        }
      }
    }

    "tryToEnrichWithSource" should {

      val subscribers = List(
        SubscriberInfo("0673052785", 1, false),
        SubscriberInfo("0673052786", 0, false),
        SubscriberInfo("0673052787", 1, false),
        SubscriberInfo("0673052788", 1, false),
        SubscriberInfo("0673052789", 0, false)
      )

      val enrichmentSource = Map(
        "0673052786" -> 1,
        "0673052787" -> 0,
        "0673052788" -> 1,
        "0673052789" -> 0,
        "0673052785" -> 0
      )

      "successfully enrich" in {

        val resultExpected = List(
          SubscriberInfo("0673052785", 1, false),
          SubscriberInfo("0673052786", 0, true),
          SubscriberInfo("0673052787", 1, false),
          SubscriberInfo("0673052788", 1, true),
          SubscriberInfo("0673052789", 0, false)
        )

        val result = tryToEnrichWithSource(subscribers, enrichmentSource)
        result match {
          case Success(value) => resultExpected shouldEqual value
          case Failure(_) => fail
        }
      }
    }

    "tryToSendToProvider" should {
      val subscribers = Seq(
        SubscriberInfo("0673052785", 1, false),
        SubscriberInfo("0673052786", 0, true),
        SubscriberInfo("0673052787", 1, false),
        SubscriberInfo("0673052788", 1, true),
        SubscriberInfo("0673052789", 0, false)
      )

      "throw ThirdPartySystemException" in {
        val result = tryToSendToProvider(true, subscribers)
        val catchResult: ThirdPartySystemException = intercept[ThirdPartySystemException] {
          result.get
        }

        catchResult.string shouldEqual "third party system exception"
      }

      "successfully send data" in {
        val result = tryToSendToProvider(false, subscribers)

        result match {
          case Success(_) => succeed
          case Failure(_) => fail
        }
      }
    }

    // TESTS FOR LEVEL 3
    "enrichAndSend" should {
      val subscribers = Seq(
        SubscriberInfo("0673052785", 1, false),
        SubscriberInfo("0673052786", 0, true),
        SubscriberInfo("0673052787", 1, false),
        SubscriberInfo("0673052788", 1, true),
        SubscriberInfo("0673052789", 0, false)
      )

      "should receive NetworkError" in {
        val result = enrichAndSend(true, false, false, false, fileSource)
        result match {
          case Left(value) => NetworkError shouldEqual value
          case Right(_) => fail
        }
      }

      "should receive AllSourceTemporaryUnavailableError" in {
        val result = enrichAndSend(false, true, true, false, fileSource)
        result match {
          case Left(value) => AllSourceTemporaryUnavailableError shouldEqual value
          case Right(_) => fail
        }
      }

      "should receive ThirdPartySystemError" in {
        val result = enrichAndSend(false, false, false, true, fileSource)
        result match {
          case Left(value) => ThirdPartySystemError shouldEqual value
          case Right(_) => fail
        }
      }

      "should receive AnyOtherError(file not found)" in {
        val result = enrichAndSend(false, false, false, false, "")
        result match {
          case Left(value) => AnyOtherError shouldEqual value
          case Right(_) => fail
        }
      }

      "success after enriching and sending" in {
        val result = enrichAndSend(false, false, false, false, fileSource)

        result match {
          case Left(_) => fail
          case Right(value) => 5 shouldEqual value
        }
      }

    }

  }
}

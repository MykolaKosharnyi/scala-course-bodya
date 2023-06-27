package dev.study.selfwork.homework3

import scala.io.Source._
import scala.language.postfixOps
import scala.util.{Failure, Success, Try}

object HomeTaskSolved extends App {

  // 1) try to read file from external system over network (use method getFile with two columns: 1) msisdn, subscriber type)
  // and don't forget to close resource after usage!!!!!!

  // 2) try to enrich get Data using main source getDataFromMainSource

  // 3) if fail to execute step 2) go to alternative source and try once more ( use getDataFromAlternativeSource)

  // 4) if success to do so, try to send to 3-d party system all list

  // 5) Implement enrichAndSend method with proper Left(Error) type or Rigiht[Int] Quantity of msisdns send to our third party system

  // Conditions:
  // use only Try Monad to resolve all problems with exception handling
  // You can use any additional custom functions / methods
  // Don't use method Try monad methods as get, getOrElse, isSuccess, isFailure !!!!!

  /// ===============help code ======================

  trait Error

  case object NetworkError extends Error // if sftp server not available

  case object SourceTemporaryUnavailableError extends Error // if main source main source unavailable

  case object AllSourceTemporaryUnavailableError extends Error //if all source were unavailable

  case object ThirdPartySystemError extends Error //if 3-d party system error

  case class TemporaryUnavailableException(string: String) extends Exception

  case class ThirdPartySystemException(string: String) extends Exception

  case class SubscriberInfo(msisdn: String, subscriberType: Int, isActive: Boolean)

  val fileSource = "src/main/resources/homework3/externalSourceFile.txt"
SubscriberInfo
  case class NetworkException(x: String) extends Exception

  // do not change this methods !!!!
  @throws[NetworkException]
  def getFile(isRisky: Boolean, source: String) = if (isRisky) throw NetworkException("SFTP server network exception") else fromFile(source)

  @throws[TemporaryUnavailableException]
  def getActiveData(isRisky: Boolean, msisdns: Seq[String]) = if (isRisky) throw TemporaryUnavailableException("Temporary Unavailable Exception") else {
    msisdns.map(m => (m, if (m.toInt % 2 == 0) 1 else 0))
  }

  @throws[TemporaryUnavailableException]
  def getDataFromMainSource(isRisky: Boolean, msisdns: Seq[String]) = getActiveData(isRisky, msisdns)

  @throws[TemporaryUnavailableException]
  def getDataFromAlternativeSource(isRisky: Boolean, msisdns: Seq[String]) = getActiveData(isRisky, msisdns)

  def sendToProvider(isRisky: Boolean, msisdns: Seq[SubscriberInfo]): Unit =
    if (isRisky) throw ThirdPartySystemException("third party system exception") else msisdns.foreach(m => println(s"Sent $m"))


  /*
    def enrichAndSend(getFileIsRisky: Boolean,
                      getDataFromMainSourceIsRisky: Boolean,
                      getDataFromAlternativeSourceIsRisky: Boolean,
                      sendToProviderIsRisky: Boolean,
                      fileSource: String): Either[Error, Int] = ???

  */


  /********  DEVELOPMENT TEST (realization at the bottom)  *********/

  /*    readData(false, fileSource) match {
        case Success(result) => println(s"result of read data: $result")
        //case Success(result) => println(s"result=${result map (_._1)}")
        case Failure(ex) => println(s"exception during read data: $ex")
      }*/


/*  //test enrich data
  val enrichResult: Try[Seq[(String, Int)]] = for {
    inputData <- readData(false, fileSource)
    enrichedData <- enrichData(false, false, inputData)
  } yield enrichedData

  enrichResult match {
    case Success(result) => println(s"result of enriching data: $result")
    case Failure(ex) => println(s"exception during enriching data: $ex")
  }*/


  /*  // make subscriber info
    val makeSubscriberInfoResult: Try[Seq[SubscriberInfo]] = for {
      inputData <- readData(false, fileSource)
      enrichedData <- enrichData(false, false, inputData)
      result <- Try(toSubscriberInfo(inputData, enrichedData))
    } yield result

    makeSubscriberInfoResult match {
      case Success(result) => println(s"result of converted data: $result")
      case Failure(ex) => println(s"exception during converting data: $ex")
    }*/


  /*  // send to subscriber test block
    val sendToSubscriberResult: Try[Seq[SubscriberInfo]] = for {
      inputData <- readData(false, fileSource)
      enrichedData <- enrichData(false, false, inputData)
      subscriberSeq <- Try(toSubscriberInfo(inputData, enrichedData))
      _ <- Try(sendToProvider(false, subscriberSeq))

    } yield subscriberSeq

    sendToSubscriberResult match {
      case Success(result) => println(s"result of converted(with sending) data: $result")
      case Failure(ex) => println(s"exception during converting(with sending) data: $ex")
    }*/


  /*  // enrich and send test block
    val enrichAndSend: Try[Seq[SubscriberInfo]] = for {
      inputData <- readData(false, fileSource)
      enrichedData <- enrichData(false, false, inputData)
      subscriberSeq <- Try(toSubscriberInfo(inputData, enrichedData))
      _ <- Try(sendToProvider(false, subscriberSeq))

    } yield subscriberSeq

    enrichAndSend match {
      case Success(result) => println(s"result of converted(with sending) data: $result")
      case Failure(ex) => println(s"exception during converting(with sending) data: $ex")
    }*/

  //  def sendAndCountMsisdn(msisdnList: Seq[SubscriberInfo])(postFunc: Seq[SubscriberInfo] => Int): Int
  //  def countSizeFunction: Seq[SubscriberInfo] => Int = (sequence: Seq[SubscriberInfo]) => sequence.size

  /********  IMPLEMENTATION  *********/

  private def readLines(isRisky: Boolean, source: String): Seq[String] = {
    val data = getFile(isRisky, source)
    try data.getLines.toList finally data.close()
  }

 private  def parseLine(row: String): (String, Int) = {
    val splitString = row.split(";")
    splitString match {
      case Array(name, age) =>
        val nameValue: String = name
        val ageValue: Int = age.toInt
        (nameValue, ageValue)

      // Handle the case when the split string does not have exactly two elements
      case _ => throw NetworkException("Wrong data in file") //need to change to another exception
    }
  }

  private def readData(isRisky: Boolean, source: String): Try[Seq[(String, Int)]] = Try(readLines(isRisky, source) map parseLine)

  private def getMsisdnFromInputFile(inputData: Seq[(String, Int)]): Seq[String] = inputData map (_._1)

  private def enrichData(isRisky1: Boolean, isRisky2: Boolean, readData: Seq[(String, Int)]): Try[Seq[(String, Int)]] = {
    val inputMsisdn: Seq[String] = getMsisdnFromInputFile(readData)

    Try(getDataFromMainSource(isRisky1, inputMsisdn)).recoverWith {
      case _: Exception => Try(getDataFromAlternativeSource(isRisky2, inputMsisdn))
    }
  }

  private def toSubscriberInfo(inputData: Seq[(String, Int)], enrichedData: Seq[(String, Int)]): Seq[SubscriberInfo] = for {
    ind <- inputData
    result <- checkActive(ind, enrichedData)
  } yield result

  private def checkActive(msisdnInputInfo: (String, Int), enrichedData: Seq[(String, Int)]): Option[SubscriberInfo] = for {
    ed <- enrichedData.find(_._1 == msisdnInputInfo._1)
  } yield SubscriberInfo(msisdnInputInfo._1, msisdnInputInfo._2, ed._2 != 0)

  private def enrichAndSendTry(getFileIsRisky: Boolean,
                               getDataFromMainSourceIsRisky: Boolean,
                               getDataFromAlternativeSourceIsRisky: Boolean,
                               sendToProviderIsRisky: Boolean,
                               fileSource: String): Try[Int] = for {
    inputData <- readData(getFileIsRisky, fileSource)
    enrichedData <- enrichData(getDataFromMainSourceIsRisky, getDataFromAlternativeSourceIsRisky, inputData)
    subscriberSeq <- Try(toSubscriberInfo(inputData, enrichedData))
    _ <- Try(sendToProvider(sendToProviderIsRisky, subscriberSeq))
    count <- Try(subscriberSeq.size)

  } yield count

  private def convertToCustomException: Throwable => Error = (ex: Throwable) => ex match {
    case NetworkException(_) => NetworkError
    case TemporaryUnavailableException(_) => AllSourceTemporaryUnavailableError
    case ThirdPartySystemException(_) => ThirdPartySystemError
  }

  private def convertTryToEither(count: Try[Int]): Either[Error, Int] = {
    count match {
      case Success(result) => Right(result)
      case Failure(exception) => Left(convertToCustomException(exception))
    }
  }

  def enrichAndSend(getFileIsRisky: Boolean,
                    getDataFromMainSourceIsRisky: Boolean,
                    getDataFromAlternativeSourceIsRisky: Boolean,
                    sendToProviderIsRisky: Boolean,
                    fileSource: String): Either[Error, Int] = {
    val countTryResult = enrichAndSendTry(getFileIsRisky, getDataFromMainSourceIsRisky, getDataFromAlternativeSourceIsRisky, sendToProviderIsRisky, fileSource);
    convertTryToEither(countTryResult)
  }

  /********  TEST BLOCK  *********/
  enrichAndSend(true, true, true, true, fileSource) match {
    case Right(x) => print(s"FAILED-1, output value: $x")
    case Left(value) => println(s"test1 is correct($value) - ${value == NetworkError}")
  }

  enrichAndSend(false, true, true, true, fileSource) match {
    case Right(_) => print("FAILED-2")
    case Left(value) => println(s"test2 is correct($value) - ${value == AllSourceTemporaryUnavailableError}")
  }

  enrichAndSend(false, true, false, true, fileSource) match {
    case Right(_) => print("FAILED-3")
    case Left(value) => println(s"test3 is correct($value) - ${value == ThirdPartySystemError}")// because second source is OK
  }

  enrichAndSend(false, false, true, true, fileSource) match {
    case Right(_) => print("FAILED-4")
    case Left(value) => println(s"test4 is correct($value) - ${value == ThirdPartySystemError}") // because first source is OK
  }

  enrichAndSend(false, false, false, true, fileSource) match {
    case Right(_) => print("FAILED-4")
    case Left(value) => println(s"test4 is correct($value) - ${value == ThirdPartySystemError}")
  }

  enrichAndSend(false, false, false, false, fileSource) match {
    case Right(x) => print(s"Test 5 success, count: $x")
    case Left(_) => println("test 5 failed")
  }

  // in 'sendToProvider' was added to print messages in console!
  // by logic and this realization no need 'SourceTemporaryUnavailableError' because 'AllSourceTemporaryUnavailableError' can handle it
}
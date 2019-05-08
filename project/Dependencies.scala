/**
  * PROPRIETARY AND CONFIDENTIAL
  *
  * Unauthorized copying of this file via any medium is strictly prohibited.
  *
  * Copyright (c) 2018-2019 Snowplow Analytics Ltd. All rights reserved.
  */

import sbt._

object Dependencies {

  object V {
    val awsLambdaEvents      = "2.2.2"
    val snowplowAnalyticsSdk = "0.4.1"
    val awsKinesis           = "1.11.453" 
  }

  val awsLambdaEvents      = "com.amazonaws"         % "aws-lambda-java-events"        % V.awsLambdaEvents
  val snowplowAnalyticsSdk = "com.snowplowanalytics" %% "snowplow-scala-analytics-sdk" % V.snowplowAnalyticsSdk
  val awsKinesis           = "com.amazonaws"         % "aws-java-sdk-kinesis"          % V.awsKinesis

}

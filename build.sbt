/*
 * Copyright (c) 2019-2019 Snowplow Analytics Ltd. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0, and
 * you may not use this file except in compliance with the Apache License
 * Version 2.0.  You may obtain a copy of the Apache License Version 2.0 at
 * http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Apache License Version 2.0 is distributed on an "AS
 * IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the Apache License Version 2.0 for the specific language
 * governing permissions and limitations there under.
 */
lazy val root = project
  .in(file("."))
  .settings(
    name := "analytics-lambda-tsv-to-json",
    organization := "com.snowplowanalytics.snowplow.lambda",
    version := "0.1.0-SNAPSHOT",
    description := "Lambda triggered by Kinesis Analytics to transform the events of the enriched stream from TSV to JSON",
    scalaVersion := "2.11.12",
    scalacOptions := BuildSettings.compilerOptions,
    javacOptions := BuildSettings.javaCompilerOptions
  )
  .settings(BuildSettings.formatting)
  .settings(BuildSettings.assemblyOptions)
  .settings(
    libraryDependencies ++= Seq(
      Dependencies.awsLambdaEvents,
      Dependencies.awsKinesis,
      Dependencies.snowplowAnalyticsSdk
    )
  )

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
package com.snowplowanalytics.snowplow.lambda

import com.amazonaws.services.lambda.runtime.events.KinesisAnalyticsStreamsInputPreprocessingEvent
import com.amazonaws.services.lambda.runtime.events.KinesisAnalyticsInputPreprocessingResponse
import com.amazonaws.services.lambda.runtime.events.KinesisAnalyticsInputPreprocessingResponse.Result
import com.amazonaws.services.lambda.runtime.events.KinesisAnalyticsInputPreprocessingResponse.Record
import com.snowplowanalytics.snowplow.analytics.scalasdk.Event
import scala.collection.JavaConverters._
import java.nio.charset.StandardCharsets
import java.nio.CharBuffer

class LambdaHandler {

  def recordsHandler(event: KinesisAnalyticsStreamsInputPreprocessingEvent)
    : KinesisAnalyticsInputPreprocessingResponse = {
    val responseRecords = event.getRecords.asScala
      .map(r =>
        (r.getRecordId(),
         new String(r.getData().array(), StandardCharsets.UTF_8)))
      .map { case (recordId, tsv) => (recordId, Event.parse(tsv).toOption) }
      .map {
        case (recordId, maybeParsed) =>
          maybeParsed match {
            case Some(parsed) =>
              (recordId, Result.Ok, parsed.toJson(true).toString())
            case None =>
              (recordId, Result.ProcessingFailed, "")
          }
      }
      .map {
        case (recordId, result, json) =>
          new Record(
            recordId,
            result,
            StandardCharsets.UTF_8.newEncoder().encode(CharBuffer.wrap(json)))
      }
      .asJava

    val response = new KinesisAnalyticsInputPreprocessingResponse()
    response.setRecords(responseRecords)
    response
  }
}

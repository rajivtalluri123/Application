package io.hmheng.grading.streams.kinesis;

import io.hmheng.grading.learnosity.domain.StudentSession;
import io.hmheng.grading.streams.config.StreamConfiguration;
import io.hmheng.grading.streams.kinesis.model.KinesisPutRecordResult;

/**
 * Created by nandipatim on 6/26/17.
 */
public interface KinesisStreamDataService {

  KinesisPutRecordResult pushToStream(Object objectToStream,StreamConfiguration streamConfiguration);

}

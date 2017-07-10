package io.hmheng.grading.streams.kinesis.model;

import com.amazonaws.services.kinesis.model.PutRecordResult;
import com.amazonaws.services.kinesis.producer.Attempt;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by nandipatim on 6/20/17.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KinesisPutRecordResult extends PutRecordResult implements Serializable{

  private List<Attempt> attempts;

  private boolean successful;

  public KinesisPutRecordResult(PutRecordResult putRecordResult){
    setSequenceNumber(putRecordResult.getSequenceNumber());
    setShardId(putRecordResult.getShardId());
  }

  public KinesisPutRecordResult withAttempts(List<Attempt> attempts) {

    setAttempts(attempts);
    return this;
  }

  public KinesisPutRecordResult withIsSucessful(Boolean isSucessful){
    setSuccessful(isSucessful);
    return this;
  }

  public String getSequenceNumber(){
    return super.getSequenceNumber();
  }

  public String getShardId(){
    return super.getShardId();
  }
}

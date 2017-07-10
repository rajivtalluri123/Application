package io.hmheng.grading.streams.grading.domain;

/**
 * Created by pabonaj on 6/6/17.
 */
public enum StudentAssignmentStatus {

  //ORDINAL OF  IN_PROGRESS(0)/READY_FOR_SCORING(1)/SCORING_IN_PROGRESS(2)/COMPLETED(3)
  IN_PROGRESS(Boolean.FALSE ,  "InComplete" , "session-started"),
  READY_FOR_SCORING(Boolean.FALSE , "ReadyForScoring" , "ready-for-scoring"),
  SCORING_IN_PROGRESS(Boolean.TRUE,"ScoringInProgress" , "scoring-in-progress"),
  SCORING_COMPLETED(Boolean.FALSE , "Completed" , "scoring-completed");

  public static StudentAssignmentStatus fromOrdinal(int ordinal) {
    int valuesCount = values().length;
    if (ordinal < 0 || ordinal >= valuesCount) {
      throw new IllegalArgumentException("ordinal " + ordinal + " is less than 0 or " +
          "greater than the number of values ["+valuesCount+"]");
    }
    return values()[ordinal];
  }

  public static StudentAssignmentStatus fromOrdinal(String ordinal) {
    if(ordinal == null){
      return null;
    }
    return fromOrdinal(Integer.valueOf(ordinal));
  }

  private final Boolean acceptScores;
  private final String scoringStatusString;
  private final String assignmentStatusEvent;

  StudentAssignmentStatus(Boolean acceptScores, String status, String assignmentStatusEvent){
    this.acceptScores = acceptScores;
    this.scoringStatusString = status;
    this.assignmentStatusEvent = assignmentStatusEvent;
  }

  public Boolean isAcceptScores() {
    return acceptScores;
  }

  public String getScoringStatusString() {
    return scoringStatusString;
  }

  public String getAssignmentStatusEvent() {
    return assignmentStatusEvent;
  }
}

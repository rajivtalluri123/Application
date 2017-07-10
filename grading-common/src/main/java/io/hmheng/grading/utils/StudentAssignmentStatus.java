package io.hmheng.grading.utils;

import java.util.Arrays;

public enum StudentAssignmentStatus {

    //ORDINAL OF  IN_PROGRESS(0)/READY_FOR_SCORING(1)/SCORING_IN_PROGRESS(2)/COMPLETED(3)
    IN_PROGRESS(Boolean.FALSE , "Incomplete" , "session-started" , Boolean.TRUE),
    READY_FOR_SCORING(Boolean.FALSE , "ReadyForScoring" , "ready-for-scoring", Boolean.TRUE),
    SCORING_IN_PROGRESS(Boolean.TRUE , "ScoringInProgress" , "scoring-in-progress", Boolean.FALSE),
    SCORING_COMPLETED(Boolean.FALSE , "Completed" , "scoring-completed" , Boolean.FALSE);

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
    private final String statusPushToScoring;
    private final String statusPushToAssignmnet;
    private final Boolean processToGrading;

    StudentAssignmentStatus(Boolean acceptScores , String statusPushToScoring , String statusPushToAssignmnet, Boolean processToGrading){
        this.acceptScores = acceptScores;
        this.statusPushToScoring = statusPushToScoring;
        this.statusPushToAssignmnet = statusPushToAssignmnet;
        this.processToGrading = processToGrading;
    }

    public Boolean isAcceptScores() {
        return acceptScores;
    }

    public String getStatusPushToScoring() {
        return statusPushToScoring;
    }

    public String getStatusPushToAssignmnet() {
        return statusPushToAssignmnet;
    }

    public Boolean isProcessToGrading(){
        return processToGrading;
    }


    public static StudentAssignmentStatus fromStatusPushToScoring(String statusPushToScoring){
        if(statusPushToScoring == null){
            throw new IllegalArgumentException("Push to Scoring Status can't be null");
        }

        return Arrays.stream(values()).filter(hl -> hl.getStatusPushToScoring().equals(statusPushToScoring)).findFirst()
            .orElse(null);
    }
}

DROP  VIEW IF EXISTS   activitystudentscore_view;



create or replace VIEW
  activitystudentscore_view
  (
      responseId,
      scoreReference,
      staffPersonalRefId,
      studentQuestionRefId,
      attempted,
      value,
      weight,
	    maxScore,
      Score,
      title,
      description,
      automarkable,
      correctResponse,
      score_type
  ) AS
 SELECT
    distinct ss.response_id,
    	    	ss.score_reference,
    	    	ss.staff_personal_refid,
    	    	ss.student_question_refid,
            ss.attemted,
            ss.value,
            ss.weight,
            ss.max_score,
            ss.score,
            score.title,
            score.description,
            score.automarkable,
            score.correct_response,
            score.type
            from student_score ss
            inner join
            	score score on
                ss.score_reference = score.score_reference;


DROP  VIEW IF EXISTS   activitystudentitem_view;


create or replace VIEW
  activitystudentitem_view
  (
      refId,
      sessionId,
      time,
      itemReference,
      maxScore,
      userFlagged,
      organisationId,
      itemPoolId,
      type,
      automarkable
  ) AS
 SELECT
    distinct st.refid,st.session_refid, st.time,item.item_reference, st.max_score, st.user_flagged,
                item.organisation_id, item.item_pool_id, item.type, automarkable
                from student_item st inner join  item item on  st.item_reference = item.item_reference
                inner join student_question sq on sq.student_item_refid = st.refid
                inner join question q on q.question_reference = sq.question_reference;


DROP  VIEW IF EXISTS   activitystudentquestion_view;

create or replace VIEW
  activitystudentquestion_view
  (
      refId,
      questionReference,
      studentItemRefId,
      actualResponse,
      responseId,
      rubricReference,
      questionType,
      automarkable
  ) AS
 SELECT
    distinct sq.refid,
    		sq.question_reference,
    		sq.student_item_refid,
    		sq.actual_response,
    		sq.response_id,
            question.rubric_reference,
            question.question_type,
            question.automarkable
            from student_question sq
            inner join
            	question question on
                sq.question_reference = question.question_reference;
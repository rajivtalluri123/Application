

DROP  VIEW IF EXISTS activitystudentquestion_view;

alter table public.question alter column question_type type character varying(32);

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



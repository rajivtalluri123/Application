alter table public.STUDENT_SESSION ADD COLUMN SCORING_STREAM_SEQ VARCHAR(64);
alter table public.STUDENT_SESSION ADD COLUMN SCORING_PUSH_DT TIMESTAMP;
alter table public.STUDENT_SESSION ADD COLUMN STATUS_STREAM_SEQ VARCHAR(64);
alter table public.STUDENT_SESSION ADD COLUMN STATUS_PUSH_DT TIMESTAMP;
alter table public.ACTIVITY ADD COLUMN ACTIVITY_TEMPLATE_ID VARCHAR(40);
alter table public.SCORE ADD COLUMN TYPE VARCHAR(8);

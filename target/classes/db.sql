DROP TABLE ADMIN;
DROP TABLE STUDENT;
DROP TABLE SLOT;
DROP TABLE LECTURE;
DROP TABLE TEACHER;
DROP TABLE CLASSROOM;
DROP TABLE QUIZ;
DROP TABLE COURSE;

CREATE TABLE COURSE (
                        courseid varchar2(10),
                        coursename varchar2(50),
                        credithrs number,
                        section varchar2(10),
                        constraint course_pk PRIMARY KEY (courseid, section)
);

CREATE TABLE QUIZ (
                      topic varchar2(100),
                      taken number,
                      quizid number,
                      courseid varchar2(10),
                      section varchar2(10),
                      constraint fk_course FOREIGN KEY (courseid, section) REFERENCES COURSE(courseid, section),
                      constraint quiz_pk PRIMARY KEY (quizid, courseid, section)
);

CREATE TABLE CLASSROOM (
                           classroomid varchar2(10),
                           type varchar2(10),
                           vacancy number,
                           constraint classroom_pk PRIMARY KEY (classroomid)
);


CREATE TABLE SLOT (
                      slot varchar2(50),
                      type varchar2(10),
                      constraint slot_pk PRIMARY KEY (slot)
);

CREATE TABLE LECTURE (
                         lectureid varchar2(10),
                         classroomid varchar2(10),
                         quizid number,
                         courseid varchar2(10),
                         day varchar2(10),
                         slot varchar2(50),
                         section varchar2(10),
                         constraint lecture_pk PRIMARY KEY (lectureid),
                         constraint fk_classrooom FOREIGN KEY (classroomid) REFERENCES CLASSROOM(classroomid),
                         constraint fk_quiz FOREIGN KEY (quizid, courseid, section) REFERENCES QUIZ(quizid, courseid, section),
                         constraint fk_slot FOREIGN KEY (slot) REFERENCES SLOT(slot)
);

CREATE TABLE ADMIN (
                       name varchar2(50),
                       email varchar2(50),
                       adminid varchar2(10),
                       pss varchar2(50),
                       constraint adminid_pk PRIMARY KEY (adminid)
);

CREATE TABLE TEACHER (
                         name varchar2(50),
                         email varchar2(50),
                         teacherid varchar2(10),
                         pss varchar2(50),
                         courseid varchar2(10),
                         section varchar2(10),
                         constraint fk_course_teacher FOREIGN KEY (courseid, section) REFERENCES COURSE(courseid, section),
                         constraint teacherid_pk PRIMARY KEY (teacherid, courseid, section)
);

DROP TABLE STUDENT;

CREATE TABLE STUDENT (
                         name varchar2(50),
                         email varchar2(50),
                         studentid varchar2(10),
                         pss varchar2(50),
                         courseid varchar2(10),
                         section varchar2(10),
                         constraint fk_course_student FOREIGN KEY (courseid, section) REFERENCES COURSE(courseid, section),
                         constraint studentid_pk PRIMARY KEY (studentid, courseid, section)
);

INSERT INTO SLOT VALUES ('8:30 - 9:50', 'Class'), ('10:00 - 11:20', 'Class'), ('11:30 - 12:50', 'Class'), ('01:00 - 02:20', 'Class'), ('02:30 - 03:50', 'Class'), ('03:55 - 05:15', 'Class'), ('05:20 - 06:40', 'Class'), ('06:45 - 08:05', 'Class');
INSERT INTO SLOT VALUES ('8:30 - 11:15', 'Lab'), ('11:25 - 02:10', 'Lab'), ('02:25 - 05:10', 'Class'), ('05:20 - 08:05', 'Class');

commit;
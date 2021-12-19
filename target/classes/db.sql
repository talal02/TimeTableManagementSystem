DROP TABLE ADMIN;
DROP TABLE STUDENT;
DROP TABLE LECTURE;
DROP TABLE TEACHER;
DROP TABLE CLASSROOM;
DROP TABLE QUIZ;
DROP TABLE COURSE;
DROP TABLE SLOT;

CREATE TABLE COURSE (
                        courseid varchar2(10),
                        coursename varchar2(100),
                        credithrs number,
                        section varchar2(30),
                        constraint course_pk PRIMARY KEY (courseid, section)
);

CREATE TABLE QUIZ (
                      topic varchar2(100),
                      quizid number,
                      courseid varchar2(10),
                      section varchar2(10),
                      constraint fk_course FOREIGN KEY (courseid, section) REFERENCES COURSE(courseid, section),
                      constraint quiz_pk PRIMARY KEY (quizid, courseid, section)
);

CREATE TABLE CLASSROOM (
                           classroomid varchar2(30),
                           type varchar2(10),
                           vacancy number,
                           constraint classroom_pk PRIMARY KEY (classroomid)
);


CREATE TABLE SLOT (
                      slot varchar2(100),
                      type varchar2(10),
                      constraint slot_pk PRIMARY KEY (slot)
);

CREATE TABLE LECTURE (
                         lectureid varchar2(10),
                         classroomid varchar2(10),
                         quizid number,
                         courseid varchar2(10),
                         day varchar2(10),
                         slot varchar2(100),
                         section varchar2(10),
                         constraint lecture_pk PRIMARY KEY (lectureid),
                         constraint fk_classrooom FOREIGN KEY (classroomid) REFERENCES CLASSROOM(classroomid),
                         constraint fk_quiz FOREIGN KEY (quizid, courseid, section) REFERENCES QUIZ(quizid, courseid, section),
                         constraint fk_slot FOREIGN KEY (slot) REFERENCES SLOT(slot)
);

CREATE TABLE ADMIN (
                       name varchar2(100),
                       email varchar2(100),
                       adminid varchar2(10),
                       pss varchar2(100),
                       constraint adminid_pk PRIMARY KEY (adminid)
);

CREATE TABLE TEACHER (
                         name varchar2(100),
                         email varchar2(100),
                         teacherid varchar2(10),
                         pss varchar2(100),
                         courseid varchar2(10),
                         section varchar2(10),
                         constraint fk_course_teacher FOREIGN KEY (courseid, section) REFERENCES COURSE(courseid, section),
                         constraint teacherid_pk PRIMARY KEY (teacherid, courseid, section)
);

CREATE TABLE STUDENT (
                         name varchar2(100),
                         email varchar2(100),
                         studentid varchar2(10),
                         pss varchar2(100),
                         courseid varchar2(10),
                         section varchar2(10),
                         notification varchar2(150),
                         constraint fk_course_student FOREIGN KEY (courseid, section) REFERENCES COURSE(courseid, section),
                         constraint studentid_pk PRIMARY KEY (studentid, courseid, section)
);

INSERT INTO SLOT VALUES ('08:30 - 09:50', 'Class');
INSERT INTO SLOT VALUES ('10:00 - 11:20', 'Class');
INSERT INTO SLOT VALUES ('11:30 - 12:50', 'Class');
INSERT INTO SLOT VALUES ('01:00 - 02:20', 'Class');
INSERT INTO SLOT VALUES ('02:30 - 03:50', 'Class');
INSERT INTO SLOT VALUES ('03:55 - 05:15', 'Class');
INSERT INTO SLOT VALUES ('05:20 - 06:40', 'Class');
INSERT INTO SLOT VALUES ('06:45 - 08:05', 'Class');
INSERT INTO SLOT VALUES ('02:25 - 05:10', 'Class');
INSERT INTO SLOT VALUES ('05:20 - 08:05', 'Class');

INSERT INTO SLOT VALUES ('08:30 - 11:15', 'Lab');
INSERT INTO SLOT VALUES ('11:25 - 02:10', 'Lab');
INSERT INTO SLOT VALUES ('02:30 - 05:15', 'Lab');

INSERT INTO CLASSROOM VALUES ('C-301', 'Classroom', '0');
INSERT INTO CLASSROOM VALUES ('C-302', 'Classroom', '0');
INSERT INTO CLASSROOM VALUES ('C-303', 'Classroom', '0');
INSERT INTO CLASSROOM VALUES ('C-304', 'Classroom', '0');
INSERT INTO CLASSROOM VALUES ('C-305', 'Classroom', '0');
INSERT INTO CLASSROOM VALUES ('C-306', 'Classroom', '0');
INSERT INTO CLASSROOM VALUES ('C-307', 'Classroom', '0');
INSERT INTO CLASSROOM VALUES ('C-308', 'Classroom', '0');

INSERT INTO CLASSROOM VALUES ('C-401', 'Classroom', '0');
INSERT INTO CLASSROOM VALUES ('C-402', 'Classroom', '0');
INSERT INTO CLASSROOM VALUES ('C-403', 'Classroom', '0');
INSERT INTO CLASSROOM VALUES ('C-404', 'Classroom', '0');
INSERT INTO CLASSROOM VALUES ('C-405', 'Classroom', '0');
INSERT INTO CLASSROOM VALUES ('C-406', 'Classroom', '0');
INSERT INTO CLASSROOM VALUES ('C-407', 'Classroom', '0');
INSERT INTO CLASSROOM VALUES ('C-408', 'Classroom', '0');

INSERT INTO CLASSROOM VALUES ('Rawal-I', 'Lab', '0');
INSERT INTO CLASSROOM VALUES ('Rawal-II', 'Lab', '0');
INSERT INTO CLASSROOM VALUES ('Rawal-III', 'Lab', '0');
INSERT INTO CLASSROOM VALUES ('Margalla-I', 'Lab', '0');
INSERT INTO CLASSROOM VALUES ('Margalla-II', 'Lab', '0');
INSERT INTO CLASSROOM VALUES ('Margalla-III', 'Lab', '0');

INSERT INTO COURSE VALUES ('CS-1002', 'Programming Fundamentals', '3', 'A,B,C,D,E,F,G');
INSERT INTO COURSE VALUES ('CL-1002', 'Programming Fundamentals Lab', '1', 'A,B,C,D,E,F,G');
INSERT INTO COURSE VALUES ('CS-1004', 'Object Oriented Programming', '3', 'A,B,C,D,E,F,G');
INSERT INTO COURSE VALUES ('CL-1004', 'Object Oriented Programming Lab', '1', 'A,B,C,D,E,F,G');
INSERT INTO COURSE VALUES ('CS-1005', 'Discrete Structures', '3', 'A,B,C,D,E,F,G');
INSERT INTO COURSE VALUES ('CS-2001', 'Data Structures', '3', 'A,B,C,D');
INSERT INTO COURSE VALUES ('CL-2001', 'Data Structures Lab', '1', 'A,B,C,D');
INSERT INTO COURSE VALUES ('CS-2005', 'Database Systems', '3', 'A,B,C');
INSERT INTO COURSE VALUES ('CL-2005', 'Database Systems Lab', '1', 'A,B,C');
INSERT INTO COURSE VALUES ('CS-2006', 'Operating Systems', '3', 'A,B,C');
INSERT INTO COURSE VALUES ('CL-2006', 'Operating Systems Lab', '3', 'A,B,C');
INSERT INTO COURSE VALUES ('CS-2009', 'Design and Analysis of Algorithms', '3', 'A,B,C');
INSERT INTO COURSE VALUES ('CS-3005', 'Theory of Automata', '3', 'A,B,C,D,E,F,G');

--INSERT INTO LECTURE (LECTUREID, CLASSROOMID, COURSEID, DAY, SLOT, SECTION) VALUES ('1', 'C-301','CS-1002', 'Monday', '08:30 - 09:50', 'A');
--INSERT INTO LECTURE (LECTUREID, CLASSROOMID, COURSEID, DAY, SLOT, SECTION) VALUES ('2', 'C-302','CS-1004', 'Monday', '08:30 - 09:50', 'B');
--INSERT INTO LECTURE (LECTUREID, CLASSROOMID, COURSEID, DAY, SLOT, SECTION) VALUES ('3', 'C-303','CS-1005', 'Monday', '10:00 - 11:20', 'C');
--INSERT INTO LECTURE (LECTUREID, CLASSROOMID, COURSEID, DAY, SLOT, SECTION) VALUES ('4', 'Rawal-I','CL-2001', 'Monday', '11:25 - 02:10', 'D');
--
--INSERT INTO LECTURE (LECTUREID, CLASSROOMID, COURSEID, DAY, SLOT, SECTION) VALUES ('5', 'C-402','CS-3005', 'Tuesday', '01:00 - 02:20', 'F');



commit;
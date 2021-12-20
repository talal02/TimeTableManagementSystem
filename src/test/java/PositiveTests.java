import com.database.Database;
import com.database.PersistenceHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class PositiveTests {
    PersistenceHandler db;


    @BeforeEach
    public void before() throws SQLException, ClassNotFoundException {
        db = Database.getInstance();
    }

    @Test
    public void testAddClass() {
        Assertions.assertTrue(db.addClassroom("C-306", "Class"));
    }

    @Test
    public void testAddCourse() {
        Assertions.assertTrue(db.addCourse("CS-1000", "IICT", 3, "A,B,C"));
    }

    @Test
    public void testAddTeacher() {
        Assertions.assertTrue(db.addTeacher(""+99, "Zubair Khan", "zubair.khan@nu.edu.pk", "1234", "CS-1000", "A"));
    }

    @Test
    public void testAddStudent() {
        Assertions.assertTrue(db.addStudent("i190545@nu.edu.pk", "1234", ""+99, "CS-1000", "A", "Ahmed Ali"));
    }

    @Test
    public void testAddAdmin() {
        Assertions.assertTrue(db.addAdmin("admin@nu.edu.pk", "1234", "" + 2));
    }

    @Test
    public void testRemoveClass() {
        Assertions.assertTrue(db.removeClassroom("CS-306"));
    }

    @Test
    public void testRemoveCourse() {
        Assertions.assertTrue(db.removeCourse("CS-1000"));
    }

    @Test
    public void testRemoveTeacher() {
        Assertions.assertTrue(db.removeTeacher("zubair.khan@nu.edu.pk"));
    }

}

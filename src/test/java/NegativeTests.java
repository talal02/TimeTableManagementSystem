import com.database.Database;
import com.database.PersistenceHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class NegativeTests {
    PersistenceHandler db;


    @BeforeEach
    public void before() throws SQLException, ClassNotFoundException {
        db = Database.getInstance();
    }

    @Test
    public void testAddClass() {
        Assertions.assertFalse(db.addClassroom("C-301", "Class"));
    }

    @Test
    public void testAddCourse() {
        Assertions.assertFalse(db.addCourse("CL-1002", "IICT", 3, "A"));
    }

    @Test
    public void testAddTeacher() {
        Assertions.assertFalse(db.addTeacher("" + 100, "", "j.i@nu.edu.pk", "1234", "CS-3005", "E"));
    }

    @Test
    public void testAddStudent() {
        Assertions.assertFalse(db.addStudent("i190727", "1234", "" + 99, "CS-1000", "A", "Talal"));
    }

    @Test
    public void testAddAdmin() {
        Assertions.assertFalse(db.addAdmin("admin@nu.edu.pk", "1234", "" + 2));
    }

    @Test
    public void testRemoveClass() {
        Assertions.assertFalse(db.removeClassroom("CS-316"));
    }

    @Test
    public void testRemoveCourse() {
        Assertions.assertFalse(db.removeCourse("CS-10088"));
    }

    @Test
    public void testRemoveTeacher() {
        Assertions.assertFalse(db.removeTeacher("zubair.khan@nu.edu.pk"));
    }

}

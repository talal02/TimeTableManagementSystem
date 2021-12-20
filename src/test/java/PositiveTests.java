import com.database.Database;
import com.database.PersistenceHandler;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class PositiveTests {
    PersistenceHandler db;


    @BeforeEach
    public void before() throws SQLException, ClassNotFoundException {
        db = Database.getInstance();
    }

    @Test
    public void testA() {
        Assertions.assertTrue(db.addClassroom("C-306", "Class"));
    }

    @Test
    public void testB() {
        Assertions.assertTrue(db.addCourse("CS-1000", "IICT", 1, "A"));
    }

    @Test
    public void testC() {
        Assertions.assertTrue(db.addTeacher(""+99, "Zubair Khan", "zubair.khan@nu.edu.pk", "1234", "CS-1000", "A"));
    }

    @Test
    public void testD() {
        Assertions.assertTrue(db.addStudent("i190545@nu.edu.pk", "1234", ""+99, "CS-1000", "A", "Ahmed Ali"));
    }

    @Test
    public void testE() {
        Assertions.assertTrue(db.addAdmin("admin@nu.edu.pk", "1234", "" + 2));
    }

    @Test
    public void testF() {
        Assertions.assertTrue(db.removeClassroom("C-306"));
    }

    @Test
    public void testG() {
        Assertions.assertTrue(db.removeTeacher("zubair.khan@nu.edu.pk"));
    }

    @Test
    public void testH() {
        Assertions.assertTrue(db.removeCourse("CS-1000"));
    }
}

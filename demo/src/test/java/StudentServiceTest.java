

import com.example.demo.model.Student;
import com.example.demo.Repository.DepartmentRepository;
import com.example.demo.Repository.StudentRepository;
import com.example.demo.exception.StudentException;
import com.example.demo.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    void getAllStudents_shouldReturnAllStudents() {
        // ARRANGE - prepare fake data
        List<Student> fakeStudents = List.of(
            new Student(1, "John", 20),
            new Student(2, "Jane", 22)
        );
        when(studentRepository.findAll()).thenReturn(fakeStudents);

        // ACT - call the method
        List<Student> result = studentService.getAllStudents();

        // ASSERT - check the result
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getName());
    }

    // TEST 2 - Get student by ID (success)
    @Test
    void getStudentById_shouldReturnStudent_whenExists() {
        // ARRANGE
        Student fakeStudent = new Student(1, "John", 20);
        when(studentRepository.findById(1)).thenReturn(Optional.of(fakeStudent));

        // ACT
        Student result = studentService.getStudentById(1);

        // ASSERT
        assertEquals("John", result.getName());
        assertEquals(20, result.getAge());
    }

    // TEST 3 - Get student by ID (not found)
    @Test
    void getStudentById_shouldThrowException_whenNotExists() {
        // ARRANGE
        when(studentRepository.findById(999)).thenReturn(Optional.empty());

        // ACT + ASSERT
        assertThrows(StudentException.class, () -> {
            studentService.getStudentById(999);
        });
    }

    // TEST 4 - Add student
    @Test
    void addStudent_shouldSaveAndReturnStudent() {
        // ARRANGE
        Student fakeStudent = new Student(1, "John", 20);
        when(studentRepository.save(any(Student.class))).thenReturn(fakeStudent);

        // ACT
        Student result = studentService.addStudent("John", 20, null);

        // ASSERT
        assertEquals("John", result.getName());
        assertEquals(20, result.getAge());
    }

    // TEST 5 - Delete student (success)
    @Test
    void deleteStudent_shouldReturnTrue_whenExists() {
        // ARRANGE
        when(studentRepository.existsById(1)).thenReturn(true);

        // ACT
        boolean result = studentService.deleteStudent(1);

        // ASSERT
        assertTrue(result);
        verify(studentRepository, times(1)).deleteById(1);
    }

    // TEST 6 - Delete student (not found)
    @Test
    void deleteStudent_shouldReturnFalse_whenNotExists() {
        // ARRANGE
        when(studentRepository.existsById(999)).thenReturn(false);

        // ACT
        boolean result = studentService.deleteStudent(999);

        // ASSERT
        assertFalse(result);
    }
}



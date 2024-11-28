package com.cybersoft.baitap30_11.controller;

import com.cybersoft.baitap30_11.domain.StudentSpecification;
import com.cybersoft.baitap30_11.dto.RegistrationDTO;
import com.cybersoft.baitap30_11.dto.StudentDTO;
import com.cybersoft.baitap30_11.entity.Course;
import com.cybersoft.baitap30_11.entity.Registration;
import com.cybersoft.baitap30_11.entity.Student;
import com.cybersoft.baitap30_11.repository.CourseRepository;
import com.cybersoft.baitap30_11.repository.RegistrationRepository;
import com.cybersoft.baitap30_11.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private RegistrationRepository registrationRepository;

    // POST: Thêm mới sinh viên
    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody StudentDTO studentDTO) {
        Student student = new Student();
        student.setName(studentDTO.getStudentName());
        student.setEmail(studentDTO.getEmail());
        student.setAge(studentDTO.getAge());

        Student savedStudent = studentRepository.save(student);
        StudentDTO savedStudentDTO = convertStudentToDTO(savedStudent);
        return new ResponseEntity<>(savedStudentDTO, HttpStatus.CREATED);
    }

    // GET: Lấy danh sách tất cả sinh viên
    @GetMapping
    public ResponseEntity<?> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        List<StudentDTO> studentDTOs = students.stream()
                .map(this::convertStudentToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(studentDTOs, HttpStatus.OK);
    }

    // GET: Lấy thông tin sinh viên theo id
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable int id) {
        return studentRepository.findById(id)
                .map(student -> new ResponseEntity<>(convertStudentToDTO(student), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // PUT: Cập nhật thông tin sinh viên theo id
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable int id, @RequestBody StudentDTO studentDTO) {
        return studentRepository.findById(id)
                .map(existingStudent -> {
                    existingStudent.setName(studentDTO.getStudentName());
                    existingStudent.setEmail(studentDTO.getEmail());
                    existingStudent.setAge(studentDTO.getAge());

                    Student updatedStudent = studentRepository.save(existingStudent);
                    return new ResponseEntity<>(convertStudentToDTO(updatedStudent), HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // DELETE: Xóa sinh viên theo id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable int id) {
        return studentRepository.findById(id)
                .map(student -> {
                    studentRepository.delete(student);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // GET: Tìm kiếm sinh viên theo tên (keyword)
    @GetMapping("/search")
    public ResponseEntity<?> searchStudents(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer ageFrom,
            @RequestParam(required = false) Integer ageTo,
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name,asc") String[] sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort[0]).ascending());

        Specification<Student> spec = Specification.where(null);

        if (name != null) {
            spec = spec.and(StudentSpecification.hasName(name));
        }
        if (ageFrom != null && ageTo != null) {
            spec = spec.and(StudentSpecification.hasAgeBetween(ageFrom, ageTo));
        }
        if (email != null) {
            spec = spec.and(StudentSpecification.hasEmailEndingWith(email));
        }

        Page<Student> studentPage = studentRepository.findAll(spec, pageable);

        // Chuyển đổi Entity sang DTO
        List<StudentDTO> studentDTOs = studentPage.getContent().stream()
                .map(this::convertStudentToDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(studentDTOs, HttpStatus.OK);
    }



    // POST: Thêm một danh sách khóa học mà sinh viên đã đăng ký
    @PostMapping("/{studentId}/courses")
    public ResponseEntity<?> registerCoursesForStudent(@PathVariable int studentId, @RequestBody List<Integer> courseId) {
        return studentRepository.findById(studentId)
                .map(student -> {

                    List<Course> courses = courseRepository.findAllById(courseId);
                    List<Registration> registrations = courses.stream()
                            .map(course -> {
                                Registration registration = new Registration();
                                registration.setStudent(student);
                                registration.setCourse(course);
                                registration.setRegistrationDate(new Date());
                                return registration;
                            })
                            .collect(Collectors.toList());

                    registrationRepository.saveAll(registrations);

                    // Chuyển đổi sang DTO và trả về
                    List<RegistrationDTO> registrationDTOs = registrations.stream()
                            .map(this::convertToDTO)
                            .collect(Collectors.toList());

                    return new ResponseEntity<>(registrationDTOs, HttpStatus.CREATED);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    // GET: Lấy danh sách tất cả các khóa học mà một sinh viên đã đăng ký
    @GetMapping("/{studentId}/courses")
    public ResponseEntity<?> getCoursesForStudent(@PathVariable int studentId) {
        List<Registration> registrations = registrationRepository.findByStudentId(studentId);
        List<RegistrationDTO> registrationDTOs = registrations.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(registrationDTOs, HttpStatus.OK);
    }

    // POST: Thêm một sinh viên vào một khóa học
    @PostMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<?> addStudentToCourse(@PathVariable int studentId, @PathVariable int courseId) {
        return studentRepository.findById(studentId).flatMap(student ->
                courseRepository.findById(courseId).map(course -> {
                    Registration registration = new Registration();
                    registration.setStudent(student);
                    registration.setCourse(course);
                    registration.setRegistrationDate(new Date());

                    registrationRepository.save(registration);

                    RegistrationDTO registrationDTO = convertToDTO(registration);
                    return new ResponseEntity<>(registrationDTO, HttpStatus.CREATED);
                })).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // DELETE: Hủy đăng ký của sinh viên khỏi một khóa học
    @DeleteMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<?> unregisterStudentFromCourse(@PathVariable int studentId, @PathVariable int courseId) {
        List<Registration> registrations = registrationRepository.findByStudentIdAndCourseId(studentId, courseId);

        if (!registrations.isEmpty()) {
            // Xoá tất cả các bản ghi trong danh sách
            registrationRepository.deleteAll(registrations);
            return new ResponseEntity<>("Unregistered successfully!", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Registration not found!", HttpStatus.NOT_FOUND);
        }
    }




    // Helper method to convert Entity to DTO
    private StudentDTO convertStudentToDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudentId(student.getId());
        studentDTO.setStudentName(student.getName());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setAge(student.getAge());
        return studentDTO;
    }

    // Helper method to convert Registration to RegistrationDTO
    private RegistrationDTO convertToDTO(Registration registration) {
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setRegistrationId(registration.getRegistrationId());
        registrationDTO.setStudentId(registration.getStudent().getId());
        registrationDTO.setCourseId(registration.getCourse().getId());
        registrationDTO.setRegistrationDate(registration.getRegistrationDate());
        return registrationDTO;
    }

}

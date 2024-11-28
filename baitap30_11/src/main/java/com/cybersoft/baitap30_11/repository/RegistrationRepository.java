package com.cybersoft.baitap30_11.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.cybersoft.baitap30_11.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Integer> {
    List<Registration> findByStudentId(int studentId); // Truy cập vào 'id' của đối tượng 'student'
    List<Registration> findByCourseId(int courseId);  // Truy cập vào 'id' của đối tượng 'course'

    List<Registration> findByStudentIdAndCourseId(int studentId, int courseId);


}

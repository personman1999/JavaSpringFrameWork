package com.cybersoft.baitap30_11.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cybersoft.baitap30_11.entity.Student;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findStudentsByNameContaining(String keyword);

    Page<Student> findAll(Specification<Student> spec, Pageable pageable);
}

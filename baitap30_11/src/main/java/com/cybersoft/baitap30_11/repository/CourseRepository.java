package com.cybersoft.baitap30_11.repository;

import com.cybersoft.baitap30_11.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    public List<Course> findByDurationGreaterThan (int duration);

    long count();
}

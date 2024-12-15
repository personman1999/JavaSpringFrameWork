package com.cybersoft.baitap30_11.domain;

import com.cybersoft.baitap30_11.entity.Course;
import org.springframework.data.jpa.domain.Specification;

public class CourseSpecification {
    public static Specification<Course> hasDurationGreaterThan(int duration) {
        return (root, query, builder) -> builder.greaterThan(root.get("duration"), duration);
    }
}

package com.cybersoft.baitap30_11.domain;

import com.cybersoft.baitap30_11.entity.Student;
import org.springframework.data.jpa.domain.Specification;

public class StudentSpecification {
    public static Specification<Student> hasName(String name) {
        return (root, query, builder) -> builder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Student> hasAgeBetween(Integer ageFrom, Integer ageTo) {
        return (root, query, builder) -> builder.between(root.get("age"), ageFrom, ageTo);
    }

    public static Specification<Student> hasEmailEndingWith(String domain) {
        return (root, query, builder) -> builder.like(root.get("email"), "%" + domain);
    }

}

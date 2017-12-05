package com.kaishengit.pojo;

import javax.persistence.*;
import java.util.Set;

/**
 * @author zh
 * Created by Administrator on 2017/11/29.
 * @JoinTable(name = "teacher_student")配置关系表
 * joinColumns = {@JoinColumn("student_id")} student自己在关系表中的id
 * inverseJoinColumns = {@JoinColumn("teacher_id")} 值是数组形式单个值可以省略大括号， teacher在关系表中的id
 */
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "student_name")
    private String studentName;
    @ManyToMany
    @JoinTable(name = "teacher_student",
            joinColumns = {@JoinColumn(name = "student_id")},
            inverseJoinColumns = {@JoinColumn(name = "teacher_id")})
    private Set<Teacher> teacherSet;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Set<Teacher> getTeacherSet() {
        return teacherSet;
    }

    public void setTeacherSet(Set<Teacher> teacherSet) {
        this.teacherSet = teacherSet;
    }
}
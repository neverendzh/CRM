package com.kaishengit.pojo;

import javax.persistence.*;
import java.util.Set;

/**
 * @author zh
 * Created by Administrator on 2017/11/29.
 * mappedBy = "teacherSet"表示放弃关系维护
 */
@Entity
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "teacher_name")
    private String teacherName;
    @ManyToMany(mappedBy = "teacherSet")
    private Set<Student> studentSet;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Set<Student> getStudentSet() {
        return studentSet;
    }

    public void setStudentSet(Set<Student> studentSet) {
        this.studentSet = studentSet;
    }
}
package com.kaishengit.hibernate;

import com.kaishengit.pojo.Student;
import com.kaishengit.pojo.Teacher;
import com.kaishengit.util.HibernateUtil;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zh
 * Created by Administrator on 2017/11/29.
 * Hibernate中多对多的关系央射的使用
 */
public class ManyToManyTest {
    private Session session;

    @Before
    public void before(){
        session = HibernateUtil.getSession();
        session.beginTransaction();
    }


    @After
    public void after(){
        session.getTransaction().commit();
    }

    /**
     * 查询一个学生对应多个老师
     * 在默认情况下如果不使用老师这个对象默认是不会查询的，是懒加载模式
     */
    @Test
    public void findStudent(){
        Student student = (Student) session.get(Student.class,4);
        System.out.println("StudentName---》"+student.getStudentName());
//      懒加载模式
        Set<Teacher> teacherSet = student.getTeacherSet();
        for (Teacher teacher : teacherSet){
            System.out.println("id-->"+teacher.getId()+"TeacherName--》"+teacher.getTeacherName());
        }
    }


    /**
     * 给学生新添加老师在多对多关系中
     */
    @Test
    public void newStudentTeacher(){
        Teacher teacher = new Teacher();
        teacher.setTeacherName("t4");
        session.save(teacher);

        Student student = (Student) session.get(Student.class,1);


//        如果这个Set集合是new出来的不是查询出来的那么就会把以前
//        学生对应的老师删除，再次插入新的对应关系
        /*Set<Teacher> teacherSet = new HashSet<Teacher>();
        teacherSet.add(teacher);*/

//      查询出原来对应的老师再添加上新增的老师
        Set<Teacher> teacherSet = student.getTeacherSet();
        teacherSet.add(teacher);

        student.setTeacherSet(teacherSet);
    }

    /**
     * 保存多个学生的对应关系，值需要一个表维护其中的关系即可，先存不维护关系的对象，
     * 在存维护关系的对象
     */
    @Test
    public void saveStudentTeacherMany(){
        Student student = new Student();
        student.setStudentName("s5");
        Student student1 = new Student();
        student1.setStudentName("s6");

        Teacher teacher = new Teacher();
        teacher.setTeacherName("t5");
        Teacher teacher1 = new Teacher();
        teacher1.setTeacherName("t6");

        Set<Teacher> teacherSet = new HashSet<Teacher>();
        teacherSet.add(teacher);
        teacherSet.add(teacher1);

        student.setTeacherSet(teacherSet);
        student1.setTeacherSet(teacherSet);

        session.save(teacher);
        session.save(teacher1);
        session.save(student);
        session.save(student1);
    }


}
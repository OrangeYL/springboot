package com.orange.exercise.stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Li ZhiCheng
 * @create: 2022-11-2022/11/22 18:44
 * @description:
 */
public class demo {

    public static void main(String[] args) {
        List<Student> students = getList();
        //filter:过滤，过滤分数大于60
        List<Student> list = students.stream().filter(
                student -> student.getScore() >= 60).collect(Collectors.toList());
        list.forEach(System.out::println);
        System.out.println("=========================================");
        //map:映射，将流转换成新的类型的流
        List<String> strings = students.stream().map(Student::getName).collect(Collectors.toList());
        strings.forEach(System.out::println);
        System.out.println("=========================================");
        //anyMatch()，只要有一个元素匹配传入的条件，就返回 true。
        //allMatch()，只有有一个元素不匹配传入的条件，就返回 false；如果全部匹配，则返回 true。
        //noneMatch()，只要有一个元素匹配传入的条件，就返回 false；如果全部不匹配，则返回 true。
        boolean anyMatchFlag = students.stream().anyMatch(student -> student.getName().contains("李"));
        boolean allMatchFlag = students.stream().allMatch(student -> student.getName().length() > 1);
        boolean noneMatchFlag = students.stream().noneMatch(student -> student.getScore() == 100);
        System.out.println(anyMatchFlag);
        System.out.println(allMatchFlag);
        System.out.println(noneMatchFlag);
        System.out.println("=========================================");
        for(Student student : students){
            List<Student> studentList = students.stream().filter(
                    e -> e.getName().equals(student.getName())).collect(Collectors.toList());
            studentList.forEach(System.out::println);
            System.out.println("=========================================");
        }


    }

    public static List<Student>  getList(){
        ArrayList<Student> students = new ArrayList<>();
        students.add(new Student("张三", 60));
        students.add(new Student("张三", 80));
        students.add(new Student("王五", 50));
        students.add(new Student("赵六", 70));
        students.add(new Student("孙七", 90));
        students.add(new Student("周八", 30));
        return students;
    }
}
@Data
@AllArgsConstructor
@NoArgsConstructor
class Student implements Serializable {
    private String name;

    private double score;
}

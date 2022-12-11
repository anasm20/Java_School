package main;

import models.Person;
import models.School;
import models.Student;
import models.Teacher;
import services.SchoolServices;
import tools.Actions;

import java.util.Scanner;

public class Hmi {
    private static SchoolServices schoolServices;
    public static void start(){
        Scanner scanner = new Scanner(System.in);
        School school =  Hmi.createSchool();

        System.out.println("Welcome to " + school.getName() + " in " + school.getAddress());
        String action;

        do {
            System.out.println("What do you want to do ?");
            action = scanner.next();

            switch (action){
                case "addStudent":
                    Hmi.addStudent();
                    break;
                case "addTeacher":
                    Hmi.addTeacher();
                    break;
                case "printAllStudents":
                    Hmi.printAllStudents();
                    break;
                case "printAllTeachers":
                    Hmi.printAllTeachers();
                    break;
                case "findStudentByName":
                    Hmi.printDataPersonByName(Actions.STUDENT);
                    break;
                case "findTeacherByName":
                    Hmi.printDataPersonByName(Actions.TEACHER);
                    break;
                case "removeStudent":
                    Hmi.removePerson(Actions.STUDENT);
                    break;
                case "removeTeacher":
                    Hmi.removePerson(Actions.TEACHER);
                    break;
            }
        } while (!action.equals("exit"));
    }

    private static School createSchool(){
        School school = new School();
        school.setName("Coding");
        school.setAddress("Vienna");
        school.setPhoneNumber("0403020506");
        Hmi.schoolServices = new SchoolServices(school);
        return school;
    }

    private static void addStudent(){
        Student student = new Student();
        Hmi.setDataPerson(student);
        Hmi.schoolServices.addStudent(student);
    }

    private static void addTeacher(){
        Teacher teacher = new Teacher();
        Hmi.setDataPerson(teacher);
        Hmi.schoolServices.addTeacher(teacher);
    }

    public static void printAllStudents() {
        for (Student student: Hmi.schoolServices.getSchool().getStudents()) {
            System.out.println(student.getData());
        }
    }

    public static void printAllTeachers() {
        for (Teacher teacher: Hmi.schoolServices.getSchool().getTeachers()) {
            System.out.println(teacher.getData());
        }
    }

    private static void setDataPerson(Person p) {
        Scanner reader = new Scanner(System.in);

        System.out.println("Plz enter the name");
        p.setName(reader.next());

        System.out.println("Plz enter the age");
        p.setAge(reader.nextInt());

        if (p instanceof Teacher){
            System.out.println("Plz enter the salary");
            ((Teacher) p).setSalary(reader.nextFloat());
        } else if(p instanceof Student) {
            System.out.println("Plz enter the phone number");
            ((Student) p).setParentPhoneNumber(reader.next());
        }
    }

    private static void printDataPersonByName(Actions actions){
        Person p = Hmi.findPersonByName(actions);
        Hmi.printDataPerson(p);
    }

    private static void printDataPerson(Person person){
        if(person == null){
            System.out.println("Not found!!");
        } else {
            System.out.println(person.getData());
        }

    }

    private static Person findPersonByName(Actions action) {
        Scanner reader = new Scanner(System.in);
        System.out.println("Plz enter the name");
        String name = reader.next();
        Person p ;

        if (action == Actions.STUDENT){
            p = Hmi.schoolServices.getStudentByName(name);
        }else {
            p = Hmi.schoolServices.getTeacherByName(name);
        }
        return p;
    }

    private static void removePerson(Actions actions){
        Person p = Hmi.findPersonByName(actions);

        if (p instanceof Student) {
            Hmi.schoolServices.removeStudent((Student) p);
        } else {
            Hmi.schoolServices.removeTeacher((Teacher) p);
        }
    }


}

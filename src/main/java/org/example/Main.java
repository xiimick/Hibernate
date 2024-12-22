package org.example;

import org.example.Homework;
import org.example.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hillel-persistence-unit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();


        try {

            createStudent(entityManager);


            Student student = findStudentById(entityManager, 1L);
            if (student != null) {
                System.out.println("Знайдений студент: " + student);
            }


            addHomeworkToStudent(entityManager, student);


            updateStudentFirstName(entityManager, student, "Jane");


            deleteStudent(entityManager, student);
        } finally {

            if (entityManager != null) {
                entityManager.close();
            }
            if (entityManagerFactory != null) {
                entityManagerFactory.close();
            }
        }
    }


    private static void createStudent(EntityManager entityManager) {
        Student student = new Student();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setEmail("john.doe@example.com");

        entityManager.getTransaction().begin();
        entityManager.persist(student);
        entityManager.getTransaction().commit();
        System.out.println("Студента створено: " + student);
    }


    private static Student findStudentById(EntityManager entityManager, Long id) {
        return entityManager.find(Student.class, id);
    }


    private static void addHomeworkToStudent(EntityManager entityManager, Student student) {
        if (student == null) {
            System.out.println("Студента не знайдено, не можна додати домашнє завдання");
            return;
        }

        Homework homework = new Homework();
        homework.setDescription("Math Homework");
        homework.setDeadline(LocalDate.of(2024, 9, 30));
        homework.setMark(95);

        entityManager.getTransaction().begin();
        student.addHomework(homework);
        entityManager.merge(student);
        entityManager.getTransaction().commit();
        System.out.println("Домашнє завдання додано студенту: " + student);
    }


    private static void updateStudentFirstName(EntityManager entityManager, Student student, String newFirstName) {
        if (student == null) {
            System.out.println("Студента не знайдено, не можна оновити ім'я");
            return;
        }

        entityManager.getTransaction().begin();
        student.setFirstName(newFirstName);
        entityManager.merge(student);
        entityManager.getTransaction().commit();
        System.out.println("Ім'я студента оновлено на: " + newFirstName);
    }


    private static void deleteStudent(EntityManager entityManager, Student student) {
        if (student == null) {
            System.out.println("Студента не знайдено, не можна видалити");
            return;
        }

        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(Student.class, student.getId()));
        entityManager.getTransaction().commit();
        System.out.println("Студента видалено: " + student);
    }
}
import java.util.ArrayList;
import java.util.Scanner;

public class StudentGradeTracker {

    // Student data ko store karne ke liye simple class
    static class Student {
        String name;
        double grade;

        public Student(String name, double grade) {
            this.name = name;
            this.grade = grade;
        }
    }

    public static void main(String[] args) {
        ArrayList<Student> students = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=========================================");
        System.out.println("   Welcome to Student Grade Tracker App   ");
        System.out.println("=========================================");

        while (true) {
            System.out.print("\nEnter student name (or type 'exit' to finish): ");
            String name = scanner.nextLine().trim();

            if (name.equalsIgnoreCase("exit")) {
                break;
            }

            if (name.isEmpty()) {
                System.out.println("Name cannot be empty. Please try again.");
                continue;
            }

            double grade = -1;
            while (true) {
                System.out.print("Enter grade for " + name + " (0 - 100): ");
                if (scanner.hasNextDouble()) {
                    grade = scanner.nextDouble();
                    if (grade >= 0 && grade <= 100) {
                        break;
                    } else {
                        System.out.println("Invalid input! Grade must be between 0 and 100.");
                    }
                } else {
                    System.out.println("Invalid input! Please enter a valid number.");
                    scanner.next(); // Clear invalid token
                }
            }
            scanner.nextLine(); // Consume newline character

            // List me student add karna
            students.add(new Student(name, grade));
        }

        // Agar koi data enter nahi kiya gaya
        if (students.isEmpty()) {
            System.out.println("\nNo student data entered. Exiting program.");
            scanner.close();
            return;
        }

        // Calculations
        double total = 0;
        double highest = students.get(0).grade;
        double lowest = students.get(0).grade;

        for (Student s : students) {
            total += s.grade;
            if (s.grade > highest) {
                highest = s.grade;
            }
            if (s.grade < lowest) {
                lowest = s.grade;
            }
        }

        double average = total / students.size();

        // Summary Report Display karna
        System.out.println("\n=========================================");
        System.out.println("             SUMMARY REPORT              ");
        System.out.println("=========================================");
        System.out.printf("%-20s | %-10s\n", "Student Name", "Grade");
        System.out.println("-----------------------------------------");
        
        for (Student s : students) {
            System.out.printf("%-20s | %-10.2f\n", s.name, s.grade);
        }
        
        System.out.println("-----------------------------------------");
        System.out.printf("Total Students : %d\n", students.size());
        System.out.printf("Average Score  : %.2f\n", average);
        System.out.printf("Highest Score  : %.2f\n", highest);
        System.out.printf("Lowest Score   : %.2f\n", lowest);
        System.out.println("=========================================");

        scanner.close();
    }
}

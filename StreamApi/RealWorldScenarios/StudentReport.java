package StreamApi.RealWorldScenarios;

import java.util.Arrays;
import java.util.List;

class Student {

    private String studentName;
    private List<Integer> subjectMarks;

    public Student(String studentName, List<Integer> subjectMarks) {
        this.studentName = studentName;
        this.subjectMarks = subjectMarks;
    }

    public String getStudentName() {
        return studentName;
    }

    public List<Integer> getSubjectMarks() {
        return subjectMarks;
    }

    public int getTotalMarks() {
        return subjectMarks.stream()
                .mapToInt(mark -> mark)
                .sum();
    }

    public double getPercentage() {
        return getTotalMarks() / 5.0;
    }
}

public class StudentReport {

    public static void main(String[] args) {

        List<Student> students = Arrays.asList(
                new Student(
                        "Avinash",
                        List.of(80, 75, 90, 85, 70)
                ),

                new Student(
                        "Rahul",
                        List.of(90, 88, 92, 85, 95)
                ),

                new Student(
                        "Neha",
                        List.of(75, 70, 80, 78, 72)
                ),

                new Student(
                        "Priya",
                        List.of(85, 82, 88, 90, 84)
                )
        );

        List<Student> rankedStudents = students.stream()
                .sorted((firstStudent, secondStudent) ->
                        Integer.compare(
                                secondStudent.getTotalMarks(),
                                firstStudent.getTotalMarks()
                        )
                )
                .toList();

        for (int index = 0; index < rankedStudents.size(); index++) {

            Student student = rankedStudents.get(index);
            int rank = index + 1;

            System.out.println("Rank: " + rank);
            System.out.println("Name: " + student.getStudentName());
            System.out.println("Marks: " + student.getSubjectMarks());
            System.out.println("Total: " + student.getTotalMarks());
            System.out.println("Percentage: " + student.getPercentage() + "%");
            System.out.println("-----------------------");
        }
    }
}
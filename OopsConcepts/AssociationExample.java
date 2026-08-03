package OopsConcepts;

class Teacher {

    private String teacherName;

    public Teacher(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherName() {
        return teacherName;
    }
}

class Student {

    private String studentName;

    public Student(String studentName) {
        this.studentName = studentName;
    }

    public void learnFrom(Teacher teacher) {

        System.out.println(
                studentName + " learns from " + teacher.getTeacherName()
        );
    }
}

public class AssociationExample {

    public static void main(String[] args) {

        Teacher teacher = new Teacher("Hari");
        Student student = new Student("Avinash");

        student.learnFrom(teacher);
    }
}
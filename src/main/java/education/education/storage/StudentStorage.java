package education.education.storage;


import education.education.model.Student;

public class StudentStorage {

    private Student[] students = new Student[10];
    private int size;

    public void add(Student student) {

        if (students.length == size) {
            extend();
        }
        students[size++] = student;
    }

    private void extend() {
        Student[] array = new Student[students.length + 10];
        System.arraycopy(students, 0, array, 0, students.length);
        students = array;
    }

    public Student getByIndex(int index) {
        if (index < 0 || index > students.length) {
            System.err.println("invalid index");
            return null;
        }
        return students[index];
    }

    public void print() {

        for (int i = 0; i < size; i++) {
            System.out.println(students[i]);
        }
    }

    private void delete(int index) {
        for (int i = index + 1; i < size; i++) {
            students[i - 1] = students[i];
        }
    }

    public Student getByEmail(String email) {
        for (int i = 0; i < size; i++) {
            if (students[i].getEmail().equals(email)) {
                return students[i];
            }
        }
        return null;
    }


    public void deleteStudentByEmail(Student student) {
        for (int i = 0; i < size; i++) {
            if (students[i].equals(student)) {
                delete(i);
            }
        }

    }
}

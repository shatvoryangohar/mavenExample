package education.education;

import education.education.exception.UserNotFoundException;
import education.education.model.Lesson;
import education.education.model.Student;
import education.education.model.User;
import education.education.storage.LessonStorage;
import education.education.storage.StudentStorage;
import education.education.storage.UserStorage;
import education.education.util.DateUtil;

import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

public class StudentLessonTest implements StudentLessonCommands {
    static Scanner scanner = new Scanner(System.in);
    static LessonStorage lessonStorage = new LessonStorage();
    static StudentStorage studentStorage = new StudentStorage();
    static UserStorage userStorage = new UserStorage();

    public static void main(String[] args)  {



        boolean isRun = true;
        while (isRun) {
            StudentLessonCommands.printCommands();
            String command = scanner.nextLine();
            switch (command) {
                case EXIT:
                    isRun = false;
                case LOGIN:
                    login();
                    break;
                case REGISTER:
                    register();
                    break;
                default:
                    System.out.println("Invalid command");
            }

        }

    }

    private static void userLogin() {
        boolean isFound = true;
        while (isFound) {
            StudentLessonCommands.printUserCommands();
            String command = scanner.nextLine();
            switch (command) {
                case EXIT:
                    isFound = false;
                    break;
                case ADD_LESSON:
                    addLesson();
                    break;
                case ADD_STUDENT:
                    addStudent();
                    break;
                case PRINT_STUDENTS:
                    studentStorage.print();
                    break;
                case PRINT_LESSONS:
                    lessonStorage.print();
                    break;
                case PRINT_STUDENTS_BY_LESSON:
                    printStudentsByLesson();
                    break;
            }
        }
    }

    private static void adminLogin() {
        boolean isFound = true;
        while (isFound) {
            StudentLessonCommands.printAdminCommands();
            String command = scanner.nextLine();
            switch (command) {
                case EXIT:
                    isFound = false;
                    break;
                case ADD_LESSON:
                    addLesson();
                    break;
                case ADD_STUDENT:
                    addStudent();
                    break;
                case PRINT_STUDENTS:
                    studentStorage.print();
                    break;
                case PRINT_LESSONS:
                    lessonStorage.print();
                    break;
                case PRINT_STUDENTS_BY_LESSON:
                    printStudentsByLesson();
                    break;
                case DELETE_LESSONS_BY_NAME:
                    deleteLessonsByName();
                    break;
                case DELETE_STUDENT_BY_EMAIL:
                    deleteStudentByEmail();
                    break;
                default:
                    System.out.println("Invalid command");
            }
        }


    }

    private static void register() {
        System.out.println("Please input email");
        String email = scanner.nextLine();
        try {
            userStorage.getByEmail(email);
            System.out.println("Please input name");
            String name = scanner.nextLine();
            System.out.println("Please input surname");
            String surname = scanner.nextLine();
            System.out.println("Please input password");
            String password = scanner.nextLine();
            System.out.println("Please input type(ADMIN/USER");
            String type = scanner.nextLine();
            if (type.equalsIgnoreCase("admin") || type.equalsIgnoreCase("user")) {
                User user = new User();
                user.setName(name);
                user.setSurname(surname);
                user.setEmail(email);
                user.setPassword(password);
                user.setType(type.toUpperCase());
                userStorage.add(type, user);
                System.out.println("Thank you,user was registered ");
            } else {
                System.out.println("Invalid type");
            }


        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
    }
        private static void login() {
        System.out.println("Please input email");
        String email = scanner.nextLine();

        try {
            User byEmail = userStorage.getByEmail(email);
            System.out.println("Please input password");
            String password = scanner.nextLine();
            if (byEmail.getPassword().equals(password)) {
                if (byEmail.getType().equalsIgnoreCase("Admin")) {
                    adminLogin();
                } else if (byEmail.getType().equalsIgnoreCase("User")) {
                    userLogin();
                } else {
                    System.out.println("Password is wrong");
                }
            }
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }


    private static void deleteStudentByEmail() {
        System.out.println("please choose student's email");
        studentStorage.print();
        String email = scanner.nextLine();
        Student student = studentStorage.getByEmail(email);
        if (student != null) {
            studentStorage.deleteStudentByEmail(student);
        } else {
            System.out.println("Students does not exists");
        }
    }

    private static void deleteLessonsByName() {
        System.out.println("please choose lesson's name ");
        lessonStorage.print();
        String name = scanner.nextLine();
        Lesson lesson = lessonStorage.getByName(name);
        if (lesson != null) {
            lessonStorage.deleteLessonsByName(lesson);
        } else {
            System.out.println("lesson does not exists");
        }

    }

    private static void printStudentsByLesson() {
        System.out.println("please choose lesson's name ");
        lessonStorage.print();
        String lessonName = scanner.nextLine();
        Lesson lesson = lessonStorage.getByName(lessonName);
        if (lessonStorage.getByName(lesson.getName()) != null) {
            studentStorage.print();
        } else {
            System.out.println("Invalid name!");
        }
    }

    private static void addStudent() {

        System.out.println("please input student's name,surname,age,email,phone,lesson name,dateOfBirth");
        String studentData = scanner.nextLine();
        String[] studentDataStr = studentData.split(",");
        if (studentDataStr.length == 7) {
            int age = Integer.parseInt(scanner.nextLine());
            String lessonNames = scanner.nextLine();
            Date data = null;
            try {
                data = DateUtil.stringToDate(studentDataStr[6]);
                String[] namesSplitted = lessonNames.split(",");
                Lesson[] lessons = new Lesson[namesSplitted.length];
                for (int i = 0; i < namesSplitted.length; i++) {
                    Lesson lesson = lessonStorage.getByName(namesSplitted[i]);
                    if (lesson != null) {
                        lessons[i] = lesson;

                    }
                    Student student = new Student(studentDataStr[0], studentDataStr[1], age, studentDataStr[3], studentDataStr[4], lessons, data);
                    if (studentStorage.getByEmail(student.getEmail()) != null) {
                        System.out.println("Invalid email!Student with email already exists");

                    }
                    studentStorage.add(student);
                }
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    private static void addLesson() {
        System.out.println("please input lesson's name");
        String name = scanner.nextLine();
        System.out.println("please input lesson's duration");
        double duration = Double.parseDouble(scanner.nextLine());
        System.out.println("please input lesson's lecturerName");
        String lecturedName = scanner.nextLine();
        System.out.println("please input lesson's price");
        double price = Double.parseDouble(scanner.nextLine());
        Lesson lesson = new Lesson(name, duration, lecturedName, price);
        if (lessonStorage.getByName(lesson.getName()) != null) {
            System.out.println("Invalid name!lesson with name already exists ");
        } else {
            addLesson();
            System.out.println("Thanks!Lessons was added");
        }

    }

}



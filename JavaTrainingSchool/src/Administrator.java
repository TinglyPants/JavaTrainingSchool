import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Administrator {

    public static void main(String[] args) {
        // Parsing Arguments
        if (args.length < 2) {
            System.out.println("Please provide a sufficient number of arguments!");
            return;
        }

        String configurationFileName = args[0];
        int days;
        try {
            days = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid format for days: " + args[1]);
            return;
        }

        School school;
        try {
            school = buildSchoolFromConfigurationFile(configurationFileName);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return;
        }

        // Run administrator
        Administrator administrator = new Administrator(school);
        administrator.run(days);
    }

    private static School buildSchoolFromConfigurationFile(String configurationFileName) {
        // Reading configuration file
        ArrayList<String> lines;
        lines = parseConfigurationFile(configurationFileName);

        // Load School
        School school;
        school = loadSchoolFromConfiguration(lines);

        // Load Subjects into school
        for (Subject subject : loadSubjectsFromConfiguration(lines)) {
            school.add(subject);
        }

        // Load Students into school
        for (Student student : loadStudentsFromConfiguration(lines)) {
            school.add(student);
        }


        // Load Instructors into school
        for (Instructor instructor : loadInstructorsFromConfiguration(lines)) {
            school.add(instructor);
        }

        return school;
    }

    private static ArrayList<String> parseConfigurationFile(String configurationFileName) {
        ArrayList<String> lines = new ArrayList<>();

        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(configurationFileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Could not load configuration file: " + configurationFileName);
        }

        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.isBlank()) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading from configuration file: " + configurationFileName);
        }

        return lines;
    }

    private static School loadSchoolFromConfiguration(ArrayList<String> configurationFileLines) {
        try {
            for (String line : configurationFileLines) {
                String[] parts = line.split(":");
                if (parts[0].equals("school")) {
                    return new School(parts[1]);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Invalid formatting detected in configuration file!");
        }

        throw new RuntimeException("Configuration file missing school!");
    }

    private static ArrayList<Subject> loadSubjectsFromConfiguration(ArrayList<String> configurationFileLines) {
        ArrayList<Subject> subjects = new ArrayList<>();
        try {
            for (String line : configurationFileLines) {
                String[] parts = line.split(":");
                if (parts[0].equals("subject")) {
                    String[] args = parts[1].split(",");
                    String description = args[0];
                    int subjectID = Integer.parseInt(args[1]);
                    int specialismID = Integer.parseInt(args[2]);
                    int duration = Integer.parseInt(args[3]);

                    subjects.add(new Subject(subjectID, specialismID, duration, description));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Invalid formatting detected in configuration file!");
        }

        return subjects;
    }

    private static ArrayList<Student> loadStudentsFromConfiguration(ArrayList<String> configurationFileLines) {
        ArrayList<Student> students = new ArrayList<>();
        try {
            for (String line : configurationFileLines) {
                String[] parts = line.split(":");
                if (parts[0].equals("student")) {
                    String[] args = parts[1].split(",");
                    String name = args[0];
                    char gender = args[1].charAt(0);
                    int age = Integer.parseInt(args[2]);

                    students.add(new Student(name, gender, age));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Invalid formatting detected in configuration file!");
        }

        return students;
    }

    private static ArrayList<Instructor> loadInstructorsFromConfiguration(ArrayList<String> configurationFileLines) {
        ArrayList<Instructor> instructors = new ArrayList<>();
        try {
            for (String line : configurationFileLines) {
                String[] parts = line.split(":");
                if (parts[0].equals("Teacher")) {
                    String[] args = parts[1].split(",");
                    String name = args[0];
                    char gender = args[1].charAt(0);
                    int age = Integer.parseInt(args[2]);

                    instructors.add(new Teacher(name, gender, age));
                } else if (parts[0].equals("Demonstrator")) {
                    String[] args = parts[1].split(",");
                    String name = args[0];
                    char gender = args[1].charAt(0);
                    int age = Integer.parseInt(args[2]);

                    instructors.add(new Demonstrator(name, gender, age));
                } else if (parts[0].equals("OOTrainer")) {
                    String[] args = parts[1].split(",");
                    String name = args[0];
                    char gender = args[1].charAt(0);
                    int age = Integer.parseInt(args[2]);

                    instructors.add(new OOTrainer(name, gender, age));
                } else if (parts[0].equals("GUITrainer")) {
                    String[] args = parts[1].split(",");
                    String name = args[0];
                    char gender = args[1].charAt(0);
                    int age = Integer.parseInt(args[2]);

                    instructors.add(new GUITrainer(name, gender, age));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Invalid formatting detected in configuration file!");
        }

        return instructors;
    }

    private School school;

    public Administrator(School school) {
        this.school = school;
    }

    public void run() {
        while (true) {
            runOnce();
        }
    }

    public void run(int days) {
        for (int i = 0; i < days; i++) {
            runOnce();
            this.reportCourses();
            this.reportStudents();
            this.reportInstructors();
        }
    }

    private void runOnce() {
        Random random = new Random();
        // Admit 0-2 students
        int numStudents = random.nextInt(2);
        for (int i = 0; i < numStudents; i++) {
            this.school.add(new Student(createRandomName(), createRandomGender(), createRandomAge()));
        }

        // Admit instructors based on probabilities
        int TEACHER_PROBABILITY_PERCENT = 20;
        int DEMONSTRATOR_PROBABILITY_PERCENT = 10;
        int OOTRAINER_PROBABILITY_PERCENT = 5;
        int GUITRAINER_PROBABILITY_PERCENT = 5;
        if (random.nextInt(100) < TEACHER_PROBABILITY_PERCENT) {
            this.school.add(new Teacher(createRandomName(), createRandomGender(), createRandomAge()));
        }
        if (random.nextInt(100) < DEMONSTRATOR_PROBABILITY_PERCENT) {
            this.school.add(new Demonstrator(createRandomName(), createRandomGender(), createRandomAge()));
        }
        if (random.nextInt(100) < OOTRAINER_PROBABILITY_PERCENT) {
            this.school.add(new OOTrainer(createRandomName(), createRandomGender(), createRandomAge()));
        }
        if (random.nextInt(100) < GUITRAINER_PROBABILITY_PERCENT) {
            this.school.add(new GUITrainer(createRandomName(), createRandomGender(), createRandomAge()));
        }

        // Run a day at the school
        this.school.aDayAtSchool();

        // Release instructors with no assigned courses
        Iterator<Instructor> instructorIterator = this.school.getInstructors().iterator();
        while (instructorIterator.hasNext()) {
            Instructor next = instructorIterator.next();
            if (next.getAssignedCourse() == null) {
                instructorIterator.remove();
            }
        }

        // Release students who have graduated all available subjects
        Iterator<Student> studentIterator = this.school.getStudents().iterator();
        while (studentIterator.hasNext()) {
            Student next = studentIterator.next();
            if (this.studentHasCompletedAllCourses(next)){
                studentIterator.remove();
            }
        }

        // Release free students based on probability
        int STUDENT_LEAVE_PROBABILITY_PERCENT = 5;
        studentIterator = this.school.getStudents().iterator();
        while (studentIterator.hasNext()) {
            Student next = studentIterator.next();
            if (this.studentHasNoCourses(next) && random.nextInt(100) < STUDENT_LEAVE_PROBABILITY_PERCENT) {
                studentIterator.remove();
            }
        }
    }

    private String createRandomName() {
        StringBuilder sb = new StringBuilder();
        char[] UPPER_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        char[] VOWELS = "aaaeeeiiiooouuuy".toCharArray();
        char[] CONSONANTS = "bcdfghjklmnpqrstvwxz".toCharArray();

        Random random = new Random();
        int length = random.nextInt(12);

        sb.append(UPPER_CHARS[random.nextInt(26)]);

        // 90% chance to switch from consonant to vowel (or other way around) for each character
        boolean isConsonant = true;
        for (int i = 0; i < length - 1 ; i++){
            if (random.nextInt(100) < 90) {
                isConsonant = !isConsonant;
            }

            if (isConsonant) {
                sb.append(CONSONANTS[random.nextInt(20)]);
            } else {
                sb.append(VOWELS[random.nextInt(16)]);
            }
        }

        return sb.toString();
    }

    private char createRandomGender() {
        char[] GENDERS = { 'M', 'F' };
        return GENDERS[new Random().nextInt(2)];
    }

    private int createRandomAge() {
        return new Random().nextInt(100);
    }

    private boolean studentHasCompletedAllCourses(Student student) {
        for (Subject subject : this.school.getSubjects()) {
            if (!student.hasCertificate(subject)){
                return false;
            }
        }
        return true;
    }

    private boolean studentHasNoCourses(Student student) {
        for (Course course: this.school.getCourses()) {
            if (course.getStudents().contains(student)) {
                return false;
            }
        }
        return true;
    }

    private void reportCourses() {
        System.out.println("Course Report:");
        for (Course course : this.school.getCourses()){
            System.out.println("  - Course { status = %d, students = [ ".formatted(course.getStatus()));
            for (Student student : course.getStudents()) {
                System.out.println("      - \"%s\"".formatted(student.getName()));
            }
            System.out.println("    ] }");
        }
    }

    private void reportStudents() {
        System.out.println("Student Report:");
        for (Student student : this.school.getStudents()){
            System.out.println("  - Student { name = %s, enrolled courses = [ ".formatted(student.getName()));
            for (Course course : this.school.getCourses()) {
                if (course.getStudents().contains(student)) {
                    System.out.println("      - %d".formatted(course.getSubject().getID()));
                }
            }
            System.out.println("    ], certificates = [ ");
            for (Integer certificate : student.getCertificates()) {
                System.out.println("      - %d".formatted(certificate));
            }
            System.out.println("    ] }");
        }
    }

    private void reportInstructors() {
        System.out.println("Instructor Report:");
        for (Instructor instructor : this.school.getInstructors()){
            if (instructor.getAssignedCourse() != null) {
                System.out.println("  - Instrutor { name = %s, assigned course subject ID = %d }".formatted(
                        instructor.getName(),
                        instructor.getAssignedCourse().getSubject().getID()));
            } else {
                System.out.println("  - Instrutor { name = %s }".formatted(instructor.getName()));
            }
        }
    }
}

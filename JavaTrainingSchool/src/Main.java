public class Main {
    public static void main(String[] args){
        School school = new School("School of Java");
        school.add(new Subject(1, 1, 4, "Subject 1"));
        school.add(new Subject(2, 2, 6, "Subject 2"));
        Administrator administrator = new Administrator(school);

        administrator.run(200);
    }
}

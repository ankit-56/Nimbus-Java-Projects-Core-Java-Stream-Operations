import java.util.*;
import java.util.stream.*;
import java.util.Comparator;

class Employee {
    String name;
    int age;
    double salary;

    Employee(String name, int age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    void display() {
        System.out.println("Name: " + name + ", Age: " + age + ", Salary: " + salary);
    }
}

class Student {
    String name;
    double marks;

    Student(String name, double marks) {
        this.name = name;
        this.marks = marks;
    }
}

class Product {
    String name;
    double price;
    String category;

    Product(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }
}

public class NimbusLambdaStreams_2_2{
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while(true) {
            System.out.println("\n--- Nimbus Lambda & Streams Menu ---");
            System.out.println("1. Sort Employee Objects Using Lambda");
            System.out.println("2. Filter and Sort Students Using Streams");
            System.out.println("3. Stream Operations on Product Dataset");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch(choice) {
                case 1: sortEmployees(); break;
                case 2: filterSortStudents(); break;
                case 3: productStreamOperations(); break;
                case 4: System.out.println("Exiting..."); sc.close(); System.exit(0);
                default: System.out.println("Invalid choice! Try again.");
            }
        }
    }

    static void sortEmployees() {
        List<Employee> employees = new ArrayList<>();
        System.out.print("Enter number of employees: ");
        int n = sc.nextInt();
        sc.nextLine();
        for(int i=0;i<n;i++) {
            System.out.print("Enter name: ");
            String name = sc.nextLine();
            System.out.print("Enter age: ");
            int age = sc.nextInt(); sc.nextLine();
            System.out.print("Enter salary: ");
            double salary = sc.nextDouble(); sc.nextLine();
            employees.add(new Employee(name, age, salary));
        }

        System.out.println("\nSort by name:");
        employees.sort((e1,e2) -> e1.name.compareToIgnoreCase(e2.name));
        employees.forEach(Employee::display);

        System.out.println("\nSort by age:");
        employees.sort(Comparator.comparingInt(e -> e.age));
        employees.forEach(Employee::display);

        System.out.println("\nSort by salary (descending):");
        employees.sort((e1,e2) -> Double.compare(e2.salary, e1.salary));
        employees.forEach(Employee::display);
    }

    static void filterSortStudents() {
        List<Student> students = new ArrayList<>();
        System.out.print("Enter number of students: ");
        int n = sc.nextInt(); sc.nextLine();
        for(int i=0;i<n;i++) {
            System.out.print("Enter name: ");
            String name = sc.nextLine();
            System.out.print("Enter marks: ");
            double marks = sc.nextDouble(); sc.nextLine();
            students.add(new Student(name, marks));
        }

        System.out.println("\nStudents with marks > 75%, sorted by marks:");
        students.stream()
                .filter(s -> s.marks > 75)
                .sorted(Comparator.comparingDouble(s -> s.marks))
                .map(s -> s.name)
                .forEach(System.out::println);
    }

    static void productStreamOperations() {
        List<Product> products = new ArrayList<>();
        System.out.print("Enter number of products: ");
        int n = sc.nextInt(); sc.nextLine();
        for(int i=0;i<n;i++) {
            System.out.print("Enter product name: ");
            String name = sc.nextLine();
            System.out.print("Enter price: ");
            double price = sc.nextDouble(); sc.nextLine();
            System.out.print("Enter category: ");
            String category = sc.nextLine();
            products.add(new Product(name, price, category));
        }

        System.out.println("\nProducts grouped by category:");
        Map<String, List<Product>> grouped = products.stream()
                .collect(Collectors.groupingBy(p -> p.category));
        grouped.forEach((cat,list) -> {
            System.out.println(cat + ":");
            list.forEach(p -> System.out.println("  " + p.name + " - " + p.price));
        });

        System.out.println("\nMost expensive product in each category:");
        Map<String, Optional<Product>> maxPricePerCategory = products.stream()
                .collect(Collectors.groupingBy(p -> p.category,
                        Collectors.maxBy(Comparator.comparingDouble(p -> p.price))));
        maxPricePerCategory.forEach((cat, prod) -> {
            prod.ifPresent(p -> System.out.println(cat + ": " + p.name + " - " + p.price));
        });

        double avgPrice = products.stream()
                .collect(Collectors.averagingDouble(p -> p.price));
        System.out.println("\nAverage price of all products: " + avgPrice);
    }
}

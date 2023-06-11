import java.sql.*;
import java.util.Scanner;

public class Main {

  public static String url = "jdbc:mysql://10.0.10.3:3306/mysqldb";
  public static String username = "wchuchro";
  public static String password = "pass123";

  public static void main(String[] args) {
    try (
      Connection connection = DriverManager.getConnection(
        url,
        username,
        password
      )
    ) {
        Scanner scanner = new Scanner(System.in);
        displayMenu(); 
        String choice = scanner.nextLine();

        while (!choice.equalsIgnoreCase("exit")) {
            switch (choice) {
              case "0":
                System.out.println("Connected to database!");
                createDatabase(connection);
                break;
              case "1":
                createRecord(connection, scanner);
                break;
              case "2":
                readRecords(connection);
                break;
              case "3":
                updateRecord(connection, scanner);
                break;
              case "4":
                deleteRecord(connection, scanner);
                break;
              default:
                System.out.println("Invalid choice. Please try again.");
                break;
            }
            displayMenu();
            choice = scanner.nextLine();
        }
        
    } catch (SQLException e) {
      System.out.println("Error connecting to database: " + e.getMessage());
    }
  }

  static void displayMenu() {
    System.out.println("----------------------------");
    System.out.println("0. Create a database (compulsory for the first time, do not use if database was created previously)");
    System.out.println("1. Create a record");
    System.out.println("2. Read records");
    System.out.println("3. Update a record");
    System.out.println("4. Delete a record");
    System.out.println("Type 'exit' to quit");
    System.out.print("Enter your choice: ");
  }

  private static void createDatabase(Connection connection) throws SQLException {
        String createTableQuery = "CREATE TABLE users (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "username VARCHAR(50)," +
                    "age INT," +
                    "adult BOOLEAN" +
                    ")";

        String defaultTable = "INSERT INTO users (username, age, adult) VALUES " +
                    "('John Doe', 25, true), " +
                    "('Jane Smith', 30, true), " +
                    "('Mark Johnson', 18, false)";
 

        Statement statement = connection.createStatement();
        statement.executeUpdate(createTableQuery);
        statement.executeUpdate(defaultTable);

        System.out.println("Default database created.");
  }
  
  private static void createRecord(Connection connection, Scanner scanner) throws SQLException {
    System.out.print("Enter name: ");
    String name = scanner.nextLine();
    System.out.print("Enter age: ");
    String age = scanner.nextLine();
    System.out.print("Enter new: Is adult? [write number: true - 1 OR false - 0]: ");
    String adult = scanner.nextLine();

    String insertQuery ="INSERT INTO users (username, age, adult) VALUES " 
                    + "('" + name + "', '" + age + "', '" + adult + "')";

  try (Statement statement = connection.createStatement();) {
    statement.executeUpdate(insertQuery);
    System.out.println("Added user: " + name);
  } catch (SQLException e) {
    System.out.println(e.getMessage());
  }
  }

  private static void readRecords(Connection connection) throws SQLException {
    String selectQuery = "SELECT * FROM users";
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery(selectQuery);

    while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String usernameValue = resultSet.getString("username");
                int age = resultSet.getInt("age");
                boolean adult = resultSet.getBoolean("adult");

                System.out.printf("ID: %d, Username: %s, Age: %d, Adult: %b%n", id, usernameValue, age, adult);
            }

    resultSet.close();
  }

  private static void updateRecord(Connection connection, Scanner scanner) throws SQLException{
    System.out.print("Enter ID of the record to update: ");
    String id = scanner.nextLine();
    System.out.print("Enter new name: ");
    String name = scanner.nextLine();
    System.out.print("Enter new age: ");
    String age = scanner.nextLine();
    System.out.print("Enter new: Is adult? [write number: true - 1 OR false - 0]: ");
    String adult = scanner.nextLine();

    String updateQuery =
      "UPDATE users SET name = '" +
      name +
      "', age = '" +
      age +
      "', adult = '" +
      adult +
      "' WHERE id = " +
      id;

    Statement statement = connection.createStatement();
    statement.executeUpdate(updateQuery);
    System.out.println("Updated user with id: " + id);
  }

  private static void deleteRecord(Connection connection, Scanner scanner)
    throws SQLException {
    System.out.print("Enter ID of the record to delete: ");
    int id = Integer.parseInt(scanner.nextLine());

    String deleteQuery = "DELETE FROM users WHERE id = " + id;
    Statement statement = connection.createStatement();
    statement.executeUpdate(deleteQuery);
    
    System.out.println("Deleted user with id: " + id);
  }
}

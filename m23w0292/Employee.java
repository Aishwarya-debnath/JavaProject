package twentyfour.fall.oop.group1.finalproject.m23w0292;

public class Employee {
    private String name;
    private String role;
    private String employeeID; 
    
     
    public Employee(String name, String role, String employeeID) {
        this.name = name;
        this.role = role;
        this.employeeID = employeeID; 
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
    
    public String getEmployeeID() {
        return employeeID;
    }
    
    
}


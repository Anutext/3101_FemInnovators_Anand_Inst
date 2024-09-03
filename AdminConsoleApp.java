import java.util.*;

class AdminConsoleApp {
    private static Scanner scanner = new Scanner(System.in);
    private static Admin currentAdmin = new Admin();
    private static Map<String, User> users = new HashMap<>();
    private static Map<String, Assessment> assessments = new HashMap<>();
    private static List<LogEntry> auditLogs = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            showAdminLogin();
        }
    }

    private static void showAdminLogin() {
        System.out.println("=== Admin Login ===");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (authenticateAdmin(username, password)) {
            showAdminDashboard();
        } else {
            System.out.println("Invalid username or password.\n");
        }
    }

    private static boolean authenticateAdmin(String username, String password) {
        // Simple authentication check
        return username.equals("admin") && password.equals("adminpass");
    }

    private static void showAdminDashboard() {
        while (true) {
            System.out.println("\n=== Admin Dashboard ===");
            System.out.println("1. User Management");
            System.out.println("2. Assessment Management");
            System.out.println("3. Manual Question Upload");
            System.out.println("4. AI Proctoring Configuration");
            System.out.println("5. Reports and Analytics");
            System.out.println("6. System Settings");
            System.out.println("7. Audit Logs");
            System.out.println("8. Logout");
            System.out.print("Select an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    showUserManagementPage();
                    break;
                case "2":
                    showAssessmentManagementPage();
                    break;
                case "3":
                    showManualQuestionUploadPage();
                    break;
                case "4":
                    showAIProctoringConfigurationPage();
                    break;
                case "5":
                    showReportsAndAnalyticsPage();
                    break;
                case "6":
                    showSystemSettingsPage();
                    break;
                case "7":
                    showAuditLogsPage();
                    break;
                case "8":
                    System.out.println("Logged out successfully.");
                    return;
                default:
                    System.out.println("Invalid option.\n");
            }
        }
    }

    private static void showUserManagementPage() {
        System.out.println("\n=== User Management ===");
        if (users.isEmpty()) {
            System.out.println("No users available.\n");
            return;
        }

        for (Map.Entry<String, User> entry : users.entrySet()) {
            User user = entry.getValue();
            System.out.println("Username: " + user.getUsername() + ", Role: " + user.getRole() + ", Status: " + user.getStatus());
        }

        System.out.println("1. Edit User");
        System.out.println("2. Deactivate User");
        System.out.println("3. Delete User");
        System.out.println("4. Import/Export Users");
        System.out.print("Select an option: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                editUser();
                break;
            case "2":
                deactivateUser();
                break;
            case "3":
                deleteUser();
                break;
            case "4":
                importExportUsers();
                break;
            default:
                System.out.println("Invalid option.\n");
        }
    }

    private static void editUser() {
        System.out.print("Enter username to edit: ");
        String username = scanner.nextLine();
        User user = users.get(username);

        if (user != null) {
            System.out.print("Enter new role: ");
            String role = scanner.nextLine();
            user.setRole(role);
            System.out.println("User role updated successfully.\n");
        } else {
            System.out.println("User not found.\n");
        }
    }

    private static void deactivateUser() {
        System.out.print("Enter username to deactivate: ");
        String username = scanner.nextLine();
        User user = users.get(username);

        if (user != null) {
            user.setStatus("Deactivated");
            System.out.println("User deactivated successfully.\n");
        } else {
            System.out.println("User not found.\n");
        }
    }

    private static void deleteUser() {
        System.out.print("Enter username to delete: ");
        String username = scanner.nextLine();

        if (users.remove(username) != null) {
            System.out.println("User deleted successfully.\n");
        } else {
            System.out.println("User not found.\n");
        }
    }

    private static void importExportUsers() {
        System.out.println("Import/Export functionality is under development.");
    }

    private static void showAssessmentManagementPage() {
        System.out.println("\n=== Assessment Management ===");
        if (assessments.isEmpty()) {
            System.out.println("No assessments available.\n");
            return;
        }

        for (Map.Entry<String, Assessment> entry : assessments.entrySet()) {
            Assessment assessment = entry.getValue();
            System.out.println("Title: " + assessment.getTitle() + ", Status: " + assessment.getStatus());
        }

        System.out.println("1. Edit Assessment");
        System.out.println("2. Delete Assessment");
        System.out.println("3. Archive Assessment");
        System.out.print("Select an option: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                editAssessment();
                break;
            case "2":
                deleteAssessment();
                break;
            case "3":
                archiveAssessment();
                break;
            default:
                System.out.println("Invalid option.\n");
        }
    }

    private static void editAssessment() {
        System.out.print("Enter assessment title to edit: ");
        String title = scanner.nextLine();
        Assessment assessment = assessments.get(title);

        if (assessment != null) {
            System.out.print("Enter new status: ");
            String status = scanner.nextLine();
            assessment.setStatus(status);
            System.out.println("Assessment updated successfully.\n");
        } else {
            System.out.println("Assessment not found.\n");
        }
    }

    private static void deleteAssessment() {
        System.out.print("Enter assessment title to delete: ");
        String title = scanner.nextLine();

        if (assessments.remove(title) != null) {
            System.out.println("Assessment deleted successfully.\n");
        } else {
            System.out.println("Assessment not found.\n");
        }
    }

    private static void archiveAssessment() {
        System.out.print("Enter assessment title to archive: ");
        String title = scanner.nextLine();
        Assessment assessment = assessments.get(title);

        if (assessment != null) {
            assessment.setStatus("Archived");
            System.out.println("Assessment archived successfully.\n");
        } else {
            System.out.println("Assessment not found.\n");
        }
    }

    private static void showManualQuestionUploadPage() {
        System.out.println("\n=== Manual Question Upload ===");
        System.out.print("Enter question text: ");
        String questionText = scanner.nextLine();
        System.out.print("Enter correct answer: ");
        String correctAnswer = scanner.nextLine();
        // Handle options if required

        Question question = new Question(questionText, correctAnswer);
        System.out.println("Question added successfully.\n");
    }

    private static void showAIProctoringConfigurationPage() {
        System.out.println("\n=== AI Proctoring Configuration ===");
        System.out.println("1. Configure Webcam");
        System.out.println("2. Configure Microphone");
        System.out.println("3. Configure Screen Recording");
        System.out.println("4. Set Proctoring Rules");
        System.out.print("Select an option: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                configureWebcam();
                break;
            case "2":
                configureMicrophone();
                break;
            case "3":
                configureScreenRecording();
                break;
            case "4":
                setProctoringRules();
                break;
            default:
                System.out.println("Invalid option.\n");
        }
    }

    private static void configureWebcam() {
        System.out.println("Webcam configuration is under development.");
    }

    private static void configureMicrophone() {
        System.out.println("Microphone configuration is under development.");
    }

    private static void configureScreenRecording() {
        System.out.println("Screen recording configuration is under development.");
    }

    private static void setProctoringRules() {
        System.out.println("Proctoring rules configuration is under development.");
    }

    private static void showReportsAndAnalyticsPage() {
        System.out.println("\n=== Reports and Analytics ===");
        System.out.println("1. View System Activity");
        System.out.println("2. View Assessment Reports");
        System.out.println("3. View Performance Metrics");
        System.out.print("Select an option: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                viewSystemActivity();
                break;
            case "2":
                viewAssessmentReports();
                break;
            case "3":
                viewPerformanceMetrics();
                break;
            default:
                System.out.println("Invalid option.\n");
        }
    }

    private static void viewSystemActivity() {
        System.out.println("System activity reports are under development.");
    }

    private static void viewAssessmentReports() {
        System.out.println("Assessment reports are under development.");
    }

    private static void viewPerformanceMetrics() {
        System.out.println("Performance metrics are under development.");
    }

    private static void showSystemSettingsPage() {
        System.out.println("\n=== System Settings ===");
        System.out.println("1. Configure Branding");
        System.out.println("2. Configure Email Templates");
        System.out.println("3. Configure Notifications");
        System.out.println("4. Manage User Roles and Permissions");
        System.out.println("5. Manage Integrations and API Settings");
        System.out.print("Select an option: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                configureBranding();
                break;
            case "2":
                configureEmailTemplates();
                break;
            case "3":
                configureNotifications();
                break;
            case "4":
                manageUserRoles();
                break;
            case "5":
                manageIntegrations();
                break;
            default:
                System.out.println("Invalid option.\n");
        }
    }

    private static void configureBranding() {
        System.out.println("Branding configuration is under development.");
    }

    private static void configureEmailTemplates() {
        System.out.println("Email templates configuration is under development.");
    }

    private static void configureNotifications() {
        System.out.println("Notifications configuration is under development.");
    }

    private static void manageUserRoles() {
        System.out.println("User roles management is under development.");
    }

    private static void manageIntegrations() {
        System.out.println("Integrations and API settings management is under development.");
    }

    private static void showAuditLogsPage() {
        System.out.println("\n=== Audit Logs ===");
        if (auditLogs.isEmpty()) {
            System.out.println("No audit logs available.\n");
            return;
        }

        for (LogEntry log : auditLogs) {
            System.out.println("Timestamp: " + log.getTimestamp() + ", User: " + log.getUser() + ", Action: " + log.getAction());
        }

        System.out.println("1. Filter Logs");
        System.out.print("Select an option: ");
        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            filterLogs();
        }
    }

    private static void filterLogs() {
        System.out.println("Log filtering is under development.");
    }
}

class Admin {
    private String username = "admin";
    private String password = "adminpass";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

class User {
    private String username;
    private String role;
    private String status;

    public User(String username, String role) {
        this.username = username;
        this.role = role;
        this.status = "Active";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

class Assessment {
    private String title;
    private String status;

    public Assessment(String title, String status) {
        this.title = title;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

class Question {
    private String questionText;
    private String correctAnswer;

    public Question(String questionText, String correctAnswer) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}

class LogEntry {
    private String timestamp;
    private String user;
    private String action;

    public LogEntry(String timestamp, String user, String action) {
        this.timestamp = timestamp;
        this.user = user;
        this.action = action;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getUser() {
        return user;
    }

    public String getAction() {
        return action;
    }
}

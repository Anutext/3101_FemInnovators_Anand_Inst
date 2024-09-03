import java.util.*;

class EducatorAssessmentPlatform {
    private static Scanner scanner = new Scanner(System.in);
    private static Map<String, List<Question>> assessments = new HashMap<>();
    private static User currentUser = new User();

    public static void main(String[] args) {
        while (true) {
            showMainMenu();
        }
    }

    private static void showMainMenu() {
        System.out.println("=== Main Menu ===");
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.print("Select an option: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                login();
                break;
            case "2":
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option.\n");
        }
    }

    private static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (authenticate(username, password)) {
            showDashboard();
        } else {
            System.out.println("Invalid username or password.\n");
        }
    }

    private static boolean authenticate(String username, String password) {
        // Simplified authentication for demonstration
        return username.equals("admin") && password.equals("password");
    }

    private static void showDashboard() {
        while (true) {
            System.out.println("\n=== Dashboard ===");
            System.out.println("1. Create Assessment");
            System.out.println("2. Manage Assessments");
            System.out.println("3. View Reports and Analytics");
            System.out.println("4. Settings");
            System.out.println("5. Logout");
            System.out.print("Select an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    createAssessment();
                    break;
                case "2":
                    showManageAssessments();
                    break;
                case "3":
                    viewReportsAndAnalytics();
                    break;
                case "4":
                    showSettings();
                    break;
                case "5":
                    System.out.println("Logged out successfully.");
                    return;
                default:
                    System.out.println("Invalid option.\n");
            }
        }
    }

    private static void createAssessment() {
        System.out.print("Enter assessment title: ");
        String title = scanner.nextLine();
        System.out.print("Enter assessment description: ");
        String description = scanner.nextLine();

        List<Question> questions = new ArrayList<>();
        boolean addMoreQuestions = true;

        while (addMoreQuestions) {
            System.out.print("Enter question: ");
            String questionText = scanner.nextLine();
            System.out.print("Enter correct answer: ");
            String correctAnswer = scanner.nextLine();

            Question question = new Question(questionText, correctAnswer);
            questions.add(question);

            System.out.print("Add another question? (yes/no): ");
            addMoreQuestions = scanner.nextLine().equalsIgnoreCase("yes");
        }

        assessments.put(title, questions);
        System.out.println("Assessment created successfully!\n");
    }

    private static void showManageAssessments() {
        System.out.println("\n--- Manage Assessments ---");
        List<String> assessmentsList = new ArrayList<>(assessments.keySet());

        if (assessmentsList.isEmpty()) {
            System.out.println("No assessments available to manage.\n");
            return;
        }

        for (int i = 0; i < assessmentsList.size(); i++) {
            System.out.println((i + 1) + ". " + assessmentsList.get(i));
        }
        System.out.print("Select an assessment to manage (enter number): ");
        String input = scanner.nextLine();
        int choice;
        try {
            choice = Integer.parseInt(input);
            if (choice < 1 || choice > assessmentsList.size()) {
                System.out.println("Invalid selection.\n");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.\n");
            return;
        }

        String selectedAssessment = assessmentsList.get(choice - 1);
        System.out.println("Managing Assessment: " + selectedAssessment);

        System.out.println("1. Edit Assessment");
        System.out.println("2. Delete Assessment");
        System.out.println("3. View Assessment Results");
        System.out.print("Select an option: ");
        String manageChoice = scanner.nextLine();

        switch (manageChoice) {
            case "1":
                editAssessment(selectedAssessment);
                break;
            case "2":
                deleteAssessment(selectedAssessment);
                break;
            case "3":
                viewAssessmentResults(selectedAssessment);
                break;
            default:
                System.out.println("Invalid option.\n");
        }
    }

    private static void editAssessment(String assessmentName) {
        System.out.println("\n--- Edit Assessment ---");
        System.out.print("Enter new title: ");
        String newTitle = scanner.nextLine().trim();
        System.out.print("Enter new description: ");
        String newDescription = scanner.nextLine().trim();

        List<Question> questions = assessments.remove(assessmentName);
        assessments.put(newTitle, questions);

        System.out.println("Assessment updated successfully!\n");
    }

    private static void deleteAssessment(String assessmentName) {
        System.out.println("\n--- Delete Assessment ---");
        System.out.print("Are you sure you want to delete this assessment? (yes/no): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("yes")) {
            assessments.remove(assessmentName);
            System.out.println("Assessment deleted successfully!\n");
        } else {
            System.out.println("Delete operation cancelled.\n");
        }
    }

    private static void viewAssessmentResults(String assessmentName) {
        System.out.println("\n--- View Assessment Results ---");
        Map<String, Integer> results = currentUser.getResults();
        if (results.containsKey(assessmentName)) {
            int score = results.get(assessmentName);
            System.out.println("Your score for " + assessmentName + ": " + score);
        } else {
            System.out.println("No results available for this assessment.\n");
        }
    }

    private static void viewReportsAndAnalytics() {
        System.out.println("\n--- Reports and Analytics ---");

        if (assessments.isEmpty()) {
            System.out.println("No assessments available to generate reports.\n");
            return;
        }

        for (Map.Entry<String, List<Question>> entry : assessments.entrySet()) {
            String assessmentName = entry.getKey();
            List<Question> questions = entry.getValue();
            System.out.println("Assessment: " + assessmentName);
            System.out.println("Number of Questions: " + questions.size());

            Map<String, Integer> results = currentUser.getResults();
            if (results.containsKey(assessmentName)) {
                int score = results.get(assessmentName);
                System.out.println("Your Score: " + score);
            } else {
                System.out.println("No results available for this assessment.");
            }

            System.out.println("---------------");
        }
    }

    private static void showSettings() {
        System.out.println("\n--- Settings ---");
        System.out.println("1. Change Username");
        System.out.println("2. Change Password");
        System.out.print("Select an option: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                changeUsername();
                break;
            case "2":
                changePassword();
                break;
            default:
                System.out.println("Invalid option.\n");
        }
    }

    private static void changeUsername() {
        System.out.print("Enter new username: ");
        String newUsername = scanner.nextLine().trim();
        currentUser.setUsername(newUsername);
        System.out.println("Username updated successfully!\n");
    }

    private static void changePassword() {
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine().trim();
        currentUser.setPassword(newPassword);
        System.out.println("Password updated successfully!\n");
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

class User {
    private String username = "admin";
    private String password = "password";
    private Map<String, Integer> results = new HashMap<>();

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

    public Map<String, Integer> getResults() {
        return results;
    }

    public void addResult(String assessment, int score) {
        results.put(assessment, score);
    }
}

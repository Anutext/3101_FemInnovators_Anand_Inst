import java.util.*;
import java.util.concurrent.TimeUnit;

// User class to store user information
class User {
    private String username;
    private String email;
    private String password;
    private List<String> scheduledAssessments;
    private List<String> notifications;
    private Map<String, Integer> results;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.scheduledAssessments = new ArrayList<>();
        this.notifications = new ArrayList<>();
        this.results = new HashMap<>();
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public List<String> getScheduledAssessments() {
        return scheduledAssessments;
    }

    public void addAssessment(String assessment) {
        scheduledAssessments.add(assessment);
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public void addNotification(String notification) {
        notifications.add(notification);
    }

    public void addResult(String assessment, int score) {
        results.put(assessment, score);
    }

    public Map<String, Integer> getResults() {
        return results;
    }
}

// Main application class
public class AssessmentSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static Map<String, User> users = new HashMap<>();
    private static User currentUser = null;
    private static Map<String, List<Question>> assessments = new HashMap<>();
    private static Map<String, Integer> mockResults = new HashMap<>();

    public static void main(String[] args) {
        // Initialize some mock assessments and results
        initializeAssessments();

        while (true) {
            if (currentUser == null) {
                showMainMenu();
            } else {
                showDashboard();
            }
        }
    }

    private static void initializeAssessments() {
        List<Question> javaAssessment = new ArrayList<>();
        javaAssessment.add(new Question("What is the size of an int in Java?", "4 bytes", "Incorrect"));
        javaAssessment.add(new Question("What keyword is used to inherit a class in Java?", "extends", "Incorrect"));
        javaAssessment.add(new Question("Explain the concept of polymorphism.", "Polymorphism allows objects to be treated as instances of their parent class rather than their actual class.", "Incorrect"));
        assessments.put("Java Basics", javaAssessment);

        // Mock results (for demonstration)
        mockResults.put("Java Basics", 85);
    }

    private static void showMainMenu() {
        System.out.println("=== Welcome to the Assessment System ===");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Select an option: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                registerUser();
                break;
            case "2":
                loginUser();
                break;
            case "3":
                System.out.println("Exiting the system. Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option. Please try again.\n");
        }
    }

    private static void registerUser() {
        System.out.println("\n--- User Registration ---");
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        if (users.containsKey(username)) {
            System.out.println("Username already exists. Please try a different one.\n");
            return;
        }

        User newUser = new User(username, email, password);
        // Add a default assessment and notification for demonstration
        newUser.addAssessment("Java Basics");
        newUser.addNotification("Welcome " + username + "! Your first assessment 'Java Basics' is scheduled.");
        users.put(username, newUser);
        System.out.println("Registration successful! You can now log in.\n");
    }

    private static void loginUser() {
        System.out.println("\n--- User Login ---");
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        User user = users.get(username);
        if (user != null && user.checkPassword(password)) {
            currentUser = user;
            System.out.println("Login successful! Welcome, " + currentUser.getUsername() + ".\n");
        } else {
            System.out.println("Invalid credentials. Please try again.\n");
        }
    }

    private static void showDashboard() {
        System.out.println("=== Dashboard ===");
        System.out.println("1. View Scheduled Assessments");
        System.out.println("2. View Notifications");
        System.out.println("3. Start Assessment");
        System.out.println("4. Logout");
        System.out.print("Select an option: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                viewScheduledAssessments();
                break;
            case "2":
                viewNotifications();
                break;
            case "3":
                startAssessmentFlow();
                break;
            case "4":
                currentUser = null;
                System.out.println("Logged out successfully.\n");
                break;
            default:
                System.out.println("Invalid option. Please try again.\n");
        }
    }

    private static void viewScheduledAssessments() {
        System.out.println("\n--- Scheduled Assessments ---");
        List<String> assessmentsList = currentUser.getScheduledAssessments();
        if (assessmentsList.isEmpty()) {
            System.out.println("No assessments scheduled.\n");
        } else {
            for (int i = 0; i < assessmentsList.size(); i++) {
                System.out.println((i + 1) + ". " + assessmentsList.get(i));
            }
            System.out.println();
        }
    }

    private static void viewNotifications() {
        System.out.println("\n--- Notifications ---");
        List<String> notifications = currentUser.getNotifications();
        if (notifications.isEmpty()) {
            System.out.println("No new notifications.\n");
        } else {
            for (String notification : notifications) {
                System.out.println("- " + notification);
            }
            System.out.println();
        }
    }

    private static void startAssessmentFlow() {
        System.out.println("\n--- Start Assessment ---");
        List<String> assessmentsList = currentUser.getScheduledAssessments();
        if (assessmentsList.isEmpty()) {
            System.out.println("No assessments available to start.\n");
            return;
        }

        System.out.println("Available Assessments:");
        for (int i = 0; i < assessmentsList.size(); i++) {
            System.out.println((i + 1) + ". " + assessmentsList.get(i));
        }
        System.out.print("Select an assessment to start (enter number): ");
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
        System.out.println("You selected: " + selectedAssessment + "\n");

        // Proceed with System Check
        if (!systemCheck()) {
            System.out.println("System check failed. Please resolve the issues and try again.\n");
            return;
        }

        // Show Pre-Assessment Instructions
        preAssessmentInstructions(selectedAssessment);

        // Proctoring Check
        if (!proctoringCheck()) {
            System.out.println("Proctoring initialization failed. Cannot proceed with the assessment.\n");
            return;
        }

        // Start Assessment
        List<Question> questions = assessments.get(selectedAssessment);
        if (questions == null || questions.isEmpty()) {
            System.out.println("No questions available for this assessment.\n");
            return;
        }

        Map<Integer, String> answers = assessmentInterface(questions);

        // Submission
        if (submissionPage(answers)) {
            // Mock result generation
            int score = generateMockScore(questions.size());
            currentUser.addResult(selectedAssessment, score);
            System.out.println("Assessment submitted successfully! Your score: " + score + "/" + questions.size() + "\n");
        } else {
            System.out.println("Assessment submission cancelled.\n");
        }
    }

    private static boolean systemCheck() {
        System.out.println("--- System Check ---");
        System.out.println("Checking internet speed...");
        simulateDelay();
        System.out.println("Internet Speed: Pass");

        System.out.println("Checking camera...");
        simulateDelay();
        System.out.println("Camera: Pass");

        System.out.println("Checking microphone...");
        simulateDelay();
        System.out.println("Microphone: Pass");

        System.out.print("Run diagnostic? (yes/no): ");
        String input = scanner.nextLine().trim().toLowerCase();
        if (input.equals("yes")) {
            // Simulate diagnostic run
            System.out.println("Running diagnostics...");
            simulateDelay();
            System.out.println("All systems are compatible.\n");
            return true;
        } else {
            System.out.println("Diagnostic cancelled.\n");
            return false;
        }
    }

    private static void preAssessmentInstructions(String assessmentName) {
        System.out.println("--- Pre-Assessment Instructions ---");
        System.out.println("Welcome to the '" + assessmentName + "' assessment.");
        System.out.println("Please read the following guidelines before starting:");
        System.out.println("1. Ensure you have a stable internet connection.");
        System.out.println("2. Do not use any unauthorized devices during the assessment.");
        System.out.println("3. You will have 60 minutes to complete the assessment.");
        System.out.println("4. Once you start, you cannot pause the assessment.\n");

        System.out.print("Press Enter to start the assessment...");
        scanner.nextLine();
    }

    private static boolean proctoringCheck() {
        System.out.println("\n--- Proctoring Check ---");
        System.out.println("AI proctoring will be initialized to monitor your assessment.");
        System.out.print("Do you acknowledge and agree to proceed? (yes/no): ");
        String input = scanner.nextLine().trim().toLowerCase();
        if (input.equals("yes")) {
            System.out.println("Initializing AI proctoring...");
            simulateDelay();
            System.out.println("Proctoring active. You may begin your assessment.\n");
            return true;
        } else {
            System.out.println("Proctoring not initialized. Cannot proceed with the assessment.\n");
            return false;
        }
    }

    private static Map<Integer, String> assessmentInterface(List<Question> questions) {
        System.out.println("--- Assessment Interface ---");
        Map<Integer, String> answers = new HashMap<>();
        int currentQuestion = 1;
        int totalQuestions = questions.size();
        int timeLimitMinutes = 60;
        long startTime = System.currentTimeMillis();
        long endTime = startTime + TimeUnit.MINUTES.toMillis(timeLimitMinutes);
        boolean submitted = false;

        while (currentQuestion <= totalQuestions) {
            Question q = questions.get(currentQuestion - 1);
            System.out.println("Question " + currentQuestion + ": " + q.getText());
            System.out.print("Your Answer: ");
            String answer = scanner.nextLine();
            answers.put(currentQuestion, answer);

            System.out.println("1. Next");
            if (currentQuestion > 1) {
                System.out.println("2. Previous");
            }
            System.out.println("3. Submit");
            System.out.print("Select an option: ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                if (currentQuestion < totalQuestions) {
                    currentQuestion++;
                } else {
                    System.out.println("This is the last question.");
                }
            } else if (choice.equals("2") && currentQuestion > 1) {
                currentQuestion--;
            } else if (choice.equals("3")) {
                System.out.print("Are you sure you want to submit? (yes/no): ");
                String confirm = scanner.nextLine().trim().toLowerCase();
                if (confirm.equals("yes")) {
                    submitted = true;
                    break;
                }
            } else {
                System.out.println("Invalid option. Please try again.");
            }

            // Check time
            long currentTime = System.currentTimeMillis();
            if (currentTime > endTime) {
                System.out.println("\nTime is up! Automatically submitting your assessment.");
                submitted = true;
                break;
            }

            // Display remaining time
            long remainingTime = endTime - currentTime;
            long minutes = TimeUnit.MILLISECONDS.toMinutes(remainingTime);
            long seconds = TimeUnit.MILLISECONDS.toSeconds(remainingTime) % 60;
            System.out.println("Time Remaining: " + minutes + " minutes " + seconds + " seconds\n");
        }

        if (!submitted) {
            // Auto-submit if time is up
            System.out.println("\nTime is up! Automatically submitting your assessment.");
        }

        return answers;
    }

    private static boolean submissionPage(Map<Integer, String> answers) {
        System.out.println("\n--- Submission Page ---");
        System.out.println("Please review your answers before submission.");
        for (Map.Entry<Integer, String> entry : answers.entrySet()) {
            System.out.println("Question " + entry.getKey() + ": " + entry.getValue());
        }

        System.out.print("Do you want to submit your answers? (yes/no): ");
        String input = scanner.nextLine().trim().toLowerCase();
        if (input.equals("yes")) {
            return true;
        } else {
            System.out.print("Do you want to review your answers? (yes/no): ");
            String review = scanner.nextLine().trim().toLowerCase();
            if (review.equals("yes")) {
                // In a real application, you'd allow the user to navigate and change answers
                System.out.println("Review functionality not implemented in this console version.\n");
            }
            return false;
        }
    }

    private static int generateMockScore(int totalQuestions) {
        Random rand = new Random();
        return rand.nextInt(totalQuestions + 1); // Random score between 0 and totalQuestions
    }

    private static void simulateDelay() {
        try {
            Thread.sleep(1000); // 1 second delay to simulate processing
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

// Question class to store question text and answers
class Question {
    private String text;
    private String correctAnswer; // Not used in this console version
    private String feedback;      // Not used in this console version

    public Question(String text, String correctAnswer, String feedback) {
        this.text = text;
        this.correctAnswer = correctAnswer;
        this.feedback = feedback;
    }

    public String getText() {
        return text;
    }

    // Additional getters can be added as needed
}

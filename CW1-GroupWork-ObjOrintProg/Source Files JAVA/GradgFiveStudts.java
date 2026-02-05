import java.util.Scanner;

public class GradgFiveStudts {

    public static void main(String[] args) {
               Scanner input = new Scanner(System.in);
        int[] gradeCounts = new int[9];
        
        int studentNumber = 1;
        while (studentNumber <= 5) {
            System.out.print("Enter score for student " + studentNumber + " (0-100): ");
            int score = input.nextInt();
            
            if (score < 0 || score > 100) {
                System.out.println("Invalid score! Please enter a number between 0 and 100.");
                continue;
            }
            
            int grade;
            String remark;
            
            if (score >= 80) {
                grade = 1;
                remark = "D1";
            } else if (score >= 75) {
                grade = 2;
                remark = "D2";
            } else if (score >= 66) {
                grade = 3;
                remark = "C3";
            } else if (score >= 60) {
                grade = 4;
                remark = "C4";
            } else if (score >= 50) {
                grade = 5;
                remark = "C5";
            } else if (score >= 45) {
                grade = 6;
                remark = "C6";
            } else if (score >= 35) {
                grade = 7;
                remark = "P7";
            } else if (score >= 30) {
                grade = 8;
                remark = "P8";
            } else {
                grade = 9;
                remark = "F";
            }
            
            System.out.println("Student " + studentNumber + " → Score: " + score 
                             + ", Grade: " + grade + ", Remark: " + remark);
            
            gradeCounts[grade - 1]++;  
            studentNumber++;          
        }
        
        System.out.println("\n=== Grade Summary (5 students) ===");
        for (int i = 0; i < 9; i++) {
            System.out.println("Grade " + (i + 1) + " (" + getRemark(i + 1) + "): " 
                             + gradeCounts[i] + " student(s)");
        }
        
        input.close();
    }
    
    private static String getRemark(int grade) {
        return switch (grade) {
            case 1 -> "D1";
            case 2 -> "D2";
            case 3 -> "C3";
            case 4 -> "C4";
            case 5 -> "C5";
            case 6 -> "C6";
            case 7 -> "P7";
            case 8 -> "P8";
            case 9 -> "F";
            default -> "";
        };
    }
}

package helper;

import model.User;

import java.util.Random;

public class DataHelper {

    public static String generateRandomEmail(){
        return String.format("%s@test.com" , generateRandomString(7));
    }

    //PostForm and Article
    public static String generateRandomTitle(){
        return String.format("%s" , generateRandomString(20));
    }

    public static String generateRandomContent(){
        return String.format("%s" , generateRandomString(50));
    }

    //CommentForm
    public static String generateRandomName(){
        return String.format("%s" , generateRandomString(20));
    }

    public static String generateRandomComment(){
        return String.format("%s" , generateRandomString(50));
    }

    public static User getTestUser(){
        return new User ("","esteban@test.com", "Test123");
    }//getTestUser

    private static String generateRandomString(int targetStringLength){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

        return generatedString;
    }
}

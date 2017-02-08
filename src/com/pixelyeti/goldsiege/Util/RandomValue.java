package com.pixelyeti.goldsiege.Util;

import java.util.Random;

/**
 * Created by Callum on 09/06/2015.
 */
public class RandomValue {

    public static char randomChar(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max-min) + 1) + min;

        if (randomNum >= 0 && randomNum <= 9) {
            return (char)('0' + randomNum);
        }
        switch (randomNum) {
            case 10:
                return 'A';
            case 11:
                return 'B';
            case 12:
                return 'C';
            case 13:
                return 'D';
            case 14:
                return 'E';
            case 15:
                return 'F';
            default:
                return 'A';
        }
    }

}

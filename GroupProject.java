import java.util.concurrent.ThreadLocalRandom;
import java.math.BigInteger;
import java.util.Timer;
import java.lang.Math;
import java.util.TimerTask;

//
//  Group Project 1
//  Wesley Aldridge
//  Ali Almalki
// Alper Battal

public class GroupProject
{
    public static void checkForUsageErrors(String args[])
    {
        int length = args.length;
        int n;

        // Throw usage error:
        if (length != 1 && length != 2)
        {
            usageError();
        }

        n = Integer.parseInt(args[0]);

        if (n > 1000 && n < 1)
            usageError();

        if(length == 2)
        {
            if(Integer.parseInt(args[1]) != 1000)
            usageError();
        }
    }

    public static BigInteger[] createDigits(BigInteger[] numbers, int n, boolean run1000)
    {
        int timesToRun = (run1000 ? 1000 : 2);
        int remainder = n % 10;
        int howManyTimes = n / 10; // How many times to generate sets of 10 random digits for each number

        long start = (long) Math.pow(10, 9); // 10^9 will have 10 digits
        long end = start * 10;


        for (int i = 0; i < timesToRun; i++)
        {
            String stringHolder = new String("");


            for(int j = 0; j < howManyTimes; j++)
            {
                long longHolder;
                longHolder = ThreadLocalRandom.current().nextLong(start, end);
                stringHolder += longHolder;
            }

            if(remainder > 0)
            {
                long longHolder;
                long tempStart = (long) Math.pow(10, remainder - 1);
                long tempEnd = tempStart * 10;
                longHolder = ThreadLocalRandom.current().nextLong(tempStart, tempEnd);
                stringHolder += longHolder;
            }

            numbers[i] = new BigInteger(stringHolder);
        }
        return numbers;
    }

    public static BigInteger[] performDivision(BigInteger[] numbers, boolean run1000)
    {
        int timesToRun = (run1000 ? 500 : 1);
        BigInteger[] quotients = new BigInteger[(run1000? 500 : 1)];

        int j = 1;
        for(int i = 0; i < timesToRun; i += 2)
        {
            quotients[i] = numbers[i].divide(numbers[j]);

            j += 2;
        }

        return quotients;
    }

    public static void printResults(BigInteger[] numbers, BigInteger[] quotients, boolean run1000, long elapsedTime)
    {
        int timesToRun = (run1000 ? 500 : 1);
        int j = 1;
        for(int i = 0; i < timesToRun; i += 2)
        {
            System.out.println("The numerator is       : " + numbers[i]);
            System.out.println("The denominator is     : " + numbers[j]);
            System.out.println("The integer quotient is: " + quotients[i]);
            j += 2;
        }
            System.out.println("The integer division took: " + elapsedTime + "ns."); // one million ns = 1 ms
    }

    public static void usageError()
    {
        System.out.println("USAGE ERROR:");
        System.out.println(" To compile: javac GroupProject.java");
        System.out.println("     To run: java GroupProject n       (where n is the number of digits for each number, from 1 to 1000)");
        System.out.println("     To run: java GroupProject n 1000  (this will run the program for 1000 numbers instead of just 2)");
        System.exit(0);
    }


    public static void main(String[] args)
    {
        int length = args.length;
        BigInteger[] numbers;
        BigInteger[] quotients;

        long startTime;
        long endTime;
        long elapsedTime;



        // The number of digits per number:
        int n;
        // Whether to run 1000 times or not:
        boolean run1000;



        checkForUsageErrors(args);
        n = Integer.parseInt(args[0]);
        run1000 = length == 2;


        if(run1000)
        {
            numbers = new BigInteger[1000];
            quotients = new BigInteger[500];
            numbers = createDigits(numbers, n, true);
        }
        else
        {
            numbers = new BigInteger[2];
            quotients = new BigInteger[1];
            numbers = createDigits(numbers, n, false);

        }

        startTime = System.nanoTime();//System.currentTimeMillis();
        quotients = performDivision(numbers, run1000);
        endTime = System.nanoTime();//System.currentTimeMillis();
        elapsedTime = endTime - startTime;

        printResults(numbers, quotients, run1000, elapsedTime);


    }
}


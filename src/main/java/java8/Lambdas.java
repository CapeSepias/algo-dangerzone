package java8;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Chapter 1. Lambdas
 */
public class Lambdas {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        final int n = sc.nextInt();
        final String[] strings = new String[n];
        for (int i = 0; i < n; ++i) {
            strings[i] = sc.nextLine();
        }
//        Arrays.sort(strings, (first, second) -> Integer.compare(first.length(), second.length()));
        for (int i = 0; i < n; ++i) {
            System.out.println(strings[i]);
        }
    }
}


package edu.montana.csci.csci468.demo;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaReview {

    private Integer compute(String rpnEquation) {
        List<String> strings = Arrays.asList(rpnEquation.split("\\s+"));
        Deque<Integer> stack = new LinkedList<>();
        for (String string : strings) {
            if (string.equals("+")) {
                Integer x = stack.pop();
                Integer y = stack.pop();
                stack.push(y + x );
            } else if (string.equals("-")) {
                Integer x = stack.pop();
                Integer y = stack.pop();
                stack.push(y - x );
            } else {
                stack.push(Integer.parseInt(string));
            }
        }
        return stack.pop();
    }

    @Test
    public void codingOverView() {
        String rpnEquation = "21 22 + 1 -";
        Integer answer = compute(rpnEquation);
        assertEquals(42, answer);
    }

    public int showAdding() {
        int one = 1;
        int two = 2;
        return one + two;
    }

    public static void main(String[] args) throws IOException {
        LinkedList<Integer> stack = new LinkedList<>();
        BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
        do {
            System.out.print("> ");
            String string = obj.readLine();
            if (string.equals("+")) {
                Integer x = stack.pop();
                Integer y = stack.pop();
                stack.push(y + x );
            } else if (string.equals("-")) {
                Integer x = stack.pop();
                Integer y = stack.pop();
                stack.push(y - x );
            } else if (string.equals("exit")) {
                break;
            } else {
                stack.push(Integer.parseInt(string));
            }
            System.out.println("  stack:");
            for (Integer integer : stack) {
                System.out.println("    " + integer);
            }
            System.out.println("");
        } while(true);
        System.out.println("Answer: " + stack.pop());
    }

}

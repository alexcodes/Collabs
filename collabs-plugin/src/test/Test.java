package test;

public class Test {
    public static void main(String[] args) {
        System.out.println("Hello, world");
        int i;
        i = 10;
        method(i);
    }

    private static void method(int i) {
        int i1 = 1;
        i1++;
        System.out.println(i1);
    }
}
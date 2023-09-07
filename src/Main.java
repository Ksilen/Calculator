import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();
        System.out.println(calc(str));
    }

    public static String calc(String input) {

        String[] parts = input.split("[,\\s]+");
        myExeptions(parts);

        boolean romeNum = false;
        if (!parts[0].matches("\\d+"))
            romeNum = true;

        if (romeNum) {
            String romNumbers[] = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
            String arabNumbers[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
            for (int i = 0; i < 10; i++) {
                if (romNumbers[i].equals(parts[0])) {
                    parts[0] = arabNumbers[i];
                    break;
                }
            }
            for (int i = 0; i < 10; i++) {
                if (romNumbers[i].equals(parts[2])) {
                    parts[2] = arabNumbers[i];
                    break;
                }
            }
        }

        int res = 0;
        switch (parts[1]) {
            case "+":
                res = Integer.parseInt(parts[0]) + Integer.parseInt(parts[2]);
                break;
            case "-":
                int rasn = Integer.parseInt(parts[0]) - Integer.parseInt(parts[2]);
                if (romeNum && rasn <= 0)
                    throw new RuntimeException("В римской системе нет отрицательных чисел");
                res = rasn;
                break;
            case "/":
                int del = Integer.parseInt(parts[0]) / Integer.parseInt(parts[2]);
                if (romeNum && del <= 0)
                    throw new RuntimeException("В римской системе нет отрицательных чисел");
                res = del;
                break;
            case "*":
                res = Integer.parseInt(parts[0]) * Integer.parseInt(parts[2]);
                break;
        }
        if (romeNum) return toRome(res);
        return String.valueOf(res);
    }

    static String toRome(int num) {
        var keys = new String[]{"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        var vals = new int[]{100, 90, 50, 40, 10, 9, 5, 4, 1};
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < keys.length; i++) {
            while (num >= vals[i]) {
                var numCount = num / vals[i];
                num = num % vals[i];
                for (int j = 0; j < numCount; j++)
                    result.append(keys[i]);
            }
        }
        return String.valueOf(result);
    }

    static void myExeptions(String[] parts) {
        if (parts.length > 3)
            throw new RuntimeException("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");

        if (parts.length < 3)
            throw new RuntimeException("строка не является математической операцией");

        if ((!parts[0].matches("\\d+") && parts[2].matches("\\d+")) ||
                (parts[0].matches("\\d+") && !parts[2].matches("\\d+")))
            throw new RuntimeException("используются одновременно разные системы счисления");

        if (!((parts[0].matches("\\d+") && (parts[0].matches("(?:[1-9]|0[1-9]|10)") && parts[2].matches("(?:[1-9]|0[1-9]|10)")))
                || (parts[0].matches("(X|IX|IV|V?I{0,3})$") && parts[2].matches("(X|IX|IV|V?I{0,3})$"))))
            throw new RuntimeException("Калькулятор должен принимать на вход числа от 1 до 10 включительно");

        if (!parts[1].matches("[+*\\/-]"))
            throw new RuntimeException("арифметическая операция не соответствует \"+ - / *\"");
    }
}
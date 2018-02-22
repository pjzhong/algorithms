package dsa;

import dsa.LinkedList;

/**
 * Created by PJ_Z on 2/19/2018.
 */
public class Converter {
    private char digits[] =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'F'};

    public String convert(int digit, int base) {
        StringBuilder builder = new StringBuilder();

        LinkedList<Character> linkedList = new LinkedList<>();
        while(digit > 0) {
            linkedList.addFirst(digits[digit % base]);
            digit /= base;
        }

        for(int i = 0, size = linkedList.size(); i <  size; i++) {
            builder.append(linkedList.removeFirst());
        }

        return builder.toString();
    }
}

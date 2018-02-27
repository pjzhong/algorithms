package dsa.list;

/**
 * Created by PJ_Z on 2/20/2018.
 */
public class Paren {

    public boolean check(String chars) {
        return check(chars, 0, chars.length());
    }

    public boolean check(String chars, int lo ,int hi) {
        LinkedList<Character> parens = new LinkedList<>();

        for(int i = lo; i < hi; i++) {
            char character = chars.charAt(i);
            switch (chars.charAt(i)) {
                case ')':
                case '}':
                case '>':
                case ']': {
                    if(parens.isEmpty()) { return false; }

                    if((character - 1) == parens.getFirst() || (character - 2) == parens.getFirst()) {
                        parens.removeFirst();
                    } else {
                        return false;
                    }
                } break;
                default:parens.addFirst(character);
            }
        }

        return parens.isEmpty();
    }
}

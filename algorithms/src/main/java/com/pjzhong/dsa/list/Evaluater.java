package com.pjzhong.dsa.list;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by PJ_Z on 2/20/2018.
 */
public class Evaluater {

  private int currentIndex = 0;
  private String rpn = "";

  private void reset() {
    currentIndex = 0;
    rpn = "";
  }

  private double readNumber(String S) {
    double newOpnd = 0;
    for (; Character.isDigit(S.charAt(currentIndex)); currentIndex++) {
      newOpnd = 10.0 * newOpnd + (S.charAt(currentIndex) - '0');
    }
    if ('.' != S.charAt(currentIndex)) {
      return newOpnd;
    }
    double fraction = 1;
    while (Character.isDigit(S.charAt(++currentIndex))) {
      newOpnd = newOpnd + (S.charAt(currentIndex) - '0') * (fraction /= 10);
    }
    return newOpnd;
  }

  //todo implement it
  private char orderBetween(char stackTop, char current) {
    return Operator.orderBetween(stackTop, current);
  }

  private double calculate(char op, double opnd) {
    double result = 0;
    switch (op) {
      case '!': {
        result = 1;
        for (int i = 1, size = (int) opnd; i <= size; i++) {
          result *= i;
        }
      }
      break;
      default:
        throw new UnsupportedOperationException("Unsupport optr:" + op);
    }

    return result;
  }

  private double calculate(double first, char op, double second) {
    double result = 0;
    switch (op) {
      case '*': {
        result = first * second;
      }
      break;
      case '/': {
        result = first / second;
      }
      break;
      case '+': {
        result = first + second;
      }
      break;
      case '-': {
        result = first - second;
      }
      break;
      case '^': {
        result = 1;
        for (int i = 0; i < second; i++) {
          result *= first;
        }
      }
      break;
      default:
        throw new UnsupportedOperationException("Unsupport optr:" + op);
    }

    return result;
  }

  public double evaluate(String S) {
    reset();
    LinkedList<Double> opnd = new LinkedList<>();
    LinkedList<Character> optr = new LinkedList<>();

    optr.addFirst('$');
    StringBuilder rpnBuilder = new StringBuilder();
    while (!optr.isEmpty()) {
      char c = S.charAt(currentIndex);
      if (Character.isDigit(c)) {
        double newOpnd = readNumber(S);
        opnd.addFirst(newOpnd);
        rpnBuilder.append(newOpnd);
      } else {
        switch (orderBetween(optr.getFirst(), c)) {
          case '<': {
            optr.addFirst(c);
            currentIndex++;
          }
          break;
          case '=': {
            optr.removeFirst();
            currentIndex++;
          }
          break;
          case '>': {
            char op = optr.removeFirst();
            rpnBuilder.append(op);
            if ('!' == op) {
              Double popOpnd = opnd.removeFirst();
              opnd.addFirst(calculate(op, popOpnd));
            } else {
              Double opndTwo = opnd.removeFirst(), opndOne = opnd.removeFirst();
              opnd.addFirst(calculate(opndOne, op, opndTwo));
            }
          }
          break;
          default:
            throw new RuntimeException("Illegal expression");
        }
      }
    }

    rpn = rpnBuilder.toString();
    return opnd.removeFirst();
  }

  public String getRpn() {
    return rpn;
  }
}

enum Operator {
  ADD('+', 0, 0),
  SUB('-', 1, 1),
  MUL('*', 2, 2),
  DIV('/', 3, 3),
  POW('^', 4, 4),
  FAC('!', 5, 5),
  L_P('(', 6, 6),
  R_P(')', 7, 7),
  EOE('$', 8, 8);

  private static char[][] pri = {//运算符优先等级【栈顶】【当前】
      /*     |---------当前运算符-------|*/
      /*           +    -     *   /    ^    !    (    )    \0*/
      /*--  + */ {'>', '>', '<', '<', '<', '<', '<', '>', '>'},
      /*|   - */ {'>', '>', '<', '<', '<', '<', '<', '>', '>'},
      /*栈  * */ {'>', '>', '>', '>', '<', '<', '<', '>', '>'},
      /*栈  / */ {'>', '>', '>', '>', '<', '<', '<', '>', '>'},
      /*远  ^ */ {'>', '>', '>', '>', '>', '<', '<', '>', '>'},
      /*算  ! */ {'>', '>', '>', '>', '>', '>', ' ', '>', '>'},
      /*符  ( */ {'<', '<', '<', '<', '<', '<', '<', '=', ' '},
      /*|   ) */ {' ', ' ', ' ', ' ', ' ', ' ', ' ', '=', ' '},
      /*-- \0 */ {'<', '<', '<', '<', '<', '<', '<', ' ', '='},
  };
  private static Map<Character, Operator> operators = new HashMap<>();

  static {
    for (Operator operator : values()) {
      operators.put(operator.op, operator);
    }
  }

  private static Operator map(char op) {
    if (operators.containsKey(op)) {
      return operators.get(op);
    }

    throw new NoSuchElementException(op + "");
  }

  public static char orderBetween(char first, char second) {
    return pri[map(first).row][map(second).col];
  }

  private char op;
  private int row, col;

  Operator(char op, int row, int col) {
    this.op = op;
    this.row = row;
    this.col = col;
  }
}

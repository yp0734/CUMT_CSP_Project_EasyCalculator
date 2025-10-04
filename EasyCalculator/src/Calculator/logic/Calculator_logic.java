package Calculator.logic;

import java.util.Stack;

public class Calculator_logic {
    private StringBuilder expression = new StringBuilder();

    public String getExpression() {
        return expression.toString();
    }

    public String buttonPressed(String command) {
        switch (command) {
            case "清空":
                expression.setLength(0);
                break;
            case "退格":
                if (expression.length() > 0) {
                    expression.deleteCharAt(expression.length() - 1);
                }
                break;
            case "=":
                try {
                    String result = evaluate(expression.toString());
                    //进行计算
                    expression.setLength(0);
                    expression.append(result);
                }
                catch (Exception e) {
                    return "错误";
                }
                break;
            default:
                expression.append(command);
                break;
        }
        return expression.toString();
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '×' || c == '÷';
    }
    //判断是否为运算符

    private boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') return false;
        if ((op1 == '×' || op1 == '÷') && (op2 == '+' || op2 == '-')) return false;
        return true;
    }
    //判断运算符优先级

    private double applyOp(char op, double b, double a) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '×': return a * b;
            case '÷':
                if (b == 0) throw new UnsupportedOperationException("error");
                return a / b;
        }
        return 0;
    }
    //执行运算

    private String evaluate(String expression) {
        Stack<Double> values = new Stack<>();
        Stack<Character> ops = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if(Character.isDigit(c) || c =='.') {
                StringBuilder sb = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    sb.append(expression.charAt(i++));
                    //如果是数字或小数点，构建完整的数字
                }
                i--;
                values.push(Double.parseDouble(sb.toString()));
                //先将StringBuilder转换为String，再转换为Double，然后将Double压入栈
            }
            else if (isOperator(c)) {
                while (!ops.empty() && hasPrecedence(c, ops.peek())) {
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                ops.push(c);
            }
        }
        while (!ops.empty()) {
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));
        }
        double result = values.pop();

        if (result == (long)result) {
            return String.format("%d", (long)result);
        } else {
            return String.format("%s", result);
        }


    }

}

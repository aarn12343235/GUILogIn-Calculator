import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Stack;

public class Calculator extends JFrame implements ActionListener {
   private JPanel buttonPanel;
   private JTextField display;
   private JButton[] buttons;
   
   private ArrayList<String> expressions = new ArrayList<>();
   private boolean lastClickWasOperator = false;
   
   public Calculator() {
      setTitle("Calculator");
      setSize(350, 550);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLayout(null);
      setLocationRelativeTo(null);
      
      display = new JTextField();
      display.setFont(new Font("Museo Sans", Font.BOLD, 40));
      display.setHorizontalAlignment(JTextField.RIGHT);
      display.setEditable(false);
      display.setBounds(2, 20, 330, 120);
      add(display); 
      
      buttonPanel = new JPanel();
      buttonPanel.setLayout(new GridLayout(5, 4, 8, 8));
      buttonPanel.setBounds(2, 150, 330, 350);
      add(buttonPanel);
      
      String[] labels = {
                        "7", "8", "9", "/",
                        "4", "5", "6", "*",
                        "1", "2", "3", "-",
                        "%", "0", ".", "+",
                        "(-)", "=", "<-", "C"
                        };
      
      buttons = new JButton[labels.length]; 
      
      for(int i = 0; i < labels.length; i++) {
         buttons[i] = new JButton(labels[i]);
         buttons[i].setFont(new Font("Museo Sans", Font.BOLD, 20));
         buttons[i].addActionListener(this);
         buttonPanel.add(buttons[i]);
      }   
      
      setVisible(true);
   }
   
   public void actionPerformed(ActionEvent e) {
      String buttonSource = e.getActionCommand();
    
      try {
         if ("C".equals(buttonSource)) {
            expressions.clear();
            display.setText("");
            lastClickWasOperator = false;
         } 
         else if ("=".equals(buttonSource)) {
            if (!expressions.isEmpty()) {
               double result = calculateExpression();
               display.setText(String.valueOf(result));
               expressions.clear();
               expressions.add(String.valueOf(result));
               lastClickWasOperator = false;
            }
         }
         else if ("<-".equals(buttonSource)) {
            if (!expressions.isEmpty()) {
               expressions.remove(expressions.size() - 1);
               updateDisplay();
               lastClickWasOperator = false;
            }
         }
         else if ("(-)".equals(buttonSource)) {
            if (!expressions.isEmpty()) {
               String last = expressions.get(expressions.size() - 1);
               if (last.startsWith("-")) {
                  expressions.set(expressions.size() - 1, last.substring(1));
               } 
               else
                  expressions.set(expressions.size() - 1, "-" + last);
               
               updateDisplay();
            }
         }
         else if (isOperator(buttonSource)) {
            if (!lastClickWasOperator) {
               expressions.add(buttonSource);
               lastClickWasOperator = true;
            }
         }
         else {
            if (lastClickWasOperator || expressions.isEmpty())
               expressions.add(buttonSource);
            else {
                String last = expressions.get(expressions.size() - 1);
                expressions.set(expressions.size() - 1, last + buttonSource);
            }
            lastClickWasOperator = false;
         }
         
         updateDisplay();
      } catch (Exception ex) {
         display.setText("Error");
         expressions.clear();
      }
   }
   
   private void updateDisplay() {
      StringBuilder sb = new StringBuilder();
      for (String exp : expressions) {
         sb.append(exp);
      }
      display.setText(sb.toString());
   }
   
   private boolean isOperator(String op) {
      return "+-*/%.".contains(op);
   }
   
   // Stack-based expression evaluation(Shunting Yard Algorithm)
   private double calculateExpression() {
      
       StringBuilder expressionBuilder = new StringBuilder();
       for (String exp : expressions) {
           expressionBuilder.append(exp);
       }
       String expression = expressionBuilder.toString();
       
       Stack<Double> numbers = new Stack<>();
       Stack<Character> operators = new Stack<>();
       
       for (int i = 0; i < expression.length(); i++) {
           char c = expression.charAt(i);
           
           if (Character.isDigit(c)) {
               StringBuilder numBuilder = new StringBuilder();
               while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                   numBuilder.append(expression.charAt(i));
                   i++;
               }
               i--; // Adjust index after number storing
               numbers.push(Double.parseDouble(numBuilder.toString()));
           }
      
           else if (isOperator(String.valueOf(c))) {
               
               while (!operators.isEmpty() && hasPrecedence(c, operators.peek())) {
                   numbers.push(applyOperator(operators.pop(), numbers.pop(), numbers.pop()));
               }
               operators.push(c);
           }
       }
       
       while (!operators.isEmpty()) {
           numbers.push(applyOperator(operators.pop(), numbers.pop(), numbers.pop()));
       }
       
       return numbers.pop();
   }
   
   // Check operator precedence
   private boolean hasPrecedence(char op1, char op2) {
       if (op2 == '(' || op2 == ')') return false;
       if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) return false;
       return true;
   }
   
   // Apply an operator to two numbers
   private double applyOperator(char operator, double b, double a) {
       switch (operator) {
           case '+': return a + b;
           case '-': return a - b;
           case '*': return a * b;
           case '/': return a / b;
           case '%': return a % b;
       }
       throw new IllegalArgumentException("Unknown operator: " + operator);
   }
   
   public static void main(String... args) {
      new Calculator();
   }
}

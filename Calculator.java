import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Calculator extends JFrame implements ActionListener{

   private JPanel buttonPanel;
   private JTextField display;
   private JButton[] buttons;
   
   String operator;
   double num1, num2, result;
   
   public Calculator(){
      
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
      buttonPanel.setLayout(new GridLayout(4, 4, 8, 8));
      buttonPanel.setBounds(2, 150, 330, 350);
      add(buttonPanel);
      
      String[] labels = {
                        "7", "8", "9", "/",
                        "4", "5", "6", "*",
                        "1", "2", "3", "-",
                        "C", "0", "=", "+"
                        };
      
      
      buttons = new JButton[labels.length]; 
      
      for(int i = 0; i < labels.length; i++){
         buttons[i] = new JButton(labels[i]);
         buttons[i].setFont(new Font("Museo Sans", Font.BOLD, 20));
         buttons[i].addActionListener(this);
         buttonPanel.add(buttons[i]);
      
      }   
      
      setVisible(true);
   }
   
   public void actionPerformed(ActionEvent e) {
      String buttonSource = e.getActionCommand();
    
      if ("C".equals(buttonSource)) {
         display.setText("");
         num1 = num2 = result = 0;
         operator = "";
      } 
      else if ("=".equals(buttonSource)) {
         if(display.getText().isEmpty() || operator.isEmpty())
            return;
            
         num2 = Double.parseDouble(display.getText());
        
         if ("+".equals(operator)) result = num1 + num2;
         else if ("-".equals(operator)) result = num1 - num2;
         else if ("*".equals(operator)) result = num1 * num2;
         else if ("/".equals(operator)) result = num1 / num2;
        
         display.setText(String.valueOf(result));
         operator = "";
      } 
      else if (buttonSource.equals("+") || buttonSource.equals("-") || buttonSource.equals("*") || buttonSource.equals("/") ) {
         num1 = Double.parseDouble(display.getText());
         operator = buttonSource;
         display.setText("");
      } 
      else 
         display.setText(display.getText() + buttonSource);
   }
   public static void main(String... args){
      new Calculator();
   }
   
}
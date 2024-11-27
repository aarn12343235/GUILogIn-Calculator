import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.io.*;



public class LogIn extends JFrame implements ActionListener{

   Scanner userScan, passScan;
   int width = 350;
   int height = 200;
   JPanel panel;
   JLabel user, pass;
   JTextField userIn;
   JPasswordField passIn;
   JButton log, register;
   
   
   
   public LogIn(){
      setSize(width, height);
      setLocationRelativeTo(null);
      setLayout(null);
      setResizable(false);
      setDefaultCloseOperation(this.EXIT_ON_CLOSE);
      
      
      
      panel = new JPanel();
      panel.setSize(350, 200);
      panel.setLayout(null);
      add(panel); 
      
      user = new JLabel("Username: ");
      user.setBounds(15, 20, 100, 30);
      panel.add(user);
      
      userIn = new JTextField();
      userIn.setBounds(100, 20, 200, 30);
      panel.add(userIn);
      
      pass = new JLabel("Password: ");
      pass.setBounds(15, 60, 100, 30);
      panel.add(pass);
      
      passIn = new JPasswordField();
      passIn.setBounds(100, 60, 200, 30);
      panel.add(passIn);
      
      log = new JButton("LogIn");
      log.setBounds(100, 100, 70, 30);
      log.addActionListener(this);
      panel.add(log);
      
      register = new JButton("Register");
      register.setBounds(180, 100, 84, 30);
      register.addActionListener(this);
      panel.add(register);
      
      setVisible(true);
      
   }
   
   public void actionPerformed(ActionEvent e){
   
      try {
         if(e.getSource() == log){
            userScan = new Scanner(new File("users.txt"));
            passScan = new Scanner(new File("passwords.txt"));
            
            String storeUser = userScan.nextLine();
            String storePass = passScan.nextLine();
            
            if(userIn.getText().equals(storeUser) && passIn.getText().equals(storePass)){
               new Calculator();
               dispose();
            }
            else
               System.out.println("No credentials");
         }
         
         //userScan.close();
         //passScan.close();
      
      
      
      }catch(Exception ez){ez.printStackTrace();}
            
      if (e.getSource() == register) {
         new RegisterWindow();
      }
      
      
   }
   
   public static void main(String... args){
      new LogIn();
   }
   
}

class RegisterWindow extends JFrame implements ActionListener {
   JTextField userIn;
   JPasswordField passIn, confirmPassIn;
   JButton submitButton, cancelButton;
   
   public RegisterWindow() {
      setTitle("Register New Account");
      setSize(350, 250);
      setLocationRelativeTo(null);
      setLayout(null);
      setResizable(false);
      
      JLabel userLabel = new JLabel("Username:");
      userLabel.setBounds(20, 30, 100, 30);
      add(userLabel);
      
      userIn = new JTextField();
      userIn.setBounds(130, 30, 180, 30);
      add(userIn);
      
      JLabel passLabel = new JLabel("Password:");
      passLabel.setBounds(20, 70, 100, 30);
      add(passLabel);
      
      passIn = new JPasswordField();
      passIn.setBounds(130, 70, 180, 30);
      add(passIn);
      
      JLabel confirmPassLabel = new JLabel("Confirm Password:");
      confirmPassLabel.setBounds(20, 110, 120, 30);
      add(confirmPassLabel);
      
      confirmPassIn = new JPasswordField();
      confirmPassIn.setBounds(130, 110, 180, 30);
      add(confirmPassIn);
      
      submitButton = new JButton("Submit");
      submitButton.setBounds(100, 160, 80, 30);
      submitButton.addActionListener(this);
      add(submitButton);
      
      cancelButton = new JButton("Cancel");
      cancelButton.setBounds(190, 160, 80, 30);
      cancelButton.addActionListener(this);
      add(cancelButton);
      
      setVisible(true);
   }
   
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == submitButton) {
         String username = userIn.getText();
         String password = new String(passIn.getPassword());
         String confirmPassword = new String(confirmPassIn.getPassword());
         
         
         if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Please fill in field!");
            return;
         }
         
         if (!password.equals(confirmPassword)) {
            System.out.println("password dd not match!");
            return;
         }
         
         
         try {
            
            try (FileWriter usernameWriter = new FileWriter("users.txt", true);
                 BufferedWriter usernameBuffer = new BufferedWriter(usernameWriter)) {
                usernameBuffer.write(username);
                usernameBuffer.newLine();
            }
            
            
            try (FileWriter passwordWriter = new FileWriter("passwords.txt", true);
                 BufferedWriter passwordBuffer = new BufferedWriter(passwordWriter)) {
                passwordBuffer.write(password);
                passwordBuffer.newLine();
            }
            
            System.out.println("Log in successful!");
            dispose();
         } catch (IOException ex) {
            ex.printStackTrace();
         }
      }
      
      if (e.getSource() == cancelButton) {
         dispose();
      }
   }
}
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A simple calculator application built with Java Swing.
 * This class creates a graphical user interface for a calculator
 * that can perform basic arithmetic operations: addition, subtraction,
 * multiplication, and division.
 */
public class Calculator implements ActionListener {

    // Swing components for the calculator's GUI
    JFrame frame;
    JTextField textField;
    JButton[] numberButtons = new JButton[10];
    JButton[] functionButtons = new JButton[9];
    JButton addButton, subButton, mulButton, divButton;
    JButton decButton, equButton, delButton, clrButton, negButton;
    JPanel panel;

    // Fonts for styling the components
    Font myFont = new Font("Inter", Font.BOLD, 30);

    // Variables to hold the numbers and the result of the calculation
    double num1 = 0, num2 = 0, result = 0;
    // Variable to hold the operator (+, -, *, /)
    char operator;

    /**
     * Constructor for the Calculator class.
     * It initializes the GUI components and sets up the layout.
     */
    public Calculator() {
        // --- Frame Setup ---
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 550);
        frame.setLayout(null);
        frame.setResizable(false); // User cannot resize the window

        // --- Text Field (Display) Setup ---
        textField = new JTextField();
        textField.setBounds(50, 25, 300, 50);
        textField.setFont(myFont);
        textField.setEditable(false); // User cannot type directly into the display
        textField.setHorizontalAlignment(JTextField.RIGHT);

        // --- Button Initialization ---
        // Function buttons
        addButton = new JButton("+");
        subButton = new JButton("-");
        mulButton = new JButton("*");
        divButton = new JButton("/");
        decButton = new JButton(".");
        equButton = new JButton("=");
        delButton = new JButton("Del");
        clrButton = new JButton("C");
        negButton = new JButton("(-)");

        // Add function buttons to an array for easier management
        functionButtons[0] = addButton;
        functionButtons[1] = subButton;
        functionButtons[2] = mulButton;
        functionButtons[3] = divButton;
        functionButtons[4] = decButton;
        functionButtons[5] = equButton;
        functionButtons[6] = delButton;
        functionButtons[7] = clrButton;
        functionButtons[8] = negButton;

        // Set up listeners, fonts, and focus properties for function buttons
        for (int i = 0; i < 9; i++) {
            functionButtons[i].addActionListener(this);
            functionButtons[i].setFont(myFont);
            functionButtons[i].setFocusable(false); // Removes the focus border around the button
        }

        // Number buttons (0-9)
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
            numberButtons[i].setFont(myFont);
            numberButtons[i].setFocusable(false);
        }

        // --- Layout for Buttons ---
        // Set bounds for the special function buttons (delete, clear, negate)
        negButton.setBounds(50, 430, 100, 50);
        delButton.setBounds(150, 430, 100, 50);
        clrButton.setBounds(250, 430, 100, 50);

        // --- Panel for Number and Operator Buttons ---
        panel = new JPanel();
        panel.setBounds(50, 100, 300, 300);
        panel.setLayout(new GridLayout(4, 4, 10, 10)); // 4x4 grid with 10px spacing

        // Add buttons to the panel in a standard calculator layout
        // Row 1
        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(addButton);
        // Row 2
        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(subButton);
        // Row 3
        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(mulButton);
        // Row 4
        panel.add(decButton);
        panel.add(numberButtons[0]);
        panel.add(equButton);
        panel.add(divButton);

        // --- Add all components to the frame ---
        frame.add(panel);
        frame.add(negButton);
        frame.add(delButton);
        frame.add(clrButton);
        frame.add(textField);
        frame.setVisible(true); // Make the frame visible
    }

    /**
     * The main entry point for the application.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        // Create an instance of the Calculator, which starts the GUI
        Calculator calc = new Calculator();
    }

    /**
     * Handles action events from the calculator buttons.
     * This method is called whenever a button is clicked.
     * @param e The ActionEvent object containing details about the event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // --- Number Button Logic ---
        // Check if the source of the event is one of the number buttons
        for (int i = 0; i < 10; i++) {
            if (e.getSource() == numberButtons[i]) {
                // Append the digit to the text field
                textField.setText(textField.getText().concat(String.valueOf(i)));
            }
        }

        // --- Decimal Button Logic ---
        if (e.getSource() == decButton) {
            // Add a decimal point only if one doesn't already exist
            if (!textField.getText().contains(".")) {
                textField.setText(textField.getText().concat("."));
            }
        }

        // --- Operator Button Logic ---
        if (e.getSource() == addButton || e.getSource() == subButton || e.getSource() == mulButton || e.getSource() == divButton) {
            // Store the first number if the text field is not empty
            if(!textField.getText().isEmpty()){
                num1 = Double.parseDouble(textField.getText());
            }
            // Determine which operator was pressed
            if (e.getSource() == addButton) operator = '+';
            if (e.getSource() == subButton) operator = '-';
            if (e.getSource() == mulButton) operator = '*';
            if (e.getSource() == divButton) operator = '/';
            // Clear the text field to prepare for the second number
            textField.setText("");
        }

        // --- Equals Button Logic ---
        if (e.getSource() == equButton) {
             // Store the second number if the text field is not empty
            if(!textField.getText().isEmpty()){
                num2 = Double.parseDouble(textField.getText());
            }

            // Perform the calculation based on the stored operator
            switch (operator) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                case '/':
                    // Handle division by zero
                    if (num2 != 0) {
                        result = num1 / num2;
                    } else {
                        textField.setText("Error: Div by 0");
                        return; // Exit the method to prevent further execution
                    }
                    break;
            }
            // Display the result
            textField.setText(String.valueOf(result));
            // The result becomes the new first number for chained calculations
            num1 = result;
        }

        // --- Clear Button Logic ---
        if (e.getSource() == clrButton) {
            textField.setText("");
            num1 = 0;
            num2 = 0;
            result = 0;
        }

        // --- Delete Button Logic ---
        if (e.getSource() == delButton) {
            String string = textField.getText();
            textField.setText("");
            // Remove the last character from the string
            for (int i = 0; i < string.length() - 1; i++) {
                textField.setText(textField.getText() + string.charAt(i));
            }
        }
        
        // --- Negate Button Logic ---
        if (e.getSource() == negButton) {
            if(!textField.getText().isEmpty()){
                double temp = Double.parseDouble(textField.getText());
                temp *= -1; // Multiply by -1 to flip the sign
                textField.setText(String.valueOf(temp));
            }
        }
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    public static void main(String[] args) {
        
        JFrame frame = new JFrame("Huffman Encoding");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        inputPanel.setBackground(new Color(173, 216, 230));
        
        JLabel inputLabel = new JLabel("Enter string to encode: ");
        inputLabel.setFont(new Font("Arial", Font.BOLD, 14));
        inputLabel.setForeground(new Color(0, 102, 204));
        
        // Text Field
        JTextField inputField = new JTextField(35);
        inputField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        inputField.setForeground(Color.BLACK);
        inputField.setBackground(new Color(45, 212, 191));
        inputField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        
        // Button
        JButton encodeButton = new JButton("Encode");
        encodeButton.setFont(new Font("Verdana", Font.BOLD, 16));
        encodeButton.setBackground(new Color(225, 29, 72));
        encodeButton.setForeground(Color.WHITE);
        encodeButton.setFocusPainted(false);
        encodeButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

        inputPanel.add(inputLabel);
        inputPanel.add(inputField);
        inputPanel.add(encodeButton);

        // Output Panel
        JTextArea encodedOutput = new JTextArea(5, 50);
        encodedOutput.setEditable(false);
        encodedOutput.setFont(new Font("Monospaced", Font.PLAIN, 14));
        encodedOutput.setForeground(Color.BLACK);
        encodedOutput.setBackground(new Color(45, 212, 191));
        encodedOutput.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        
        JTextArea decodedOutput = new JTextArea(5, 50);
        decodedOutput.setEditable(false);
        decodedOutput.setFont(new Font("Monospaced", Font.PLAIN, 14));
        decodedOutput.setForeground(Color.BLACK);
        decodedOutput.setBackground(new Color(45, 212, 191));
        decodedOutput.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        JScrollPane encodedScroll = new JScrollPane(encodedOutput);
        JScrollPane decodedScroll = new JScrollPane(decodedOutput);

        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new GridLayout(4, 1, 5, 5));
        outputPanel.setBackground(new Color(173, 216, 230));
        
	JLabel encodedLabel = new JLabel("Encoded String:");
	encodedLabel.setFont(new Font("Arial", Font.BOLD, 14));
	encodedLabel.setForeground(new Color(34, 139, 34));
	
	JLabel decodedLabel = new JLabel("Decoded String:");
	decodedLabel.setFont(new Font("Arial", Font.BOLD, 14));
	decodedLabel.setForeground(new Color(34, 139, 34));
	
	outputPanel.add(encodedLabel);
	outputPanel.add(encodedScroll);
	outputPanel.add(decodedLabel);
	outputPanel.add(decodedScroll);
	
	// Efficiency Output
	JTextArea efficiencyOutput = new JTextArea(5, 50);
	efficiencyOutput.setEditable(false);
	efficiencyOutput.setFont(new Font("Monospaced", Font.PLAIN, 14));
	efficiencyOutput.setForeground(Color.BLACK);
	efficiencyOutput.setBackground(new Color(129, 140, 248));
	efficiencyOutput.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

	// Efficiency Panel
	JScrollPane efficiencyScroll = new JScrollPane(efficiencyOutput);
	JPanel efficiencyPanel = new JPanel();
	efficiencyPanel.setLayout(new BorderLayout());
	JLabel efficiencyLabel = new JLabel("Efficiency Analysis:");
	efficiencyLabel.setFont(new Font("Arial", Font.BOLD, 14));
	efficiencyLabel.setForeground(new Color(34, 139, 34));
	efficiencyLabel.setBackground(new Color(129, 140, 248));
	efficiencyPanel.add(efficiencyLabel, BorderLayout.NORTH);
	efficiencyPanel.add(efficiencyScroll, BorderLayout.CENTER);

	// Tree Visualization Panel
	JPanel treePanelContainer = new JPanel();
	treePanelContainer.setLayout(new BorderLayout());
	treePanelContainer.setPreferredSize(new Dimension(900, 450));

	// Add heading label
	JLabel treeHeading = new JLabel("Huffman Tree", SwingConstants.CENTER);
	treeHeading.setFont(new Font("Arial", Font.BOLD, 18));
	treeHeading.setForeground(new Color(146, 64, 14));
	treeHeading.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

	// Tree Panel
	TreePanel treePanel = new TreePanel();
	treePanel.setPreferredSize(new Dimension(900, 400));

	// Add heading and tree panel to the container
	treePanelContainer.add(treeHeading, BorderLayout.NORTH);
	treePanelContainer.add(treePanel, BorderLayout.CENTER);

	frame.add(treePanelContainer, BorderLayout.SOUTH);
	frame.add(inputPanel, BorderLayout.NORTH);
	frame.add(outputPanel, BorderLayout.CENTER);
	frame.add(efficiencyPanel, BorderLayout.EAST);

        // Action Listener for Encoding
        encodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText();
                if (input.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Input string cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    HuffmanCoder coder = new HuffmanCoder(input);
                    String encoded = coder.encode(input);
                    String decoded = coder.decode(encoded);

                    encodedOutput.setText(encoded);
                    decodedOutput.setText(decoded);

                    int asciiBits = input.length() * 8;
                    int huffmanBits = 0;
                    
                    for (char c : input.toCharArray()) {
                        huffmanBits += coder.getEncoder().get(c).length();
                    }

                    // Efficiency Calculation
                    int bitReduction = asciiBits - huffmanBits;
                    double efficiency = ((double) bitReduction / asciiBits) * 100;

                    efficiencyOutput.setText(
                        "ASCII Bits: " + asciiBits + "\n" +
                        "Huffman Bits: " + huffmanBits + "\n" +
                        "Reduction: " + bitReduction + " bits\n" +
                        "Efficiency: " + String.format("%.2f", efficiency) + " %"
                    );

                    treePanel.setTree(coder.getRoot());
                    
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Show the frame
        frame.setVisible(true);
    }
}


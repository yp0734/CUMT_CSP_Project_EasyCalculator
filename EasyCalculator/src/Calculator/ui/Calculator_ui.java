package Calculator.ui;

import Calculator.logic.Calculator_logic;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;

public class Calculator_ui implements ActionListener {
    private JFrame CalculatorFrame;
    private JTextField textField;
    private Calculator_logic calculatorLogic;

    public Calculator_ui() {
        calculatorLogic = new Calculator_logic();

        CalculatorFrame = new JFrame("EasyCalculator");
        CalculatorFrame.setBounds(400, 400, 300, 400);

        //文本框部分
        textField = new JTextField();
        textField.setBounds(10, 10, 260, 50);
        CalculatorFrame.add(textField, BorderLayout.NORTH);
        //将文本框置于页面顶部
        textField.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 30));
        //设置字体名称、样式、大小
        textField.setHorizontalAlignment(JTextField.LEFT);
        //设置文本框内文字右对齐
        textField.setEditable(false);
        //设置文本框不可编辑
        Border margin = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        //设置文本框内边距（在四周创建空白边框）
        Border line = BorderFactory.createLineBorder(Color.GRAY, 1);
        //设置文本框边框颜色、粗细
        textField.setBorder(BorderFactory.createCompoundBorder(margin, line));
        //将边框和内边距组合

        //按键部分
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        String[][] buttons = {
                {"清空", "÷", "×", "退格"},
                {"7", "8", "9", "-"},
                {"4", "5", "6", "+"},
                {"1", "2", "3", "="},
                {"0", "."}
        };
        for (int y = 0; y < buttons.length; y++) {
            for (int x = 0; x < buttons[y].length; x++) {
                //遍历二位数组来设置每个按钮的位置
                String label = buttons[y][x];
                gbc.gridx = x;
                gbc.gridy = y;
                gbc.gridwidth = 1;
                gbc.gridheight = 1;
                //重置按钮宽高为1（默认值）
                JButton button = new JButton(label);
                Font buttonFont = new Font("Microsoft YaHei UI", Font.BOLD, 18);
                button.setFont(buttonFont);
                if (label.equals("0")) {
                    gbc.gridwidth = 2;
                }
                //让"0"按钮宽度为其他按钮的两倍
                if (label.equals("=")) {
                    gbc.gridheight = 2;
                }
                //让"="按钮高度为其他按钮的两倍
                if (label.equals(".")) {
                    gbc.gridx = 2;
                    gbc.gridy = 4;
                }
                //让"."按钮位于第三列第五行
                if ("÷×-+".contains(label)) {
                    button.setForeground(Color.RED);
                }
                if ("1234567890.=".contains(label)) {
                    button.setForeground(Color.BLUE);
                }

                button.addActionListener(this);
                //添加监听器
                buttonPanel.add(button, gbc);
            }
        }

        CalculatorFrame.add(buttonPanel, BorderLayout.CENTER);
        CalculatorFrame.setVisible(true);
        CalculatorFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Calculator_ui();
            }
        });
    }
    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        String command = e.getActionCommand();
        String result = calculatorLogic.buttonPressed(command);
        textField.setText(result);
    }
}

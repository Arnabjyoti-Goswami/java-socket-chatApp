package srcCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Server extends JFrame implements ActionListener {
  public static final String IconsDir = "Icons/";

  Server() {
    setLayout(null);

    JPanel headerPanel = new JPanel();
    headerPanel.setBackground(new Color(7, 94, 84));
    headerPanel.setBounds(0,0,450,70);
    headerPanel.setLayout(null);
    add(headerPanel);

    JLabel imageLabel = setImageLabel(headerPanel, IconsDir + "3.png", 5, 20, 25, 25);

    imageLabel.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent ae) {
        System.exit(0);
      }
    });

    JLabel profileLabel = setImageLabel(headerPanel, IconsDir + "1.png", 40, 10, 50, 50);

    JLabel videoLabel = setImageLabel(headerPanel, IconsDir + "video.png", 300, 20, 30, 30);

    JLabel phoneLabel = setImageLabel(headerPanel, IconsDir + "phone.png", 360, 20, 35, 30);

    JLabel menuLabel = setImageLabel(headerPanel, IconsDir + "3icon.png", 420, 20, 10, 25);

    JLabel nameLabel = setTextLabel(headerPanel, "Gaitonde", 110, 15, 100, 18, 18, true);

    JLabel statusLabel = setTextLabel(headerPanel, "Active Now", 110, 35, 100, 18, 14, false);

    JLabel messagesContainer = new JLabel();
    // to show the bg color for JLabel:
    messagesContainer.setOpaque(true); 
    messagesContainer.setBackground(new Color(185, 190, 196));
    messagesContainer.setBounds(5, 75, 440, 570);
    messagesContainer.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
    add(messagesContainer);

    JTextField newMessageText = new JTextField();
    newMessageText.setBounds(5, 655, 310, 40);
    newMessageText.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
    add(newMessageText);

    JButton sendButton = new JButton("Send");
    sendButton.setBounds(320, 655, 123, 40);
    sendButton.setBackground(new Color(7, 94, 84));
    sendButton.setForeground(Color.WHITE);
    sendButton.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
    add(sendButton);

    setSize(450, 700);
    setLocation(200, 50);
    getContentPane().setBackground(Color.WHITE);
    // removes the window options like close and minimize:
    setUndecorated(true);
    setVisible(true);
  }

  public void actionPerformed(ActionEvent ae) {

  }

  public JLabel setImageLabel(JPanel panel, String imagePath, int left, int top, int width, int height) {
    ImageIcon originalImageIcon = new ImageIcon(ClassLoader.getSystemResource(imagePath));
    Image resizedImage = originalImageIcon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
    ImageIcon resizedImageIcon = new ImageIcon(resizedImage);

    JLabel imageLabel = new JLabel(resizedImageIcon);
    imageLabel.setBounds(left, top, width, height);
    panel.add(imageLabel);
    return imageLabel;
  }

  public JLabel setTextLabel(JPanel panel, String text, int left, int top, int width, int height, int fontSize, boolean isBold) {
    JLabel label = new JLabel(text);
    label.setBounds(left, top, width, height);

    label.setForeground(Color.WHITE);

    if (isBold) {
      label.setFont(new Font("SAN_SERIF", Font.BOLD, fontSize));
    } else {
      label.setFont(new Font("SAN_SERIF", Font.PLAIN, fontSize));
    }

    panel.add(label);
    return label;
  }

  public static void main(String[] args) {
    new Server();
  }
}

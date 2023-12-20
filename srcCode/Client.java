package srcCode;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

// to send and receive messages with sockets:
import java.net.*;
import java.io.*;

import srcCode.Server;

public class Client implements ActionListener {

  public static final String IconsDir = "Icons/";

  JTextField newMessageText;
  static JPanel messagesContainer;
  static Box verticalBox = Box.createVerticalBox();
  static Color messagesContainerBGColor = new Color(185, 190, 196);

  static DataOutputStream dout;
  static JFrame f = new JFrame();

  Client () {
    f.setLayout(null);

    JPanel headerPanel = new JPanel();
    headerPanel.setBackground(new Color(7, 94, 84));
    headerPanel.setBounds(0,0,450,70);
    headerPanel.setLayout(null);
    f.add(headerPanel);

    JLabel imageLabel = Server.getImageLabel(headerPanel, IconsDir + "3.png", 5, 20, 25, 25);

    imageLabel.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent ae) {
        System.exit(0);
      }
    });

    JLabel profileLabel = Server.getImageLabel(headerPanel, IconsDir + "2.png", 40, 10, 50, 50);
    JLabel videoLabel = Server.getImageLabel(headerPanel, IconsDir + "video.png", 300, 20, 30, 30);
    JLabel phoneLabel = Server.getImageLabel(headerPanel, IconsDir + "phone.png", 360, 20, 35, 30);
    JLabel menuLabel = Server.getImageLabel(headerPanel, IconsDir + "3icon.png", 420, 20, 10, 25);
    JLabel nameLabel = Server.getTextLabel(headerPanel, "Bunty", 110, 15, 100, 18, 18, true);
    JLabel statusLabel = Server.getTextLabel(headerPanel, "Active Now", 110, 35, 100, 18, 14, false);

    messagesContainer = new JPanel();
    // to show the bg color for JLabel:
    messagesContainer.setBackground(messagesContainerBGColor);
    messagesContainer.setBounds(5, 75, 440, 570);
    messagesContainer.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
    f.add(messagesContainer);

    newMessageText = new JTextField();
    newMessageText.setBounds(5, 655, 310, 40);
    newMessageText.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
    f.add(newMessageText);

    JButton sendButton = new JButton("Send");
    sendButton.setBounds(320, 655, 123, 40);
    sendButton.setBackground(new Color(7, 94, 84));
    sendButton.setForeground(Color.WHITE);
    sendButton.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
    sendButton.addActionListener(this);
    f.add(sendButton);
 
    f.setSize(450, 700);
    f.setLocation(800, 50);
    f.getContentPane().setBackground(Color.WHITE);
    // removes the window options like close and minimize:
    f.setUndecorated(true);
    f.setVisible(true);
  }

  public void actionPerformed(ActionEvent ae) {
    String messageText = newMessageText.getText();
    JPanel messagePanel = Server.formattedMessagePanel(messageText, false);

    // If there are multiple messages, align them vertically
    verticalBox.add(messagePanel);
    verticalBox.add(Box.createVerticalStrut(15));

    messagesContainer.setLayout(new BorderLayout());
    messagesContainer.add(verticalBox, BorderLayout.PAGE_START);

    try {
      dout.writeUTF(messageText);
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Empty the typing field after sending a message
    newMessageText.setText("");

    // Repaint the JFrame to show the new message
    f.repaint();
    f.revalidate();
    f.validate();
  }

  public static void main(String[] args) {
    new Client();

    try {
      Socket s = new Socket("localhost", 6001);
      DataInputStream din = new DataInputStream(s.getInputStream());
      dout = new DataOutputStream(s.getOutputStream());

      while (true) {
        String msgin = din.readUTF();
        JPanel messagePanel = Server.formattedMessagePanel(msgin, true);

        verticalBox.add(messagePanel);
        verticalBox.add(Box.createVerticalStrut(15));

        messagesContainer.setLayout(new BorderLayout());
        messagesContainer.add(verticalBox, BorderLayout.PAGE_START);

        f.validate();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

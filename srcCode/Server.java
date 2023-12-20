package srcCode;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;

// to display the time at which the message was sent: 
import java.text.SimpleDateFormat;
import java.util.Calendar;

// to send and receive messages with sockets:
import java.net.*;
import java.io.*;

public class Server implements ActionListener {

  static JFrame f = new JFrame();

  public static final String IconsDir = "Icons/";

  JTextField newMessageText;
  static JPanel messagesContainer;
  static Box verticalBox = Box.createVerticalBox();
  static Color messagesContainerBGColor = new Color(185, 190, 196);

  static DataOutputStream dout;

  Server() {
    f.setLayout(null);

    JPanel headerPanel = new JPanel();
    headerPanel.setBackground(new Color(7, 94, 84));
    headerPanel.setBounds(0,0,450,70);
    headerPanel.setLayout(null);
    f.add(headerPanel);

    JLabel imageLabel = getImageLabel(headerPanel, IconsDir + "3.png", 5, 20, 25, 25);

    imageLabel.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent ae) {
        System.exit(0);
      }
    });

    JLabel profileLabel = getImageLabel(headerPanel, IconsDir + "1.png", 40, 10, 50, 50);
    JLabel videoLabel = getImageLabel(headerPanel, IconsDir + "video.png", 300, 20, 30, 30);
    JLabel phoneLabel = getImageLabel(headerPanel, IconsDir + "phone.png", 360, 20, 35, 30);
    JLabel menuLabel = getImageLabel(headerPanel, IconsDir + "3icon.png", 420, 20, 10, 25);
    JLabel nameLabel = getTextLabel(headerPanel, "Gaitonde", 110, 15, 100, 18, 18, true);
    JLabel statusLabel = getTextLabel(headerPanel, "Active Now", 110, 35, 100, 18, 14, false);

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
    f.setLocation(200, 50);
    f.getContentPane().setBackground(Color.WHITE);
    // removes the window options like close and minimize:
    f.setUndecorated(true);
    f.setVisible(true);
  }

  public void actionPerformed(ActionEvent ae) {
    String messageText = newMessageText.getText();
    JPanel messagePanel = formattedMessagePanel(messageText, false);

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

  public static JPanel formattedMessagePanel(String messageText, boolean isReceived) {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    JLabel messageTextLabel = new JLabel("<html><p style=\"width: 200px\">" + messageText + "</p></html>");
    messageTextLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
    messageTextLabel.setBackground(new Color(37, 211, 102));
    messageTextLabel.setOpaque(true);
    // Add padding to the message label
    messageTextLabel.setBorder(new EmptyBorder(15, 15, 15, 15));

    panel.add(messageTextLabel);

    Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    JLabel timeLabel = new JLabel();
    timeLabel.setText(sdf.format(cal.getTime()));
    
    panel.add(timeLabel);
    panel.setBackground(messagesContainerBGColor);

    JPanel alignedPanel = new JPanel(new BorderLayout());
    if (isReceived) {
      alignedPanel.add(panel, BorderLayout.LINE_START);
    } else {
      alignedPanel.add(panel, BorderLayout.LINE_END);
    }
    alignedPanel.setBackground(messagesContainerBGColor);
    
    return alignedPanel;
  }

  public static JLabel getImageLabel(JPanel panel, String imagePath, int left, int top, int width, int height) {
    ImageIcon originalImageIcon = new ImageIcon(ClassLoader.getSystemResource(imagePath));
    Image resizedImage = originalImageIcon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
    ImageIcon resizedImageIcon = new ImageIcon(resizedImage);

    JLabel imageLabel = new JLabel(resizedImageIcon);
    imageLabel.setBounds(left, top, width, height);
    panel.add(imageLabel);
    return imageLabel;
  }

  public static JLabel getTextLabel(JPanel panel, String text, int left, int top, int width, int height, int fontSize, boolean isBold) {
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

    try {
      ServerSocket skt = new ServerSocket(6001);

      while (true) {
        Socket s = skt.accept();
        DataInputStream din = new DataInputStream(s.getInputStream());
        dout = new DataOutputStream(s.getOutputStream());

        while (true) {
          String msgin = din.readUTF();
          JPanel messagePanel = formattedMessagePanel(msgin, true);

          // If there are multiple messages, align them vertically
          verticalBox.add(messagePanel);
          verticalBox.add(Box.createVerticalStrut(15));

          messagesContainer.setLayout(new BorderLayout());
          messagesContainer.add(verticalBox, BorderLayout.PAGE_START);

          // To make this method static (as main method is static), we had to make a object 'f' of the JFrame class in this class (before this we were doing the simple extends JFrame with this class)
          f.validate(); // refresh the frame
        }
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

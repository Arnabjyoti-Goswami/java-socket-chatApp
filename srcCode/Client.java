package srcCode;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

// to display the time at which the message was sent: 
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Client extends JFrame implements ActionListener {

  public static final String IconsDir = "Icons/";

  public JTextField newMessageText;
  public JPanel messagesContainer;
  public Box verticalBox = Box.createVerticalBox();
  public static Color messagesContainerBGColor = new Color(185, 190, 196);

  Client () {
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

    JLabel profileLabel = setImageLabel(headerPanel, IconsDir + "2.png", 40, 10, 50, 50);

    JLabel videoLabel = setImageLabel(headerPanel, IconsDir + "video.png", 300, 20, 30, 30);

    JLabel phoneLabel = setImageLabel(headerPanel, IconsDir + "phone.png", 360, 20, 35, 30);

    JLabel menuLabel = setImageLabel(headerPanel, IconsDir + "3icon.png", 420, 20, 10, 25);

    JLabel nameLabel = setTextLabel(headerPanel, "Bunty", 110, 15, 100, 18, 18, true);

    JLabel statusLabel = setTextLabel(headerPanel, "Active Now", 110, 35, 100, 18, 14, false);

    messagesContainer = new JPanel();
    // to show the bg color for JLabel:
    messagesContainer.setBackground(messagesContainerBGColor);
    messagesContainer.setBounds(5, 75, 440, 570);
    messagesContainer.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
    add(messagesContainer);

    newMessageText = new JTextField();
    newMessageText.setBounds(5, 655, 310, 40);
    newMessageText.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
    add(newMessageText);

    JButton sendButton = new JButton("Send");
    sendButton.setBounds(320, 655, 123, 40);
    sendButton.setBackground(new Color(7, 94, 84));
    sendButton.setForeground(Color.WHITE);
    sendButton.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
    sendButton.addActionListener(this);
    add(sendButton);
 
    setSize(450, 700);
    setLocation(800, 50);
    getContentPane().setBackground(Color.WHITE);
    // removes the window options like close and minimize:
    setUndecorated(true);
    setVisible(true);
  }

  public void actionPerformed(ActionEvent ae) {
    String messageText = newMessageText.getText();
    JPanel messagePanel = formattedMessagePanel(messageText);

    // If there are multiple messages, align them vertically
    verticalBox.add(messagePanel);
    verticalBox.add(Box.createVerticalStrut(15));

    messagesContainer.setLayout(new BorderLayout());
    messagesContainer.add(verticalBox, BorderLayout.PAGE_START);

    // Empty the typing field after sending a message
    newMessageText.setText("");

    // Repaint the JFrame to show the new message
    repaint();
    revalidate();
    validate();
  }

  public static JPanel formattedMessagePanel(String messageText) {
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

    JPanel right = new JPanel(new BorderLayout());
    right.add(panel, BorderLayout.LINE_END);
    right.setBackground(messagesContainerBGColor);

    return right;
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
    new Client();
  }
}

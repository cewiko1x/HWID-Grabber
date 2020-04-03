package io.github.cewiko1x;

import com.sun.scenario.effect.impl.sw.java.JSWBlend_COLOR_BURNPeer;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HWID {

    public static HWID INSTANCE = new HWID();

    String hwid;

    public void init() {
        JFrame frame = new JFrame("HWID");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
JTextField hwidField = new JTextField();
JButton generateButton = new JButton("Generate");
JButton copyButton = new JButton("Copy");
JButton githubButton = new JButton("Github");
hwidField.setVisible(true);
hwidField.setBounds(7,3,270,25);
hwidField.setFont(new Font("Verdana", Font.PLAIN, 10));
hwidField.setHorizontalAlignment(JTextField.CENTER);
generateButton.setVisible(true);
generateButton.setBounds(7,30,80, 25);
generateButton.setFont(new Font("Verdana", Font.PLAIN, 10));

generateButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            hwid = getHWID();
if(hwid != null) {
    hwidField.setText(hwid);
}
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }
});

copyButton.setVisible(true);
copyButton.setBounds(100,30,80, 25);
copyButton.setFont(new Font("Verdana", Font.PLAIN, 10));

copyButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      if(hwidField.getText() == null) {
          return;
      }

        StringSelection selection = new StringSelection(hwidField.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }
});

githubButton.setVisible(true);
githubButton.setBounds(193,30,80, 25);
githubButton.setFont(new Font("Verdana", Font.PLAIN, 10));

githubButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Desktop.getDesktop().browse(new URI("https://github.com/cewiko1x"));
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
    }
});

        frame.setVisible(true);
        frame.setSize(300,100);
        frame.setLayout(null);
        frame.add(hwidField);
        frame.add(generateButton);
        frame.add(copyButton);
        frame.add(githubButton);
    }

    public static HWID getInstance() {
        return INSTANCE;
    }

    private String getHWID() throws NoSuchAlgorithmException, UnsupportedEncodingException {

        String s = "";
        final String main = System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("COMPUTERNAME") + System.getProperty("user.name").trim();
        final byte[] bytes = main.getBytes("UTF-8");
        final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        final byte[] md5 = messageDigest.digest(bytes);
        int i = 0;
        for (final byte b : md5) {
            s += Integer.toHexString((b & 0xFF) | 0x300).substring(0, 3);
            if (i != md5.length - 1) {
                s += "-";
            }
            i++;
        }
        return s;
    }

}

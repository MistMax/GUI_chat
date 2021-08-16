package com.example.gui_chat;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

public class HelloController {
    Socket socket;
    @FXML
    TextField textField;
    @FXML
    TextArea textArea;
    @FXML
    TextArea ONLINE;
    @FXML
    private  void send() {
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            String text = textField.getText();
            out.writeUTF(text);
            textField.clear(); // очищаем поле ввода сообщения
            textField.requestFocus(); // возвращаем фокусировку на поле ввода
            textArea.appendText("Вы: "+text+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    private void connect() {
        try {
            socket = new Socket("localhost", 8188);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        String response = "";
                        ArrayList<String> usersName = new ArrayList<String>();
                        try {
                            Object object = ois.readObject();
                            if(object.getClass().equals(usersName.getClass())){
                                usersName = ((ArrayList<String>) object);
                                System.out.println(usersName);
                                ONLINE.clear(); // Очищает TextArea
                                for (String userName:usersName) {
                                    ONLINE.appendText(userName+"\n");
                                }
                            }else if (object.getClass().equals(response.getClass())){
                                response = object.toString();
                                textArea.appendText(response+"\n");
                            }else{
                                textArea.appendText("Произошла ошибка");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
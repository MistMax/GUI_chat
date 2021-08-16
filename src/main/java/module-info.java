module com.example.gui_chat {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gui_chat to javafx.fxml;
    exports com.example.gui_chat;
}
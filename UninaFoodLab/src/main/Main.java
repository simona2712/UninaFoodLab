package main;

import java.awt.EventQueue;

import controller.Controller;
import gui.LoginPage;

public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Controller controller = new Controller();
                new LoginPage(controller);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
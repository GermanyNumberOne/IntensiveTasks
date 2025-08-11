package com.example.homework2;


import com.example.homework2.view.ConsoleInterface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Homework2Application {

    public static void main(String[] args) {
        ConsoleInterface consoleInterface = new ConsoleInterface();
        consoleInterface.startView();
    }

}

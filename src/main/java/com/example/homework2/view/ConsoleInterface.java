package com.example.homework2.view;

import com.example.homework2.config.HibernateConfig;
import com.example.homework2.dao.api.UserDao;
import com.example.homework2.dao.impl.UserDaoImpl;
import com.example.homework2.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleInterface {
    private final UserDao userDao = new UserDaoImpl();
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private void showMenu(){
        System.out.println("______________________________________________________________");
        System.out.println("Choose operation: ");
        System.out.println("1. Save");
        System.out.println("2. Read by Id");
        System.out.println("3. Update");
        System.out.println("4. Delete");
        System.out.println("5. Read All");
        System.out.println("6. Exit");
        System.out.print("Your choice: ");
    }

    public void startView(){
        boolean work = true;
        String choice;
        while(work){
            showMenu();
            try {
                choice = reader.readLine();
                System.out.println();

            switch (choice) {
                case "1":{
                    System.out.println("________SAVE________");

                    User user = enterUserData();
                    userDao.save(user);
                    System.out.println("____________________");
                   break;
                }
                case "2":{
                    System.out.println("________READ________");

                    System.out.print("Enter id:");
                    User user = userDao.read(readNumber());
                    System.out.println();

                    if (user == null) {
                        System.out.println("User not found");
                        break;
                    }

                    System.out.println(user.toString());

                    System.out.println("____________________");
                    break;
                }
                case "3":{
                    System.out.println("________UPDATE________");

                    System.out.println("Enter user's id:");
                    User user = userDao.read(readNumber());
                    System.out.println();

                    if(user == null) {
                        System.out.println("User not found");
                        break;
                    }

                    System.out.println(user.toString());

                    System.out.println("Enter new user data");
                    User temp = enterUserData();
                    if(temp.getAge() != null)user.setAge(temp.getAge());
                    if(temp.getEmail() != null)user.setEmail(temp.getEmail());
                    if(temp.getName() != null)user.setName(temp.getName());

                    userDao.update(user);

                    System.out.println("______________________");
                    break;
                }
                case "4":{
                    System.out.println("________DELETE________");

                    System.out.println("Enter user's id:");
                    userDao.delete(userDao.read(readNumber()));
                    System.out.println();

                    System.out.println("______________________");
                    break;
                }
                case "5":{
                    System.out.println("________READ_ALL________");
                    userDao.getAll().stream().forEach(user -> System.out.println(user.toString()));
                    System.out.println("________________________");
                    break;
                } case "6":{
                    work = false;
                    HibernateConfig.shutdown();
                    break;
                }
                default:
                    System.out.println("Try again");
                    continue;
            }
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        System.out.println("____________END____________");
    }

    private Long readNumber(){
        try {
            String buffer = reader.readLine();
            Long number = Long.parseLong(buffer);
            return number;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private User enterUserData() throws IOException{
        System.out.print("Enter name: ");
        String name = reader.readLine();

        System.out.println("");

        System.out.print("Enter email: ");
        String email = reader.readLine();

        System.out.println("");

        System.out.print("Enter age: ");
        Integer age = Integer.parseInt(reader.readLine());

        System.out.println("");

        return new User(name, email, age);
    }

}

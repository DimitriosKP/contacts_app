package api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Users {
    private static List<User> _users;
    public static User LoggedUser = null;
    static String DELIMETER = ";";

    public static void load() {
        try {
            File projectDir = new File(System.getProperty("user.dir"));
            BufferedReader br = Files.newBufferedReader(Paths.get(projectDir+"/src/data/users.txt"));

            _users = new ArrayList<>();

            //read the file line by line
            String line;
            while((line = br.readLine()) != null) {
                //ignore line starting with #
                if(line.trim().startsWith("#")) continue;

                //convert line into tokens
                String[] tokens = line.split(DELIMETER);

                User u = new User(Integer.parseInt(tokens[0]), tokens[1], tokens[2], tokens[3], tokens[4]);
                _users.add(u);
            }

            //close the reader
            br.close();
        }catch (Exception e) {
            _users = null;
            throw new RuntimeException(e);
        }
    }

    public static List<User> getUsers() {
        if (_users == null) Users.load();
        return _users;
    }

    public static boolean usernameExists(String username) {
        for(User u : _users) {
            if(u.getUsername().equals(username.trim())){
                return true;
            }
        }
        return false;
    }

    public static int getNextUserId() {
        if (_users == null) Users.load();

        List<Integer> ids = new ArrayList<>();

        for(User u : _users) {
            ids.add(u.getID());
        }

        return Collections.max(ids) + 1;
    }

    public static boolean checkPassword(String password, String cpassword) {
        if (!password.equals(cpassword)) {
            return false;
        }
        return true;
    }


    public static boolean store() {
        if(_users.isEmpty()) return true;

        try {
            File projectDir = new File(System.getProperty("user.dir"));

            BufferedWriter writer = Files.newBufferedWriter(Paths.get(projectDir + "/src/data/users.txt"));

            writer.write("#ID;Username;Password;Firstname,Lastname");
            writer.newLine();

            for(User u : _users) {
                String line = "";
                line += u.getID() + DELIMETER +
                        u.getUsername()  + DELIMETER +
                        u.getPassword() + DELIMETER +
                        u.getCPassword() + DELIMETER +
                        u.getFirstname() + DELIMETER +
                        u.getLastname() + DELIMETER;

                writer.write(line);
                writer.newLine();
            }
            writer.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static String getUserFirstname(int user_id) {
        if (_users == null) Users.load();

        for(User u : _users) {
            if(u.getID() == user_id) {
                return u.getFirstname();
            }
        }
        return "Unknown";
    }

    public static boolean loginUser(String username , String password) {
        LoggedUser = null;

        if(_users == null) Users.load();
        for(User u : _users) {
            if(u.getUsername().equals(username.trim())){
                if(u.getPassword().equals(u.getPassword().trim())){
                    LoggedUser = u;
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean registerUser(String username, String password, String firstname, String lastname) throws Exception {
        if (_users == null) Users.load();

        if (usernameExists(username)) {
            throw new Exception("This username is already exists");
        }
        User user = new User(getNextUserId(), username, password, firstname, lastname);
        _users.add(user);

        return store();
    }
}
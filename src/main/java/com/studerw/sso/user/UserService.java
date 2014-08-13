package com.studerw.sso.user;

import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.util.HashMap;

/**
 * User: studerw
 * Date: 8/12/14
 */
@Service
public class UserService {
    private static final Logger log = Logger.getLogger(UserService.class);
    final static String users_file = "com.studerw.sso.usersStream";

    @PostConstruct
    public void postConstruct(){
        File file = new File(SystemUtils.getJavaIoTmpDir(), users_file);
        file.deleteOnExit();
    }
    @PreDestroy
    public void preDestroy(){
        File file = new File(SystemUtils.getJavaIoTmpDir(), users_file);
        if (file.exists()){
            log.info("**** deleting file: " + file.getAbsolutePath());
            file.delete();
        }
    }

    public User getByUserName(String username) throws IOException, ClassNotFoundException {
        File usersFile = new File(SystemUtils.getJavaIoTmpDir(), users_file);
        if(!usersFile.exists()){
            return null;
        }

        HashMap<String, User> users = (HashMap<String, User>) SerializationUtils.deserialize(new FileInputStream(usersFile));
        return users.get(username);
    }

    public void saveUser(User user) throws FileNotFoundException {
        File usersFile = new File(SystemUtils.getJavaIoTmpDir(), users_file);
        HashMap<String, User> users;
        if (usersFile.exists()){
            users = (HashMap<String, User>) SerializationUtils.deserialize(new FileInputStream(usersFile));
        }
        else {
            users = new HashMap<String, User>();
        }
        if (users.get(user.getUsername()) != null){
            throw new IllegalArgumentException("user: " + user.getUsername() + "already exists");
        }
        users.put(user.getUsername(), user);
        SerializationUtils.serialize(users, new FileOutputStream(usersFile));
    }


}

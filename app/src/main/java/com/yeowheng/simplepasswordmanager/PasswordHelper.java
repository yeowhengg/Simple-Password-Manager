package com.yeowheng.simplepasswordmanager;

import java.security.SecureRandom;

public class PasswordHelper {

    private SecureRandom secureRandom;

    public PasswordHelper(){}

    public String GenerateSalt()  {
        secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);

        StringBuilder sb = new StringBuilder();

        for (byte aByte : salt) {
            sb.append(Integer.toString((aByte & 0xff) + 0x100, 16)
                    .substring(1));
        }

        return sb.toString();
    }

    public String HashPassword(String masterPassword, String salt){
        Integer hash = (masterPassword + salt).hashCode();
        return String.valueOf(hash);
    }


    public Boolean PasswordValidator(String userEnteredPassword, String retrievedHashedPassword, String retrieveSalt){
        return (userEnteredPassword + retrieveSalt).hashCode() == Integer.parseInt(retrievedHashedPassword);
    }



}

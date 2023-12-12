package com.ncrouch1.springserver.models;

import com.ncrouch1.springserver.constants.Constants;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;


public class CharacterInformation {
    private static final String characterDirectoryPath = Constants.characterDirectoryPath;
    private String name;
    private String role;
    private String affiliation;

    private CharacterInformation(String name, String role, String affiliation) {
        this.name = name;
        this.role = role;
        this.affiliation = affiliation;
    }

    public static CharacterInformation getCharacterInformation(String name) {
        File characterDirectory = new File(characterDirectoryPath);
        File[] characterFiles = characterDirectory.listFiles();
        for (File file : characterFiles) {
            String completePath = characterDirectoryPath + '/' + file.getName();
            if (file.getName().toLowerCase().contains(name.toLowerCase())) {
                String name_, role, affiliation = "";
                try {
                    String contents = new String(Files.readAllBytes(Paths.get(completePath)));
                    System.out.println(contents);
                    String[] lines = contents.split("\n");
                    name_ = lines[0].split(": ")[1];
                    role = lines[1].split(": ")[1];
                    affiliation = lines[2].split(": ")[1];
                    return new CharacterInformation(
                            name_,
                            role,
                            affiliation
                    );
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                    return null;
                }
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }
}

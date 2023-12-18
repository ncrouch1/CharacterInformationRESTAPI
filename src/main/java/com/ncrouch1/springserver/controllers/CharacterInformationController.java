package com.ncrouch1.springserver.controllers;

import com.google.gson.Gson;
import com.ncrouch1.springserver.constants.Constants;
import com.ncrouch1.springserver.datatypes.CharacterEntry;
import com.ncrouch1.springserver.datatypes.CharacterInformation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
public class CharacterInformationController {
    private static final String characterDirectoryPath = Constants.characterDirectoryPath;
    private static Gson gson = new Gson();
    @GetMapping("/CharacterInformation")
    public static CharacterInformation getCharacterInformation(
            @Validated @RequestBody String body
    ) {
        CharacterEntry entry = gson.fromJson(body, CharacterEntry.class);
        return CharacterInformation.getCharacterInformation(entry.name());
    }

    @PostMapping("/AddCharacter")
    public static @ResponseBody String addCharacterInformation(
            @Validated @RequestBody String body
    ) {
        CharacterInformation newCharacterInfo = gson.fromJson(body, CharacterInformation.class);
        File[] entries = new File(characterDirectoryPath).listFiles();
        if (entries == null)
            return "Not Ok";
        for (File _file: entries) {
            if (_file.getName().toLowerCase().contains(newCharacterInfo.getName().toLowerCase())) {
                return "Not Ok";
            }
        }
        try {
            File _newFile = new File(characterDirectoryPath, newCharacterInfo.getName() + ".txt");
            FileOutputStream fos = new FileOutputStream(_newFile, false);
            StringBuilder sb = new StringBuilder();
            String format = "%s: %s%n";
            sb.append(String.format(format, "name", newCharacterInfo.getName()));
            sb.append(String.format(format, "role", newCharacterInfo.getRole()));
            sb.append(String.format(format, "affiliation", newCharacterInfo.getAffiliation()));
            fos.write(sb.toString().getBytes());
            fos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return "Not Ok";
        }
        return "OK";
    }
}

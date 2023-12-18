package com.ncrouch1.springserver.datatypes;

public record CharacterEntry(String name) {
    public String getname() {
        return name;
    }
}

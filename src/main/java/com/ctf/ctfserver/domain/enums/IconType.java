package com.ctf.ctfserver.domain.enums;

public enum IconType {
    NONE("None"),
    SHIELD("Shield"),
    BUG("Bug"),
    CROWN("Crown"),
    CROSSHAIRS("Crosshairs"),
    BAN("Ban"),
    LIGHTNING("Lightning"),
    SKULL("Skull"),
    BRAIN("Brain"),
    CODE("Code"),
    COWBOY("Cowboy"),
    ANGRY("Angry");

    IconType (String iconName) {
        this.iconName = iconName;
    }

    private String iconName;

    public String getIconNAme() {
        return this.iconName;
    }
}

package com.mycompany.newtonssecondlawmotioncalculator;

public enum VectorType {
    NET_ACCELERATION("Net Acceleration"),
    NET_FORCE("Net Force"),
    FORCE("Force"),
    ACCELERATION("Acceleration");

    private final String name;

    VectorType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

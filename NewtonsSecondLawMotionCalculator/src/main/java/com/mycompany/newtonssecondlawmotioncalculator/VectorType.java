package com.mycompany.newtonssecondlawmotioncalculator;

public enum VectorType {
    ACCELERATION("Acceleration"),
    FORCE("Force");

    private final String name;

    VectorType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

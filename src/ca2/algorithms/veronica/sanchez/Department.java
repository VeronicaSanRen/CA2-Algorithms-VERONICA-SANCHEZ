/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca2.algorithms.veronica.sanchez;

/**
 *
 * @author dell
 */

    enum Department {
    INTENSIVE_CARE("Intensive Care"),
    EMERGENCY_DEPARTMENT("Emergency Department"),
    ORTHOPAEDICS("Orthopaedics"),
    NURSING("Nursing"),
    CARDIOLOGY("Cardiology"),
    RADIOLOGY("Radiology");

    private final String label;
    Department(String label) { this.label = label; }
    @Override public String toString() { return label; }

    public static Department fromString(String text) {
        for (Department d : Department.values()) {
            if (d.label.equalsIgnoreCase(text) || d.name().equalsIgnoreCase(text.replace(" ", "_"))) {
                return d;
            }
        }
        throw new IllegalArgumentException("Invalid Department: " + text);
    }
}

package de.hswt.hrm.common.ui.swt.table;

public enum Direction {
    ASCENDING(0),
    DESCENDING(0);
    
    private final int value;
    Direction(int value) { this.value = value; }
    public int getValue() { return value; }
}

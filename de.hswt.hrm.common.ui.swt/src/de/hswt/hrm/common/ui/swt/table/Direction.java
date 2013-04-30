package de.hswt.hrm.common.ui.swt.table;

import org.eclipse.swt.SWT;

public enum Direction {
    ASCENDING(SWT.UP),
    DESCENDING(SWT.DOWN);
    
    private final int value;
    Direction(int value) { this.value = value; }
    public int getValue() { return value; }
}

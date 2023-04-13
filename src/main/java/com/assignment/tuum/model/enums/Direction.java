package com.assignment.tuum.model.enums;

public enum Direction {
    IN,
    OUT;

    public static boolean contains(Direction d)
    {
        for(Direction Direction:values())
            if (Direction.name().equals(d))
                return true;
        return false;
    }
}

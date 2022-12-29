package fr.tartur.torchlines.commands.common;

public enum HeadDirection {
    EAST(-135, -44, 1, 0),
    SOUTH(-45, 44, 0, 1),
    WEST(45, 134, -1, 0),
    NORTH_FIRST(135, 180, 0, -1),
    NORTH_LAST(-180, -134, 0, -1);

    private final int min;
    private final int max;
    private final int xValue;
    private final int zValue;

    HeadDirection(int min, int max, int xValue, int zValue) {
        this.min = min;
        this.max = max;
        this.xValue = xValue;
        this.zValue = zValue;
    }

    public static HeadDirection getCorrespondingDirection(int direction) {
        for (HeadDirection headDirection : values()) {
            if (headDirection.min <= direction && headDirection.max >= direction) {
                return headDirection;
            }
        }

        // Unreachable.
        return HeadDirection.NORTH_FIRST;
    }

    public int getX() {
        return this.xValue;
    }

    public int getZ() {
        return this.zValue;
    }
}

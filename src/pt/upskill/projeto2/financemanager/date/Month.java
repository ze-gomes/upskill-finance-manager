package pt.upskill.projeto2.financemanager.date;

/**
 * @author upSkill 2020
 * <p>
 * ...
 */

public enum Month {
    NONE(0),
    JANUARY(1),
    FEBRUARY(2),
    MARCH(3),
    APRIL(4),
    MAY(5),
    JUNE(6),
    JULY(7),
    AUGUST(8),
    SEPTEMBER(9),
    OCTOBER(10),
    NOVEMBER(11),
    DECEMBER (12);

    private int value;

    Month(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

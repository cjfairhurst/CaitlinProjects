
package com.nwn.nwntools.PointCalculator;

/**
 *
 * @author Caitlin
 */
public class Ability {

    private String name;
    private int total;
    private int pointsSpent;
    private int offset;

    public Ability(String name) {
        this.name = name;
        this.offset = 0;
        this.total = 8;
    }

    public int checkCost(boolean isAddition) {
        if (isAddition) {
            if (total < 14) {
                return 1;
            } else if (total == 14 || total == 15) {
                return 2;
            } else if (total == 16 || total == 17) {
                return 3;
            } else if (total == 18 || total == 19) {
                return 4;
            } else if (total == 20 || total == 21) {
                return 5;
            } else {
                return 6;
            }
        } else {
            if (total <= 14) {
                return 1;
            } else if (total == 15 || total == 16) {
                return 2;
            } else if (total == 17 || total == 18) {
                return 3;
            } else if (total == 19 || total == 20) {
                return 4;
            } else if (total == 21 || total == 22) {
                return 5;
            } else {
                return 6;
            }
        }
    }

    public int checkBonus() {
        if (getAdjustedTotal() == 4 || getAdjustedTotal() == 5) {
            return -3;
        } else if (getAdjustedTotal() == 6 || getAdjustedTotal() == 7) {
            return -2;
        } else if (getAdjustedTotal() == 8 || getAdjustedTotal() == 9) {
            return -1;
        } else if (getAdjustedTotal() == 10 || getAdjustedTotal() == 11) {
            return 0;
        } else if (getAdjustedTotal() == 12 || getAdjustedTotal() == 13) {
            return 1;
        } else if (getAdjustedTotal() == 14 || getAdjustedTotal() == 15) {
            return 2;
        } else if (getAdjustedTotal() == 16 || getAdjustedTotal() == 17) {
            return 3;
        } else if (getAdjustedTotal() == 18 || getAdjustedTotal() == 19) {
            return 4;
        } else if (getAdjustedTotal() == 20 || getAdjustedTotal() == 21) {
            return 5;
        } else if (getAdjustedTotal() == 22 || getAdjustedTotal() == 23) {
            return 6;
        } else if (getAdjustedTotal() == 24 || getAdjustedTotal() == 25) {
            return 7;
        } else if (getAdjustedTotal() == 26 || getAdjustedTotal() == 27) {
            return 8;
        } else {
            return 9;
        }
    }

    public int addPoint(int totalPoints) {
        int cost = checkCost(true);

        if (totalPoints - cost < 0) {
            return totalPoints;
        } else {
            this.pointsSpent = (pointsSpent + cost);
            calculateTotal();
            return totalPoints - cost;
        }

    }

    public int subtractPoint(int totalPoints) {
        int cost = checkCost(false);
        if (total <= 8) {
            return totalPoints;
        } else {
            this.pointsSpent = (pointsSpent - cost);
            return totalPoints + cost;
        }
    }

    public void calculateTotal() {
        if (pointsSpent == 0) {
            total = 8;
        } else if (pointsSpent == 1) {
            total = 9;
        } else if (pointsSpent == 2) {
            total = 10;
        } else if (pointsSpent == 3) {
            total = 11;
        } else if (pointsSpent == 4) {
            total = 12;
        } else if (pointsSpent == 5) {
            total = 13;
        } else if (pointsSpent == 6) {
            total = 14;
        } else if (pointsSpent == 8) {
            total = 15;
        } else if (pointsSpent == 10) {
            total = 16;
        } else if (pointsSpent == 13) {
            total = 17;
        } else if (pointsSpent == 16) {
            total = 18;
        } else if (pointsSpent == 20) {
            total = 19;
        } else if (pointsSpent == 24) {
            total = 20;
        } else if (pointsSpent == 29) {
            total = 21;
        }
    }

    public int getAdjustedTotal() {
        calculateTotal();
        return (total + offset);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPointsSpent() {
        return pointsSpent;
    }

    public void setPointsSpent(int pointsSpent) {
        this.pointsSpent = pointsSpent;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

}

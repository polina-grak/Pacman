package pacman.model;

import pacman.enums.ItemDirection;

public class PathNode implements Comparable<PathNode> {
    public int row;
    public int col;
    public PathNode parent;
    public double gCost;
    public double hCost;
    public ItemDirection directionFromParent;

    public PathNode(int row, int col, PathNode parent, ItemDirection directionFromParent, double gCost, double hCost) {
        this.row = row;
        this.col = col;
        this.parent = parent;
        this.directionFromParent = directionFromParent;
        this.gCost = gCost;
        this.hCost = hCost;
    }
    public double getFCost() {
        return gCost + hCost;
    }
    public int compareTo(PathNode other) {
        int fCostCompare = Double.compare(this.getFCost(), other.getFCost());
        if (fCostCompare == 0) {
            return Double.compare(this.hCost, other.hCost);
        }
        return fCostCompare;
    }
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathNode pathNode = (PathNode) o;
        return row == pathNode.row && col == pathNode.col;
    }
    public int hashCode() {
        return 31 * row + col;
    }
    public String toString() {
        return "PathNode{" +
                "row=" + row +
                ", col=" + col +
                ", g=" + gCost +
                ", h=" + hCost +
                ", f=" + getFCost() +
                '}';
    }



}

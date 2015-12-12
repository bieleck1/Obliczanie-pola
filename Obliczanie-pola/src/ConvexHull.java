
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author abc
 */
public class ConvexHull {

    private ArrayList<Point> otherPoints;

    public ConvexHull() {
        this.otherPoints = new ArrayList<>();
    }

    public ArrayList<Point> designateConvexHull(ArrayList<Point> otherPoints) {
        if (otherPoints.size() < 4) {
            return otherPoints;
        } else {
            ArrayList<Point> hullPoints = new ArrayList<>();

            int l = 0;
            for (int i = 1; i < otherPoints.size(); i++) {
                if (otherPoints.get(i).getX() < otherPoints.get(l).getX()) {
                    l = i;
                }
            }

            int left = l;
            int right;

            do {
                hullPoints.add(otherPoints.get(left));

                right = (left + 1) % otherPoints.size();

                for (int i = 0; i < otherPoints.size(); i++) {
                    if (checkPoint(otherPoints.get(left), otherPoints.get(i), otherPoints.get(right))) {
                        right = i;
                    }
                }

                left = right;
            } while (left != 0);
            
            return hullPoints;
        }
    }

    private boolean checkPoint(Point p, Point q, Point r) {
        int value = (q.getY() - p.getY()) * (r.getX() - p.getX()) - (q.getX() - p.getX()) * (r.getY() - p.getY());

        if (value >= 0) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<Point> getOtherPoints() {
        return this.otherPoints;
    }
}

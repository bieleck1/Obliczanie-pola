
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

    public ArrayList<Point> designateConvexHull(ArrayList<Point> otherPoints) {
        if (otherPoints.size() < 4) {
            return otherPoints;
        } else {
            return mergeConvexHulls(designateConvexHull(setHull1(otherPoints)), designateConvexHull(setHull2(otherPoints)));
        }
    }

    private ArrayList<Point> mergeConvexHulls(ArrayList<Point> hull1, ArrayList<Point> hull2) {
        ArrayList<Point> hullPoints = new ArrayList<>();
        int PointWithMaxYofHull1 = findPointWithMaxY(hull1, 1);
        int PointWithMinYofHull1 = findPointWithMinY(hull1, 1);
        int PointWithMaxYofHull2 = findPointWithMaxY(hull2, 2);
        int PointWithMinYofHull2 = findPointWithMinY(hull2, 2);

        for (int i = 0; i < hull1.size(); i++) {
            if (i == PointWithMaxYofHull1 || i == PointWithMinYofHull1) {
                hullPoints.add(hull1.get(i));
            } else if (hull1.get(i).getX() < hull1.get(PointWithMaxYofHull1).getX() && hull1.get(i).getX() < hull1.get(PointWithMinYofHull1).getX()) {
                hullPoints.add(hull1.get(i));
            }
        }

        for (int i = 0; i < hull2.size(); i++) {
            if (i == PointWithMaxYofHull2 || i == PointWithMinYofHull2) {
                hullPoints.add(hull2.get(i));
            } else if (hull2.get(i).getX() > hull2.get(PointWithMaxYofHull2).getX() && hull2.get(i).getX() > hull2.get(PointWithMinYofHull2).getX()) {
                hullPoints.add(hull2.get(i));
            }
        }

        return hullPoints;
    }

    private ArrayList<Point> setHull1(ArrayList<Point> otherPoints) {
        ArrayList<Point> hull1 = new ArrayList<>();

        for (int i = 0; i < otherPoints.size() / 2; i++) {
            hull1.add(otherPoints.get(i));
        }

        return hull1;
    }

    private ArrayList<Point> setHull2(ArrayList<Point> otherPoints) {
        ArrayList<Point> hull2 = new ArrayList<>();

        for (int i = otherPoints.size() / 2; i < otherPoints.size(); i++) {
            hull2.add(otherPoints.get(i));
        }

        return hull2;
    }

    private int findPointWithMaxY(ArrayList<Point> hull, int numberOfHull) {
        int PointWithMaxY = 0;

        for (int i = 1; i < hull.size(); i++) {
            if (hull.get(PointWithMaxY).getY() < hull.get(i).getY()) {
                PointWithMaxY = i;
            } else if (hull.get(PointWithMaxY).getY() == hull.get(i).getY()) {
                if (numberOfHull == 1) {
                    if (hull.get(PointWithMaxY).getX() < hull.get(i).getX()) {
                        PointWithMaxY = i;
                    }
                }

                if (numberOfHull == 2) {
                    if (hull.get(PointWithMaxY).getX() > hull.get(i).getX()) {
                        PointWithMaxY = i;
                    }
                }
            }
        }

        return PointWithMaxY;
    }

    private int findPointWithMinY(ArrayList<Point> hull, int numberOfHull) {
        int PointWithMinY = 0;

        for (int i = 1; i < hull.size(); i++) {
            if (hull.get(PointWithMinY).getY() > hull.get(i).getY()) {
                PointWithMinY = i;
            } else if (hull.get(PointWithMinY).getY() == hull.get(i).getY()) {
                if (numberOfHull == 1) {
                    if (hull.get(PointWithMinY).getX() < hull.get(i).getX()) {
                        PointWithMinY = i;
                    }
                }

                if (numberOfHull == 2) {
                    if (hull.get(PointWithMinY).getX() > hull.get(i).getX()) {
                        PointWithMinY = i;
                    }
                }
            }
        }

        return PointWithMinY;
    }
}

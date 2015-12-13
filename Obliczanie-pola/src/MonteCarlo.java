
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
public class MonteCarlo {

    private ArrayList<Point> hullPoints;
    private ArrayList<Point> randomPoints;
    private PointsGenerator pointsGenerator;
    private int maxX;
    private int maxY;
    private int minX;
    private int minY;
    private int tmpX;
    private int tmpY;
    private double squareField;

    public MonteCarlo(ArrayList<Point> hullPoints) {
        this.hullPoints = hullPoints;
        this.randomPoints = new ArrayList<>();
        this.pointsGenerator = new PointsGenerator();
        this.maxX = hullPoints.get(0).getX();
        this.maxY = hullPoints.get(0).getY();
        this.minX = hullPoints.get(0).getX();
        this.minY = hullPoints.get(0).getY();
        this.squareField = 0;
    }

    public double calculateField(int numberOfPoints) {
        int insidePoints = 0;

        calculateSquareField();
        this.randomPoints = this.pointsGenerator.generate(numberOfPoints, this.maxX, this.minX, this.maxY, this.minY);
        int rayX = this.maxX + 1;

        for (int i = 0; i < numberOfPoints; i++) {
            int rayY = this.randomPoints.get(i).getY();
            int intersects = 0;
            int m = 1;
            
            for (int k = 0; k < this.hullPoints.size(); k++) {
                if (ifOnHull(this.hullPoints.get(k).getX(), this.hullPoints.get(k).getY(), this.hullPoints.get((k + 1) % this.hullPoints.size()).getX(),
                        this.hullPoints.get((k + 1) % this.hullPoints.size()).getY(), this.randomPoints.get(i).getX(), this.randomPoints.get(i).getY()) == 1) {
                    insidePoints++;
                    m = 0;
                    k = this.hullPoints.size();
                } else if (ifIntersectLine(this.hullPoints.get((k - 1 + this.hullPoints.size()) % this.hullPoints.size()).getX(),
                        this.hullPoints.get((k - 1 + this.hullPoints.size()) % this.hullPoints.size()).getY(), this.hullPoints.get(k).getX(), this.hullPoints.get(k).getY(),
                        this.hullPoints.get((k + 1) % this.hullPoints.size()).getX(), this.hullPoints.get((k + 1) % this.hullPoints.size()).getY(),
                        this.hullPoints.get((k + 2) % this.hullPoints.size()).getX(), this.hullPoints.get((k + 2) % this.hullPoints.size()).getY(),
                        this.randomPoints.get(i).getX(), this.randomPoints.get(i).getY(), rayX, rayY) == 1) {
                    m = 1;
                    intersects++;
                }
            }

            if ((intersects % 2) == 1 && m == 1) {
                insidePoints++;
            }
        }

        double hullField = this.squareField * insidePoints / numberOfPoints;

        return hullField;
    }

    private int ifOnHull(int xx, int xy, int yx, int yy, int zx, int zy) {
        int det = xx * yy + yx * zy + zx * xy - zx * yy - xx * zy - yx * xy;
        if (det != 0) {
            return 0;
        } else {
            if ((Math.min(xx, yx) <= zx) && (zx <= Math.max(xx, yx))
                    && (Math.min(xy, yy) <= zy) && (zy <= Math.max(xy, yy))) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    //Wyznacznik macierzy kwadratowej stopnia 3.
    private int det(int xx, int xy, int yx, int yy, int zx, int zy) {
        return (xx * yy + yx * zy + zx * xy - zx * yy - xx * zy - yx * xy);
    }

    private int ifIntersectLine(int prevX, int prevY, int ax, int ay, int bx, int by, int nextX, int nextY, int px, int py, int rx, int ry) {
        if ((ifOnHull(px, py, rx, ry, ax, ay) == 0)
                && (ifOnHull(px, py, rx, ry, bx, by) == 0)) {
            //p�lprosta nie przecina odcinka |AB| w koncach
            if ((Math.signum(det(px, py, rx, ry, ax, ay)) != Math.signum(det(px, py, rx, ry, bx, by)))
                    && (Math.signum(det(ax, ay, bx, by, px, py)) != Math.signum(det(ax, ay, bx, by, rx, ry)))) {
                return 1;
            } else {
                return 0;
            }
        } else {
            //			do p�lprostej nalezy przynajmniej jeden koniec odcinka |AB|
            if ((ifOnHull(px, py, rx, ry, ax, ay) == 1)
                    && (ifOnHull(px, py, rx, ry, bx, by) == 1)) {
                if (Math.signum(det(px, py, rx, ry, prevX, prevY)) == Math.signum(det(px, py, rx, ry, nextX, nextY))
                        && Math.signum(det(px, py, rx, ry, prevX, prevY)) != 0) {
                    return 0;
                } else {
                    return 1;
                }
            } else {
                if ((ifOnHull(px, py, rx, ry, prevX, prevY) == 1)
                        || (ifOnHull(px, py, rx, ry, nextX, nextY) == 1)) {
                    return 0;
                } else {
                    //polprosta zawiera tylko wierzcholek
                    if (ifOnHull(px, py, rx, ry, bx, by) == 1) {
                        this.tmpX = ax;
                        this.tmpY = ay;
                        return 0;
                    }
                    if (ifOnHull(px, py, rx, ry, ax, ay) == 1) {
                        if (Math.signum(det(px, py, rx, ry, this.tmpX, this.tmpY)) == Math.signum(det(px, py, rx, ry, bx, by))
                                && Math.signum(det(px, py, rx, ry, this.tmpX, this.tmpY)) != 0) {
                            return 0;
                        } else {
                            return 1;
                        }
                    }
                }
            }
        }
        return 0;
    }
    /*
     private boolean ifOnHull(int pX, int pY, int qX, int qY, int rX, int rY) {
     if (det(pX, pY, qX, qY, rX, rY) != 0) {
     return false;
     } else {
     if ((Math.min(pX, qX) <= rX) && (rX <= Math.max(pX, qX))
     && (Math.min(pY, qY) <= rY) && (rY <= Math.max(pY, qY))) {
     return true;
     } else {
     return false;
     }
     }
     }

     private boolean ifIntersectLine(int oX, int oY, int pX, int pY, int qX, int qY, int sX, int sY, int rX, int rY, int rayX, int rayY) {
     if ((ifOnHull(rX, rY, rayX, rayY, pX, pY) == false) && (ifOnHull(rX, rY, rayX, rayY, qX, qY) == false)) {
     //pólprosta nie przecina odcinka |AB| w koncach
     if ((Math.signum(det(rX, rY, rayX, rayY, pX, pY)) != Math.signum(det(rX, rY, rayX, rayY, qX, qY)))
     && (Math.signum(det(pX, pY, qX, qY, rX, rY)) != Math.signum(det(pX, pY, qX, qY, rayX, rayY)))) {
     return true;
     } else {
     return false;
     }
     } else {
     //          do pólprostej nalezy przynajmniej jeden koniec odcinka |AB|
     if ((ifOnHull(rX, rY, rayX, rayY, pX, pY) == true)
     && (ifOnHull(rX, rY, rayX, rayY, qX, qY) == true)) {
     if (Math.signum(det(rX, rY, rayX, rayY, oX, oY)) == Math.signum(det(rX, rY, rayX, rayY, sX, sY))
     && Math.signum(det(rX, rY, rayX, rayY, oX, oY)) != 0) {
     return false;
     } else {
     return true;
     }
     } else {
     if ((ifOnHull(rX, rY, rayX, rayY, oX, oY) == true)
     || (ifOnHull(rX, rY, rayX, rayY, sX, sY) == true)) {
     return false;
     } else {
     //polprosta zawiera tylko wierzcholek
     if (ifOnHull(rX, rY, rayX, rayY, qX, qY) == true) {
     this.tmpX = pX;
     this.tmpY = pY;
     return false;
     }
     if (ifOnHull(rX, rY, rayX, rayY, pX, pY) == true) {
     if (Math.signum(det(rX, rY, rayX, rayY, this.tmpX, this.tmpY)) == Math.signum(det(rX, rY, rayX, rayY, qX, qY))
     && Math.signum(det(rX, rY, rayX, rayY, this.tmpX, this.tmpY)) != 0) {
     return false;
     } else {
     return true;
     }
     }
     }
     }
     }
     return false;
     }

     private int det(int pX, int pY, int qX, int qY, int rX, int rY) {
     return (pX * pY + qX * rY + rX * pY - rX * qY - pX * rY - qX * pY);
     }*/

    private void calculateSquareField() {
        for (int i = 1; i < this.hullPoints.size(); i++) {
            if (this.maxX < this.hullPoints.get(i).getX()) {
                this.maxX = this.hullPoints.get(i).getX();
            }

            if (this.minX > this.hullPoints.get(i).getX()) {
                this.minX = this.hullPoints.get(i).getX();
            }

            if (this.maxY < this.hullPoints.get(i).getY()) {
                this.maxY = this.hullPoints.get(i).getY();
            }

            if (this.minY > this.hullPoints.get(i).getY()) {
                this.minY = this.hullPoints.get(i).getY();
            }
        }

        int a = this.maxX - this.minX;
        int b = this.maxY - this.minY;
        this.squareField = a * b;
    }
}

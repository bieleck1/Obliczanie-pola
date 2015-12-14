
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
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Point> points = new ArrayList<>();

        Point p1 = new Point(10, 14);
        Point p2 = new Point(20, 39);
        Point p3 = new Point(2, 20);
        Point p4 = new Point(49, 3);
        Point p5 = new Point(33, 13);
        Point p6 = new Point(23, 12);
        Point p7 = new Point(23, 4);
        Point p8 = new Point(5, 10);

        points.add(p1);
        points.add(p2);
        points.add(p3);
        points.add(p4);
        points.add(p5);
        points.add(p6);
        points.add(p7);
        points.add(p8);
        
        System.out.println("Punkty przed posortowaniem:");
        
        for (int i = 0; i < points.size(); i++) {
            System.out.println(points.get(i).getX() + " " + points.get(i).getY());
        }

        points.sort(null);
        
        System.out.println();
        System.out.println("Punkty po posortowaniu:");

        for (int i = 0; i < points.size(); i++) {
            System.out.println(points.get(i).getX() + " " + points.get(i).getY());
        }

        System.out.println();

        ConvexHull convexHull = new ConvexHull();

        ArrayList<Point> hullPoints = convexHull.designateConvexHull(points);
        
        System.out.println("Wierzchołki figury:");

        for (int i = 0; i < hullPoints.size(); i++) {
            System.out.println(hullPoints.get(i).getX() + " " + hullPoints.get(i).getY());
        }
        
        MonteCarlo monteCarlo = new MonteCarlo(hullPoints);
        
        double field = monteCarlo.calculateField(100000);
        
        System.out.println("Pole figury: " + field);
    }

}

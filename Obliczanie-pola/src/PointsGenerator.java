
import java.util.ArrayList;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author abc
 */
public class PointsGenerator {

    public ArrayList<Point> generate(int numberOfPoints, int maxX, int minX, int maxY, int minY) {

        ArrayList<Point> points = new ArrayList<>();

        Random randomGenerator = new Random();
        
        maxX -= minX;
        maxY -= minY;

        for (int i = 0; i < numberOfPoints; i++) {
            int x = randomGenerator.nextInt(maxX) + minX + randomGenerator.nextInt(2);
            int y = randomGenerator.nextInt(maxY) + minY + randomGenerator.nextInt(2);
            Point p = new Point(x, y);
            points.add(p);
        }
        return points;
    }
}

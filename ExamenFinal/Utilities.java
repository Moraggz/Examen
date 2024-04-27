package examenFinal;
import java.awt.*;

class Utilities {
    public static Point calculateGridPosition(Point mousePoint, int cellSize) {
        int x = mousePoint.x / cellSize;
        int y = mousePoint.y / cellSize;
        return new Point(x, y);
    }
}

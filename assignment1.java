import java.util.Scanner;

class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double distanceTo(Point other) {
        return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2));
    }

    public boolean isInsideTriangle(Triangle triangle) {
        double area = 0.5 * (-triangle.getPoint(1).getY() * triangle.getPoint(2).getX() + triangle.getPoint(0).getY() * (-triangle.getPoint(1).getX() + triangle.getPoint(2).getX()) + triangle.getPoint(0).getX() * (triangle.getPoint(1).getY() - triangle.getPoint(2).getY()) + triangle.getPoint(1).getX() * triangle.getPoint(2).getY() - triangle.getPoint(2).getX() * triangle.getPoint(1).getY());

        double point1 = 1 / (2 * area) * (triangle.getPoint(0).getY() * triangle.getPoint(2).getX() - triangle.getPoint(0).getX() * triangle.getPoint(2).getY() + (triangle.getPoint(2).getY() - triangle.getPoint(0).getY()) * getX() + (triangle.getPoint(0).getX() - triangle.getPoint(2).getX()) * getY());
        double point2 = 1 / (2 * area) * (triangle.getPoint(0).getX() * triangle.getPoint(1).getY() - triangle.getPoint(0).getY() * triangle.getPoint(1).getX() + (triangle.getPoint(0).getY() - triangle.getPoint(1).getY()) * getX() + (triangle.getPoint(1).getX() - triangle.getPoint(0).getX()) * getY());
        double mainpoint = 1 - point1 - point2;

        return point1 > 0 && point2 > 0 && mainpoint > 0;
    }
}

class Triangle {
    private Point[] vertices;

    public Triangle(Point[] vertices) {
        if (vertices.length != 3) {
            throw new IllegalArgumentException("A triangle can only have exactly 3 vertices.");
        }
        this.vertices = vertices;
    }

    public Point getPoint(int index) {
        return vertices[index];
    }

    public double calculatePerimeter() {
        double perimeter = 0.0;
        for (int i = 0; i < 2; i++) {
            perimeter += vertices[i].distanceTo(vertices[i + 1]);
        }
        perimeter += vertices[2].distanceTo(vertices[0]);
        return perimeter;
    }

    public String determineType() {
        double[] sideLengths = new double[3];
        for (int i = 0; i < 3; i++) {
            sideLengths[i] = vertices[i].distanceTo(vertices[(i + 1) % 3]);
        }

        boolean isEquilateral = (sideLengths[0] == sideLengths[1]) && (sideLengths[1] == sideLengths[2]);
        boolean isIsosceles = (sideLengths[0] == sideLengths[1] || sideLengths[1] == sideLengths[2] || sideLengths[0] == sideLengths[2]);

        if (isEquilateral) {
            return "It is Equilateral Triangle";
        } else if (isIsosceles) {
            return "It is Isosceles Triangle";
        } else {
            return "Unknown Triangle";
        }
    }

    public double calculateArea() {
        double a = vertices[0].distanceTo(vertices[1]);
        double b = vertices[1].distanceTo(vertices[2]);
        double c = vertices[2].distanceTo(vertices[0]);
        double s = (a + b + c) / 2;
        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
    }
}

public class assignment1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of triangles: ");
        int numTriangles = scanner.nextInt();

        Triangle[] triangles = new Triangle[numTriangles];

        for (int i = 0; i < numTriangles; i++) {
            Point[] vertices = new Point[3];

            System.out.println("Enter the coordinates (x, y) for the three vertices of Triangle " + (i + 1) + ":");
            for (int j = 0; j < 3; j++) {
                System.out.print("Vertex " + (j + 1) + ": ");
                double x = scanner.nextDouble();
                double y = scanner.nextDouble();
                vertices[j] = new Point(x, y);
            }

            triangles[i] = new Triangle(vertices);
            double perimeter = triangles[i].calculatePerimeter();
            String type = triangles[i].determineType();
            double area = triangles[i].calculateArea();

            System.out.println("Triangle " + (i + 1) + ":");
            System.out.println("Perimeter: " + perimeter);
            System.out.println("Type: " + type);
            System.out.println("Area: " + area);

            System.out.print("Enter the x-coordinate of a point to check if it's inside the triangle: ");
            double pointX = scanner.nextDouble();
            System.out.print("Enter the y-coordinate of the point to check if it's inside the triangle: ");
            double pointY = scanner.nextDouble();

            Point testPoint = new Point(pointX, pointY);
            boolean isInside = testPoint.isInsideTriangle(triangles[i]);

            if (isInside) {
                System.out.println("The point is inside the triangle.");
            } else {
                System.out.println("The point is outside the triangle.");
            }
        }

        scanner.close();
    }
}


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D.Double;

/**
 * Фабрика створення геометричних фігур із заданим типом,
 * товщиною обведення та заливкою.
 *
 * Тип кодується двозначним числом:
 * - перша цифра — форма (1 — трипроменева зірка, 3 — п’ятилучна зірка,
 *   5 — квадрат, 7 — рівносторонній трикутник, 9 — дуга);
 * - друга цифра — стиль обведення/заливки
 *   (1 — стандартне обведення, 4 — товсте обведення,
 *    7 — градієнт, 8 — суцільне заповнення).
 */
public class ShapeFactory {
    /** Поточна фігура. */
    public Shape shape;
    /** Товщина обведення фігури. */
    public BasicStroke stroke = new BasicStroke(3.0f);
    /** Об’єкт Paint для заливки або градієнту. */
    public Paint paint;
    /** Базова ширина фігури. */
    public int width = 25;
    /** Базова висота фігури. */
    public int height = 25;

    /**
     * Створює фабрику для фігур зі стилем обведення/заливки,
     * визначеним кодом {@code shape_type}.
     *
     * @param shape_type код комбінованого типу фігури та стилю
     * @throws Error якщо код не підтримується
     */
    public ShapeFactory(int shape_type) {
        switch (shape_type / 10) {
            case 1:
                this.shape = createStar(3, new Point(0, 0), width/2.0, width/2.0);
                break;
            case 3:
                this.shape = createStar(5, new Point(0, 0), width/2.0, width/4.0);
                break;
            case 5:
                this.shape = new Double(-width/2.0, -height/2.0, width, height);
                break;
            case 7:
                GeneralPath path = new GeneralPath();
                double tmpH = Math.sqrt(2)/2 * height;
                path.moveTo(-width/2.0, -tmpH);
                path.lineTo(0, -tmpH);
                path.lineTo(width/2.0, tmpH);
                path.closePath();
                this.shape = path;
                break;
            case 9:
                this.shape = new java.awt.geom.Arc2D.Double(
                    -width/2.0, -height/2.0, width, height, 30, 300, java.awt.geom.Arc2D.OPEN);
                break;
            default:
                throw new Error("Тип фігури не підтримується: " + shape_type);
        }

        switch (shape_type % 10) {
            case 1:
                this.stroke = new BasicStroke(3.0f);
                break;
            case 4:
                this.stroke = new BasicStroke(7.0f);
                break;
            case 7:
                this.paint = new GradientPaint(-width, -height, Color.white, width, height, Color.gray, true);
                break;
            case 8:
                this.paint = Color.red;
                break;
            default:
                throw new Error("Стиль обведення/заливки не підтримується: " + shape_type);
        }
    }

    /**
     * Створює зірку з вказаною кількістю променів.
     *
     * @param arms   кількість променів
     * @param center центр зірки
     * @param rOuter радіус до зовнішніх вершин
     * @param rInner радіус до внутрішніх точок
     * @return фігура-зірка
     */
    private static Shape createStar(int arms, Point center, double rOuter, double rInner) {
        double angle = Math.PI / arms;
        GeneralPath path = new GeneralPath();
        for (int i = 0; i < 2 * arms; i++) {
            double r = (i % 2 == 0) ? rOuter : rInner;
            java.awt.geom.Point2D.Double p = new java.awt.geom.Point2D.Double(
                center.x + Math.cos(i * angle) * r,
                center.y + Math.sin(i * angle) * r
            );
            if (i == 0) {
                path.moveTo(p.x, p.y);
            } else {
                path.lineTo(p.x, p.y);
            }
        }
        path.closePath();
        return path;
    }
}

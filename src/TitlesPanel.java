import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Панель для анімації фігурних узорів.
 */
public class TitlesPanel extends JPanel implements ActionListener {
    /** Контекст малювання. */
    private Graphics2D g2d;
    /** Таймер анімації. */
    private Timer animation;
    /** Прапорець готовності до нового кадру. */
    private boolean isDone = true;
    /** Поточний кут обертання (градуси). */
    private int startAngle = 0;
    /** Код типу фігури для {@link ShapeFactory}. */
    private int shapeCode;

    /**
     * Конструктор, що встановлює код фігури та запускає таймер.
     *
     * @param shapeCode код типу фігури
     */
    public TitlesPanel(int shapeCode) {
        this.shapeCode = shapeCode;
        animation = new Timer(50, this);
        animation.setInitialDelay(50);
        animation.start();
    }

    /**
     * Виконується при спрацьовуванні таймера.
     * Якщо попередній кадр завершений, викликає {@link #repaint()}.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isDone) {
            repaint();
        }
    }

    /**
     * Основна логіка малювання:
     * налаштування рендерингу, обчислення розмірів,
     * створення фігур та їхнє обертання в сітці.
     *
     * @param g контекст для малювання
     */
    private void doDrawing(Graphics g) {
        isDone = false;
        g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);

        Dimension size = getSize();
        Insets insets = getInsets();
        int w = size.width  - insets.left - insets.right;
        int h = size.height - insets.top  - insets.bottom;

        ShapeFactory factory = new ShapeFactory(shapeCode);
        g2d.setStroke(factory.stroke);
        g2d.setPaint(factory.paint);

        double angle = startAngle;
        startAngle = (startAngle + 1) % 360;
        double step = 90.0 / (w / (factory.width * 1.5));

        for (int y = factory.height; y < h; y += factory.height * 1.5) {
            for (int x = factory.width; x < w; x += factory.width * 1.5) {
                angle = (angle + step) % 360;
                AffineTransform tx = new AffineTransform();
                tx.translate(x, y);
                tx.rotate(Math.toRadians(angle));
                g2d.draw(tx.createTransformedShape(factory.shape));
            }
        }

        isDone = true;
    }

    /**
     * Перекриває {@link JPanel#paintComponent(Graphics)} для виклику
     * методa {@link #doDrawing(Graphics)}.
     *
     * @param g контекст для малювання
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
}

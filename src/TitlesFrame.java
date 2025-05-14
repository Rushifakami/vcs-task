import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Головне вікно додатку для відображення анімованих фігур.
 */
public class TitlesFrame extends JFrame {
    /**
     * Конструктор, що ініціалізує інтерфейс користувача.
     */
    public TitlesFrame() {
        initUI();
    }

    /**
     * Налаштовує параметри вікна:
     * заголовок, дію при закритті, додавання панелі,
     * розмір та розташування.
     */
    private void initUI() {
        setTitle("Криві фігури");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new TitlesPanel(37));
        setSize(350, 350);
        setLocationRelativeTo((Component) null);
    }

    /**
     * Точка входу програми.
     * Запускає створення і показ вікна в потоці EDT.
     *
     * @param args параметри командного рядка (не використовуються)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TitlesFrame frame = new TitlesFrame();
            frame.setVisible(true);
        });
    }
}

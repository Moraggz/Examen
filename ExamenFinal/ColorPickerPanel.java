package examenFinal;
import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class ColorPickerPanel extends JPanel implements Serializable {
    private static final long serialVersionUID = 1L;
    private ColorSelectionListener colorSelectionListener;
    private JLabel colorLabel;
    private JSlider sliderRed;
    private JSlider sliderGreen;
    private JSlider sliderBlue;

    public ColorPickerPanel() {
        setupUI();
    }

    private void setupUI() {
        setLayout(new GridLayout(0, 1));

        JButton[] colorButtons = {
            createColorButton(Color.BLACK),
            createColorButton(Color.WHITE),
            createColorButton(Color.RED),
            createColorButton(Color.GREEN),
            createColorButton(Color.BLUE),
            createColorButton(Color.YELLOW),
            createColorButton(Color.ORANGE),
            createColorButton(Color.PINK),
            createColorButton(Color.CYAN),
            createColorButton(Color.MAGENTA)
        };

        for (JButton button : colorButtons) {
            add(button);
        }

        sliderRed = createColorSlider("Red", 0, 255);
        sliderGreen = createColorSlider("Green", 0, 255);
        sliderBlue = createColorSlider("Blue", 0, 255);
        add(sliderRed);
        add(sliderGreen);
        add(sliderBlue);

        colorLabel = new JLabel("Color Seleccionado");
        colorLabel.setOpaque(true);
        colorLabel.setPreferredSize(new Dimension(100, 30));
        colorLabel.setBackground(Color.BLACK);
        add(colorLabel);
    }

    private JButton createColorButton(Color color) {
        JButton colorButton = new JButton();
        colorButton.setBackground(color);
        colorButton.setPreferredSize(new Dimension(30, 30));
        colorButton.addActionListener(e -> {
            if (colorSelectionListener != null) {
                colorSelectionListener.colorSelected(color);
                colorLabel.setBackground(color);
            }
        });
        return colorButton;
    }

    private JSlider createColorSlider(String label, int min, int max) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, min);
        slider.setMajorTickSpacing((max - min) / 4);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(e -> {
            int r = sliderRed.getValue();
            int g = sliderGreen.getValue();
            int b = sliderBlue.getValue();
            Color color = new Color(r, g, b);
            if (colorSelectionListener != null) {
                colorSelectionListener.colorSelected(color);
                colorLabel.setBackground(color); 
            }
        });
        return slider;
    }

    public void addColorSelectionListener(ColorSelectionListener listener) {
        this.colorSelectionListener = listener;
    }

    public interface ColorSelectionListener {
        void colorSelected(Color color);
    }
}

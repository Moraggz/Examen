package examenFinal;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class MainWindow extends JFrame implements Serializable {
    private static final long serialVersionUID = 1L;
    private CanvasPanel canvasPanel;
    private ColorPickerPanel colorPickerPanel;
    private JTextField gridSizeTextField;

    public MainWindow() {
        setTitle("Pixel Art Creator");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setupLayout();
        initializeComponents();
        registerListeners();
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        JToolBar toolBar = new JToolBar();
        JButton saveButton = new JButton("Guardar Imagen");
        saveButton.addActionListener(e -> saveCanvasImage());
        toolBar.add(saveButton);

        JButton loadButton = new JButton("Cargar Imagen");
        loadButton.addActionListener(e -> loadCanvasImage());
        toolBar.add(loadButton);

        JButton clearButton = new JButton("Limpiar Canvas");
        clearButton.addActionListener(e -> clearCanvas());
        toolBar.add(clearButton);

        add(toolBar, BorderLayout.NORTH);

        canvasPanel = new CanvasPanel();
        add(canvasPanel, BorderLayout.CENTER);

        colorPickerPanel = new ColorPickerPanel();
        add(colorPickerPanel, BorderLayout.EAST);
        
        JPanel customizationPanel = new JPanel();
        customizationPanel.setLayout(new GridLayout(0, 2));

        JLabel gridSizeLabel = new JLabel("Grid Size:");
        gridSizeTextField = new JTextField(Integer.toString(canvasPanel.getCellSize()));
        customizationPanel.add(gridSizeLabel);
        customizationPanel.add(gridSizeTextField);
        add(customizationPanel, BorderLayout.SOUTH);
    }

    private void initializeComponents() {
    }

    private void registerListeners() {
        colorPickerPanel.addColorSelectionListener(new ColorPickerPanel.ColorSelectionListener() {
            @Override
            public void colorSelected(Color color) {
                canvasPanel.setCurrentColor(color);
            }
        });

        gridSizeTextField.addActionListener(e -> {
            try {
                int newSize = Integer.parseInt(gridSizeTextField.getText());
                canvasPanel.setCellSize(newSize);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Tamaño de pixel invalido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });       
    }

    private void saveCanvasImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Imagen");
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try {
                FileHandler.saveImage(canvasPanel.getCanvasImage(), fileToSave);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error guardando imagen", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadCanvasImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Cargando Imagen");
        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToLoad = fileChooser.getSelectedFile();
            try {
                BufferedImage loadedImage = FileHandler.loadImage(fileToLoad);
                canvasPanel.setCanvasImage(loadedImage);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error cargando imagen", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearCanvas() {
        canvasPanel.clearCanvas();
    }
}

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
    }

    private void saveCanvasImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Image");
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try {
                FileHandler.saveImage(canvasPanel.getCanvasImage(), fileToSave);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error saving image", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadCanvasImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Load Image");
        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToLoad = fileChooser.getSelectedFile();
            try {
                BufferedImage loadedImage = FileHandler.loadImage(fileToLoad);
                canvasPanel.setCanvasImage(loadedImage);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error loading image", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearCanvas() {
        canvasPanel.clearCanvas();
    }
}

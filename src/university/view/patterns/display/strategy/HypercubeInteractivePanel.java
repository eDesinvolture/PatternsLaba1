package university.view.patterns.display.strategy;

import university.view.patterns.pool.DisciplineWidget;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HypercubeInteractivePanel extends JPanel implements ActionListener {

    // Геометрия гиперкуба (вершины и ребра)
    private final double[][] vertices = new double[16][4];
    private final int[][] edges = {
            {0,1},{0,2},{0,4},{0,8},{1,3},{1,5},{1,9},{2,3},{2,6},{2,10},{3,7},{3,11},{4,5},{4,6},
            {4,12},{5,7},{5,13},{6,7},{6,14},{7,15},{8,9},{8,10},{8,12},{9,11},{9,13},{10,11},
            {10,14},{11,15},{12,13},{12,14},{13,15}
    };

    // Новое: определяем 8 граней гиперкуба по индексам их вершин
    // Каждая грань - это куб, но мы будем использовать 4 вершины для отрисовки "квадрата"
    private final int[][] faces = {
            {0, 1, 3, 2}, {4, 5, 7, 6}, // Лицевая и задняя по оси Z
            {0, 1, 5, 4}, {2, 3, 7, 6}, // Нижняя и верхняя по оси Y
            {0, 2, 6, 4}, {1, 3, 7, 5}, // Левая и правая по оси X
            {8, 9, 11, 10}, {12, 13, 15, 14} // Грани, смещенные по 4-й оси W
    };

    // Хранилище для виджетов и их "фотографий"
    private final Map<Integer, DisciplineWidget> faceWidgets = new HashMap<>();
    private final Map<Integer, BufferedImage> widgetImages = new HashMap<>();

    private double angleXY = 0, angleZW = 0, angleXW = 0;
    private double rotationSpeed = 0.01;
    private final Timer timer;

    public HypercubeInteractivePanel() {
        // Инициализация вершин
        int i = 0;
        for (int x = -1; x <= 1; x += 2) for (int y = -1; y <= 1; y += 2)
            for (int z = -1; z <= 1; z += 2) for (int w = -1; w <= 1; w += 2)
                vertices[i++] = new double[]{x, y, z, w};

        setBackground(Color.DARK_GRAY);
        timer = new Timer(16, this);
        timer.start();
    }


    public void clearWidgets() {
        faceWidgets.clear();
        widgetImages.clear();
    }

    public void setWidgetForFace(int faceIndex, DisciplineWidget widget) {
        if (faceIndex >= faces.length) return;
        faceWidgets.put(faceIndex, widget);

        // "Фотографируем" виджет
        BufferedImage image = rasterizeWidget(widget);
        widgetImages.put(faceIndex, image);
    }

    private BufferedImage rasterizeWidget(DisciplineWidget widget) {
        Dimension size = widget.getPreferredSize();
        if (size.width <= 0 || size.height <= 0) {
            // Если размер 0, даем ему стандартный для рендеринга
            size = new Dimension(250, 80);
            widget.setSize(size);
        }

        BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // Важно: говорим виджету отрисовать себя на графике нашего изображения
        widget.paint(g2d);
        g2d.dispose();

        return image;
    }

    // --- Методы для вращения и отрисовки ---

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.translate(getWidth() / 2, getHeight() / 2);

        // 1. Проецируем все 16 вершин
        List<Point> projectedPoints = new ArrayList<>();
        for (double[] vertex : vertices) {
            double[] rotated = rotate(vertex);
            double w_perspective = 1 / (4 - rotated[3]);
            int scale = 250;
            projectedPoints.add(new Point(
                    (int) (rotated[0] * w_perspective * scale),
                    (int) (rotated[1] * w_perspective * scale)
            ));
        }

        // 2. Рисуем грани и виджеты на них
        for (int i = 0; i < faces.length; i++) {
            if (widgetImages.containsKey(i)) {
                // Находим 4 точки проекции для текущей грани
                Point p1 = projectedPoints.get(faces[i][0]);
                Point p2 = projectedPoints.get(faces[i][1]);
                Point p3 = projectedPoints.get(faces[i][2]);
                Point p4 = projectedPoints.get(faces[i][3]);

                // Находим центр грани
                int centerX = (p1.x + p2.x + p3.x + p4.x) / 4;
                int centerY = (p1.y + p2.y + p3.y + p4.y) / 4;

                BufferedImage img = widgetImages.get(i);
                // Рисуем "фотографию" виджета в центре грани
                g2d.drawImage(img, centerX - img.getWidth()/2, centerY - img.getHeight()/2, null);
            }
        }

        // 3. Рисуем ребра поверх всего
        g2d.setStroke(new BasicStroke(1f));
        g2d.setColor(new Color(0, 255, 255, 150)); // Полупрозрачный циан
        for (int[] edge : edges) {
            g2d.drawLine(
                    projectedPoints.get(edge[0]).x, projectedPoints.get(edge[0]).y,
                    projectedPoints.get(edge[1]).x, projectedPoints.get(edge[1]).y
            );
        }

        g2d.dispose();
    }

    // Метод таймера для анимации
    @Override
    public void actionPerformed(ActionEvent e) {
        angleXY += rotationSpeed;
        angleZW += rotationSpeed * 0.5;
        angleXW += rotationSpeed * 1.5;
        repaint();
    }

    // Утилиты (без изменений)
    public void setRotationSpeed(double speed) { this.rotationSpeed = speed; }
    private double[] rotate(double[] p) {
        double[] r = p.clone();
        double x, y, z, w;
        x = r[0]; y = r[1]; r[0] = x*Math.cos(angleXY) - y*Math.sin(angleXY); r[1] = x*Math.sin(angleXY) + y*Math.cos(angleXY);
        z = r[2]; w = r[3]; r[2] = z*Math.cos(angleZW) - w*Math.sin(angleZW); r[3] = z*Math.sin(angleZW) + w*Math.cos(angleZW);
        x = r[0]; w = r[3]; r[0] = x*Math.cos(angleXW) - w*Math.sin(angleXW); r[3] = x*Math.sin(angleXW) + w*Math.cos(angleXW);
        return r;
    }
}
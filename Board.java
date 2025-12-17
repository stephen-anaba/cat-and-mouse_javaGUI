// Name: Stephen Anaba
// CWID: 7053
// Email: stevoclock@csu.fullerton.edu
// Course #: CPSC-223jJ
// Assignment: FINAL EXAM
// Assignment title: Cat and Mouse GUI

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JFrame {
    // panels
    private JPanel top, middle, bottom;
    private JLabel titleLabel;
    // input fields
    private JTextField catDirIn, mouseDirIn, catSpeedIn, mouseSpeedIn;
    // buttons
    private JButton initBtn, goBtn, exitBtn;
    // coordinate output
    private JTextField catXout, catYout, mouseXout, mouseYout;
    // timer
    private Timer timer;
    // cat & mouse ball properties
    private double catX, catY, mouseX, mouseY, mouseSpeedValue, catSpeedValue;
    private int catSize = 50, mouseSize = 30;
    // cat & mouse speed components
    private double dxCat = 0, dyCat = 0, dxMouse = 0, dyMouse = 0;
    private boolean running = false;
    // escape hatch
    private int hatchX, hatchY, hatchWidth, hatchHeight;
    private boolean hatchInitialized = false;

    // constructor
    public Board() {
        setTitle("Cat and Mouse");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // ----------- top panel -----------
        top = new JPanel();
        top.setBackground(new Color(153, 255, 51));
        top.setLayout(new GridBagLayout());
        titleLabel = new JLabel("Cat and Mouse by Stephen Anaba", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        top.add(titleLabel);

        // ---------------- middle panel ----------------
        middle = new DrawPanel();
        middle.setBackground(new Color(255, 255, 153));

        // ---------------- bottom panel ----------------
        bottom = new JPanel();
        bottom.setBackground(new Color(255, 204, 51));
        bottom.setLayout(new GridLayout(4, 1));

        // ---- row1: initialize / go ----
        JPanel row1 = new JPanel(new GridLayout(1, 4));
        row1.setBackground(new Color(255, 204, 51));

        initBtn = new JButton("Initialize");
        JPanel empty1 = new JPanel();
        JPanel empty2 = new JPanel();
        empty1.setBackground(new Color(255, 204, 51));
        empty2.setBackground(new Color(255, 204, 51));

        goBtn = new JButton("Go");
        goBtn.setEnabled(false);

        row1.add(initBtn);
        row1.add(empty1);
        row1.add(goBtn);
        row1.add(empty2);

        // ---- row2: cat inputs / coordinates ----
        JPanel row2 = new JPanel(new GridLayout(1, 4));
        row2.setBackground(new Color(255, 204, 51));

        JPanel r2cell1 = new JPanel();
        r2cell1.setLayout(new BoxLayout(r2cell1, BoxLayout.Y_AXIS));
        r2cell1.add(new JLabel("Cat Initial Direction"));
        r2cell1.setBackground(new Color(255, 204, 51));
        catDirIn = new JTextField(10);
        r2cell1.add(catDirIn);

        JPanel r2cell2 = new JPanel();
        r2cell2.setLayout(new BoxLayout(r2cell2, BoxLayout.Y_AXIS));
        r2cell2.add(new JLabel("Cat Variable Speed"));
        r2cell2.setBackground(new Color(255, 204, 51));
        catSpeedIn = new JTextField(10);
        r2cell2.add(catSpeedIn);

        JPanel r2cell3 = new JPanel();
        r2cell3.setLayout(new BoxLayout(r2cell3, BoxLayout.Y_AXIS));
        r2cell3.add(new JLabel("Cat Coordinates"));
        r2cell3.setBackground(new Color(255, 204, 51));
        JPanel r2cell3sub = new JPanel();
        r2cell3sub.setLayout(new BoxLayout(r2cell3sub, BoxLayout.X_AXIS));
        r2cell3sub.setBackground(new Color(255, 204, 51));
        catXout = new JTextField(10);
        catYout = new JTextField(10);
        catXout.setEditable(false);
        catYout.setEditable(false);
        r2cell3sub.add(catXout);
        r2cell3sub.add(catYout);
        r2cell3.add(r2cell3sub);

        JPanel r2empty4 = new JPanel();
        r2empty4.setBackground(new Color(255, 204, 51));

        row2.add(r2cell1);
        row2.add(r2cell2);
        row2.add(r2cell3);
        row2.add(r2empty4);

        // ---- row3: mouse inputs / coords / exit ----
        JPanel row3 = new JPanel(new GridLayout(1, 4));
        row3.setBackground(new Color(255, 204, 51));

        JPanel r3cell1 = new JPanel();
        r3cell1.setLayout(new BoxLayout(r3cell1, BoxLayout.Y_AXIS));
        r3cell1.add(new JLabel("Mouse Initial Direction"));
        r3cell1.setBackground(new Color(255, 204, 51));
        mouseDirIn = new JTextField(10);
        r3cell1.add(mouseDirIn);

        JPanel r3cell2 = new JPanel();
        r3cell2.setLayout(new BoxLayout(r3cell2, BoxLayout.Y_AXIS));
        r3cell2.add(new JLabel("Mouse Variable Speed"));
        r3cell2.setBackground(new Color(255, 204, 51));
        mouseSpeedIn = new JTextField(10);
        r3cell2.add(mouseSpeedIn);

        JPanel r3cell3 = new JPanel();
        r3cell3.setLayout(new BoxLayout(r3cell3, BoxLayout.Y_AXIS));
        r3cell3.add(new JLabel("Mouse Coordinates"));
        r3cell3.setBackground(new Color(255, 204, 51));
        JPanel r3cell3sub = new JPanel();
        r3cell3sub.setBackground(new Color(255, 204, 51));
        r3cell3sub.setLayout(new BoxLayout(r3cell3sub, BoxLayout.X_AXIS));
        mouseXout = new JTextField(10);
        mouseYout = new JTextField(10);
        mouseXout.setEditable(false);
        mouseYout.setEditable(false);
        r3cell3sub.add(mouseXout);
        r3cell3sub.add(mouseYout);
        r3cell3.add(r3cell3sub);

        JPanel r3cell4 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        r3cell4.setBackground(new Color(255, 204, 51));
        exitBtn = new JButton("Exit"); 
        exitBtn.setPreferredSize(new Dimension(190, 40));
        r3cell4.add(exitBtn);

        row3.add(r3cell1);
        row3.add(r3cell2);
        row3.add(r3cell3);
        row3.add(r3cell4);

        JPanel row4 = new JPanel();
        row4.setBackground(new Color(255, 204, 51));

        bottom.add(row1);
        bottom.add(row2);
        bottom.add(row3);
        bottom.add(row4);

        add(top);
        add(middle);
        add(bottom);

        layoutPanels();

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                layoutPanels();
            }
        });

        // events
        initBtn.addActionListener(e -> initializeBoth());
        goBtn.addActionListener(e -> toggleGoPause());
        exitBtn.addActionListener(e -> System.exit(0));
        // timer (approximate 60fps)
        timer = new Timer(16, e -> {
            updateMouse();
            updateCatTracking();
        });

        setVisible(true);
    }

    private void layoutPanels() {
        int w = getWidth();
        int h = getHeight();
        int topHeight = h / 14;
        int bottomHeight = (h / 4);
        int middleHeight = h - topHeight - bottomHeight;
        top.setBounds(0, 0, w, topHeight);
        middle.setBounds(0, topHeight, w, middleHeight);
        bottom.setBounds(0, topHeight + middleHeight, w, bottomHeight);
    }

    // ------------ initialize -------------
    private void initializeBoth() {
        String s = mouseSpeedIn.getText().trim();
        String d = mouseDirIn.getText().trim();
        String sCat = catSpeedIn.getText().trim();
        String dCat = catDirIn.getText().trim();

        if (s.isEmpty() || d.isEmpty() || sCat.isEmpty() || dCat.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All speed and Direction values must be entered.");
            return;
        }

        double mouseAngleDeg, catAngleDeg;

        try {
            mouseSpeedValue = Double.parseDouble(s);
            mouseAngleDeg = Double.parseDouble(d);
            catSpeedValue = Double.parseDouble(sCat);
            catAngleDeg = Double.parseDouble(dCat);

            if (mouseSpeedValue <= 0 || catSpeedValue <= 0)
                throw new NumberFormatException();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter valid positive numbers.");
            return;
        }

        double mouseAngleRad = Math.toRadians(mouseAngleDeg);
        double catAngleRad = Math.toRadians(catAngleDeg);

        dxMouse = (mouseSpeedValue * Math.cos(mouseAngleRad)) / 60.0;
        dyMouse = -(mouseSpeedValue * Math.sin(mouseAngleRad)) / 60.0;

        dxCat = (catSpeedValue * Math.cos(catAngleRad)) / 70.0;
        dyCat = -(catSpeedValue * Math.sin(catAngleRad)) / 70.0;

        mouseX = middle.getWidth() / 2.0 - mouseSize / 2.0;
        mouseY = middle.getHeight() / 2.0 - mouseSize / 2.0;

        int panelW = middle.getWidth();
        int panelH = middle.getHeight();

        catX = 20;
        catY = panelH / 2.0 - catSize / 2.0;

        int avgSize = (catSize + mouseSize) / 2;
        hatchWidth = avgSize;
        hatchHeight = avgSize;

        // hatch fully inside panel
        hatchX = panelW - hatchWidth - 5;
        hatchY = (panelH - hatchHeight) / 2;
        hatchInitialized = true;

        middle.repaint();
        goBtn.setEnabled(true);
    }

    private void toggleGoPause() {
        if (!running) {
            timer.start();
            running = true;
            goBtn.setText("Pause");
        } else {
            timer.stop();
            running = false;
            mouseDirIn.setEnabled(false);
            catDirIn.setEnabled(false);
            goBtn.setText("Go");
        }
    }

    private void reflect(double nx, double ny) {
        double len = Math.sqrt(nx * nx + ny * ny);
        nx /= len;
        ny /= len;
        double dot = dxMouse * nx + dyMouse * ny;
        dxMouse = dxMouse - 2 * dot * nx;
        dyMouse = dyMouse - 2 * dot * ny;
    }

    private void updateMouse() {
        int panelW = middle.getWidth();
        int panelH = middle.getHeight();

        mouseX += dxMouse;
        mouseY += dyMouse;

        if (mouseX <= 0) {
            mouseX = 0;
            reflect(1, 0);
        }
        if (mouseX + mouseSize >= panelW) {
            mouseX = panelW - mouseSize;
            reflect(-1, 0);
        }
        if (mouseY <= 0) {
            mouseY = 0;
            reflect(0, 1);
        }
        if (mouseY + mouseSize >= panelH) {
            mouseY = panelH - mouseSize;
            reflect(0, -1);
        }

        if (hatchInitialized) {
            double mouseCenterX = mouseX + mouseSize / 2.0;
            double mouseCenterY = mouseY + mouseSize / 2.0;

            if (mouseCenterX >= hatchX - 3 &&
                mouseCenterX <= hatchX + 3 &&
                mouseCenterY >= hatchY &&
                mouseCenterY <= hatchY + hatchHeight) {

                timer.stop();
                running = false;
                JOptionPane.showMessageDialog(this,
                        "The mouse escaped through the secret hatch!\nMOUSE WINS!");
            }
        }

        mouseXout.setText(Integer.toString((int) (mouseX + mouseSize / 2)));
        mouseYout.setText(Integer.toString((int) (mouseY + mouseSize / 2)));

        middle.repaint();
    }

    private void updateCatTracking() {

        // stop cat logic if mouse already won
        if (!running) return;

        int panelW = middle.getWidth();
        int panelH = middle.getHeight();

        double cx = catX + catSize / 2.0;
        double cy = catY + catSize / 2.0;
        double mx = mouseX + mouseSize / 2.0;
        double my = mouseY + mouseSize / 2.0;

        double vx = mx - cx;
        double vy = my - cy;

        double len = Math.sqrt(vx * vx + vy * vy);
        if (len != 0) {
            vx /= len;
            vy /= len;
        }

        double step = catSpeedValue / 70.0;
        dxCat = vx * step;
        dyCat = vy * step;

        catX += dxCat;
        catY += dyCat;

        if (catX < 0) catX = 0;
        if (catX + catSize > panelW) catX = panelW - catSize;
        if (catY < 0) catY = 0;
        if (catY + catSize > panelH) catY = panelH - catSize;

        catXout.setText(Integer.toString((int) (catX + catSize / 2)));
        catYout.setText(Integer.toString((int) (catY + catSize / 2)));

        double catCenterX = catX + catSize / 2.0;
        double catCenterY = catY + catSize / 2.0;
        double mouseCenterX = mouseX + mouseSize / 2.0;
        double mouseCenterY = mouseY + mouseSize / 2.0;

        double dist = Math.sqrt(Math.pow(catCenterX - mouseCenterX, 2)
                + Math.pow(catCenterY - mouseCenterY, 2));

        if (dist < (catSize / 2.0)) {
            timer.stop();
            running = false;
            JOptionPane.showMessageDialog(this,
                    "The cat caught the mouse!\nCAT WINS!");
        }

        middle.repaint();
    }

    private class DrawPanel extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (goBtn.isEnabled()) {
                g.setColor(Color.BLUE);
                g.fillOval((int) mouseX, (int) mouseY, mouseSize, mouseSize);

                g.setColor(Color.RED);
                g.fillOval((int) catX, (int) catY, catSize, catSize);

                if (hatchInitialized) {
                    g.setColor(Color.BLACK);
                    g.fillRect(hatchX - 3, hatchY, 6, hatchHeight);
                }
            }
        }
    }
}


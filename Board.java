// Name: Stephen Anaba
// CWID: 7053
// Email: stevoclock@csu.fullerton.edu
// Course #: CPSC-223J
// Assignment #: FINAL program
// Program Title: Cat and Mouse GUI 

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
    private Timer timer; //mouseTimer, catTimer;
    // cat & mouse ball properties
    private double catX, catY, mouseX, mouseY;
    private int catSize = 50, mouseSize = 30;
    // cat & mouse speed components
    private double dxCat = 0, dyCat = 0, dxMouse = 0, dyMouse = 0; 
    private boolean running = false;
    // private Dimension size1, size2, size3;

    // constructor
    public Board() {
        setTitle("Cat and Mouse");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        // ----------- TOP PANEL -----------
        top = new JPanel(); 
        top.setBackground(new Color(153, 255, 51));
        top.setLayout(new GridBagLayout());
        titleLabel = new JLabel("Cat and Mouse by Stephen Anaba", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        top.add(titleLabel);
        // ---------------- MIDDLE PANEL ----------------
        middle = new DrawPanel(); // draw the mouse panel
        middle.setBackground(new Color(255, 255, 153));
        // ---------------- BOTTOM PANEL ----------------
        // create bottom, set background color, layout
        bottom = new JPanel();
        bottom.setBackground(new Color(255, 204, 51)); 
        bottom.setLayout(new GridLayout(4, 1));  // 4 rows, 1 cols

        // ---- ROW1 : initBtn | goBtn (GridLayout option)----
        // create row1, initBtn & goBtn
        JPanel row1 = new JPanel(new GridLayout(1, 4)); 
            // new JPanel(new FlowLayout(FlowLayout.LEFT,150,10)); // hor & vert spacing
        row1.setBackground(new Color(255, 204, 51)); // set bground color
        initBtn = new JButton("Initialize"); // label initBtna 
        JPanel empty1 = new JPanel();
        JPanel empty2 = new JPanel(); // 1st empty cell of row1
        // color the empties
        empty1.setBackground(new Color(255, 204, 51));
        empty2.setBackground(new Color(255, 204, 51));
        goBtn = new JButton("Go");          // label goBtn
        goBtn.setEnabled(false);           // initially deactivate goBtn
        // apply custom sizes
//        initBtn.setPreferredSize(size2); // initBtn size = btnSize2
//        goBtn.setPreferredSize(size2);  // goBtn size = btnSize2
        goBtn.setEnabled(false); // disabled until initialize 
        // add initBtn and goBtn to row1 
        row1.add(initBtn);
        row1.add(empty1); // empty
        row1.add(goBtn);
        row1.add(empty2);

        // ROW2: catDirIn | catSpeedIn | catXout | catYout | empty
        JPanel row2 = new JPanel(new GridLayout(1, 4));
        row2.setBackground(new Color(255, 204, 51));
        // r2cell1: label + catDirIn, vertically stacked
        JPanel r2cell1 = new JPanel(); // create r2cell1
        r2cell1.setLayout(new BoxLayout(r2cell1, BoxLayout.Y_AXIS));
        r2cell1.add(new JLabel("Cat Initial Direction")); // add label
        r2cell1.setBackground(new Color(255, 204, 51));
        catDirIn = new JTextField(10); // initialize catDirIn field
        // catDirIn.setPreferredSize(size1); // set 
        r2cell1.add(catDirIn);                           // add catDirIn
        // r2cell2: label + catSpeedIn, vertically stacked
        JPanel r2cell2 = new JPanel();
        r2cell2.setLayout(new BoxLayout(r2cell2, BoxLayout.Y_AXIS));
        r2cell2.add(new JLabel("Cat Variable Speed"));
        r2cell2.setBackground(new Color(255, 204, 51));
        catSpeedIn = new JTextField(10); // initialize catSpeedIn field
        // catSpeedIn.setPreferredSize(size3);
        r2cell2.add(catSpeedIn);
        // cell3: label + (catXout + catYout, horizontal), vertical
        JPanel r2cell3 = new JPanel(); // make cell & set its vert layout3
        r2cell3.setLayout(new BoxLayout(r2cell3, BoxLayout.Y_AXIS));
        r2cell3.add(new JLabel("Cat Coordinates")); // add label to r2cell3. Make a sub
        r2cell3.setBackground(new Color(255, 204, 51));

        JPanel r2cell3sub = new JPanel();
        r2cell3sub.setLayout(new BoxLayout(r2cell3sub, BoxLayout.X_AXIS));
        r2cell3sub.setBackground(new Color(255, 204, 51));
        catXout = new JTextField(10); // initialize catXout field
        catYout = new JTextField(10);// initialize catYout field 
        // catXout.setPreferredSize(size1); // set catXout size
        // catYout.setPreferredSize(size1); // set catYout size
        catXout.setEditable(false);    // make catXout un-editable
        catYout.setEditable(false);   // make catYout un-editable
        r2cell3sub.add(catXout);       // add catXout to cell3sub
        r2cell3sub.add(catYout);      // add catYout to cell3sub
        r2cell3.add(r2cell3sub);       // add cell3sub to cell3
        // cell4: empty
        JPanel r2empty4 = new JPanel();
        r2empty4.setBackground(new Color(255, 204, 51));

        // add r2cells 1-4 to row2
        row2.add(r2cell1); row2.add(r2cell2); row2.add(r2cell3); row2.add(r2empty4);
       
        // ROW3: mouseDirIn | mouseSpeedIn | mouseXout | mouseYout | exit
        JPanel row3 = new JPanel(new GridLayout(1, 4)); // create row3
        row3.setBackground(new Color(255, 204, 51)); // set bground color
        // r3cell1: label + mouseDirIn, vertically stacked
        JPanel r3cell1 = new JPanel(); // create r3cell1
        r3cell1.setLayout(new BoxLayout(r3cell1, BoxLayout.Y_AXIS));
        r3cell1.add(new JLabel("Mouse Initial Direction")); // add label
        r3cell1.setBackground(new Color(255, 204, 51));
        mouseDirIn = new JTextField(10); // initialize mouseDirIn field
//        mouseDirIn.setPreferredSize(size1); // set 
        r3cell1.add(mouseDirIn);           // add mouseDirIn
        // r3cell2: label + mouseSpeedIn, vertically stacked
        JPanel r3cell2 = new JPanel();
        r3cell2.setLayout(new BoxLayout(r3cell2, BoxLayout.Y_AXIS));
        r3cell2.add(new JLabel("Mouse Variable Speed"));
        r3cell2.setBackground(new Color(255, 204, 51));
        mouseSpeedIn = new JTextField(10); // initialize mouseSpeedIn field
//        mouseSpeedIn.setPreferredSize(size3);
        r3cell2.add(mouseSpeedIn);
        // r3cell3: label + (mouseXout + mouseYout, horizontal), vertical
        JPanel r3cell3 = new JPanel(); // make cell & set its vert layout3
        r3cell3.setLayout(new BoxLayout(r3cell3, BoxLayout.Y_AXIS));
        r3cell3.add(new JLabel("Mouse Coordinates")); // add label to cell3. Make a sub
        r3cell3.setBackground(new Color(255, 204, 51));
        JPanel r3cell3sub = new JPanel();
        r3cell3sub.setBackground(new Color(255, 204, 51));
        r3cell3sub.setLayout(new BoxLayout(r3cell3sub, BoxLayout.X_AXIS));
        mouseXout = new JTextField(10);  // initialize mouseXout field
        mouseYout = new JTextField(10); // initialize mouseYout field 
        // mouseXout.setPreferredSize(size1);   // set mouseXout size
        // mouseYout.setPreferredSize(size1);  // set mouseYout size
        mouseXout.setEditable(false);      // make mouseXout un-editable
        mouseYout.setEditable(false);     // make mouseYout un-editable
        r3cell3sub.add(mouseXout);       // add mouseXout to cell3sub
        r3cell3sub.add(mouseYout);      // add mouseYout to cell3sub
        r3cell3.add(r3cell3sub);       // add r3cell3sub to r3cell3
        // r3cell4: exitBtn
        // JPanel r3cell4 = new JPanel();
        JPanel r3cell4 = new JPanel(new FlowLayout(FlowLayout.CENTER,10,5));
        r3cell4.setBackground(new Color(255, 204, 51));
        exitBtn = new JButton("Exit");
        exitBtn.setBackground(new Color(255, 204, 51));
        exitBtn.setPreferredSize(new Dimension(190,40));
        r3cell4.add(exitBtn); // add exitBtn to r3cell4
        // add r3cells 1-4 to row2
        row3.add(r3cell1); 
        row3.add(r3cell2); 
        row3.add(r3cell3); 
        row3.add(r3cell4);

        // row4: empty
        JPanel row4 = new JPanel();
        row4.setBackground(new Color(255, 204, 51));

        // add rows 1-3 to bottom panel
        bottom.add(row1); 
        bottom.add(row2); 
        bottom.add(row3);
        bottom.add(row4);

        // ----------- add panels ----------
        add(top); add(middle); add(bottom);

        layoutPanels(); // line 179
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                layoutPanels();
            }
        });

        // ---------------- events ----------------
        initBtn.addActionListener(e -> initializeBoth());
        goBtn.addActionListener(e -> toggleGoPause());
        exitBtn.addActionListener(e -> System.exit(0));

        // mouseTimer (60 fps)
        timer = new Timer(16, e -> updateMouse());
        // catTimer = new Timer(16, e -> updateCat());

        setVisible(true);
 

    } // end of constructor

    // ================= panel layout =================
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

    // ---------------------------------------
    // initialize mouse after input validation
    // ---------------------------------------
    private void initializeBoth() {
        // get mouse speed & direction
        String s = mouseSpeedIn.getText().trim();
        String d = mouseDirIn.getText().trim();
        // get cat speed & direction
        String sCat = catSpeedIn.getText().trim();
        String dCat = catDirIn.getText().trim();

        // input validation
        if (s.isEmpty() || d.isEmpty() || sCat.isEmpty() || dCat.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All speed and Direction values must be entered.");
            return;
        }

        // ------ after initial speed and direction enetered -----
        double mouseSpeed, mouseAngleDeg;
        try { // convert speed & dir to numbers
            mouseSpeed = Double.parseDouble(s);
            mouseAngleDeg = Double.parseDouble(d);
            // endure negative numbers are not entered
            if (mouseSpeed <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter valid positive numbers.");
            return;
        }
        // convert the angle from degrees to radian
        double mouseAngleRad = Math.toRadians(mouseAngleDeg);
        // Velocity per frame
        dxMouse = (mouseSpeed * Math.cos(mouseAngleRad))/60.0;
        dyMouse = -(mouseSpeed * Math.sin(mouseAngleRad))/60.0; // negative = upward
        // Start mouse in middle
        mouseX = middle.getWidth() / 2.0 - mouseSize / 2.0;
        mouseY = middle.getHeight() / 2.0 - mouseSize / 2.0;

        middle.repaint();
        goBtn.setEnabled(true);
    } // end of initializeMouse

    // -----------
    // go / pause
    // -----------
    private void toggleGoPause() {
        if (!running) {
            timer.start();
            running = true;
            goBtn.setText("Pause");
        } else {
            timer.stop();
            running = false;
            mouseDirIn.setEnabled(false); // disable mouseDirIn 
            catDirIn.setEnabled(false); // disable catDirIn
            goBtn.setText("Go");
        }
    } // end of toggleGoPause

    // ------------------------------------
    // general vector reflection function
    // ------------------------------------
    private void reflect(double nx, double ny) {
        double len = Math.sqrt(nx * nx + ny * ny);
        nx /= len;
        ny /= len;

        double dot = dxMouse * nx + dyMouse * ny;
        dxMouse = dxMouse - 2 * dot * nx;
        dyMouse = dyMouse - 2 * dot * ny;
    } // end of reflect(...)

    // ----------------------------------------------
    // update ball position using physics reflection
    // ----------------------------------------------
    private void updateMouse() {
        int panelW = middle.getWidth();
        int panelH = middle.getHeight();

        // Move with double precision
        mouseX += dxMouse;
        mouseY += dyMouse;

        // left wall
        if (mouseX <= 0) {
            mouseX = 0;
            reflect(1, 0);  // normal = (1, 0)
        }

        // right wall
        if (mouseX + mouseSize >= panelW) {
            mouseX = panelW - mouseSize;
            reflect(-1, 0); // normal = (-1, 0)
        }

        // top wall
        if (mouseY <= 0) {
            mouseY = 0;
            reflect(0, 1);  // normal = (0, 1)
        }

        // bottom wall
        if (mouseY + mouseSize >= panelH) {
            mouseY = panelH - mouseSize;
            reflect(0, -1); // normal = (0, -1)
        }

        // Update center outputs
        mouseXout.setText(Integer.toString((int)(mouseX + mouseSize / 2)));
        mouseYout.setText(Integer.toString((int)(mouseY + mouseSize / 2)));

        middle.repaint();
    } // end of updateMouse()

    // ================== draw panel =================
    private class DrawPanel extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (goBtn.isEnabled()) {
                // draw mouse
                g.setColor(Color.BLUE); 
                g.fillOval((int)mouseX, (int)mouseY, mouseSize, mouseSize); 
                // draw cat
                g.setColor(Color.RED);
                g.fillOval((int)catX, (int)catY, catSize, catSize);
            }
        } // end paintComponent(Graphics gMouse)
    } // end of DrawPanel

} // end of Board






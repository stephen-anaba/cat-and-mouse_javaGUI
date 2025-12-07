// Name: Stephen Anaba
// CWID: 7053
// Email: stevoclock@csu.fullerton.edu
// Course #: CPSC-223J
// Assignment #: FINAL program
// Program Title: Cat and Mouse GUI 

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// ==================== NEW ============================
// ------ Top Panel -------
// initBtn:     Initialize => button
// goBtn:       Go/Pause => Toggle button
// ------ Middle Panel ------
// catDir:       Cat initial dir => Input text field
// catSpeed:     Cat variable speed => Output text field (immutable)
// catX:         Cat-X => X-coordinate for Cat
// catY:         Cat-Y => Y-coordinate for Cat
// ------- Bottom Panel -------
// mouseDir:     Mouse initial dir => Input text field
// mouseSpeed:   Mouse variable speed => Output field (immutable)
// mouseX:       Mouse-X => X-coordinate for Mouse
// mouseY:       Mouse-Y => Y-coordinate for Mouse
// exitBtn:      Exit => button
// ---------------------------------------------------
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
    // cat & mouse ball properties : 214
    private double catBallX, catBallY, mouseBallX, mouseBallY;
    private int catBallSize = 50; private int mouseBallSize = 30;
    // cat & mouse speed components
    private double dxCat = 0, dyCat = 0, dxMouse = 0, dyMouse = 0; 
    private boolean catRunning = false;
    private boolean mouseRunning = false;
    // 213
    private Dimension size1, size2, size3;

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
        middle = new DrawPanel();
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
        JPanel r3cell4 = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
        r3cell4.setBackground(new Color(255, 204, 51));
        exitBtn = new JButton("Exit");
        exitBtn.setBackground(new Color(255, 204, 51));
        exitBtn.setPreferredSize(new Dimension(190,40));
        // exitBtn.setPreferredSize(size2); // size2
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
        bottom.add(row1); bottom.add(row2); bottom.add(row3);
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
 /*       initBtn.addActionListener(e -> initializeBall());
        startBtn.addActionListener(e -> toggleStartPause());
        quitBtn.addActionListener(e -> System.exit(0));

        // timer (60 fps)
        timer = new Timer(16, e -> updateBall()); */

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
// 71
/*        // catDirIn/mouseDirIn/catXout/catYout/mouseXout/mouseYou 
        size1 = new Dimension((int)(7*bottomHeight/2), (int)(bottomHeight/6)); // w50, h32 
        // initBtn/goBtn/exitBtn
        size2 = new Dimension(2*bottomHeight/3, bottomHeight/6); // 80,30
        // catSpeedIn/mouseSpeedIn
        size3 = new Dimension(2*bottomHeight/3, bottomHeight/6); // 130,32 */
    }

    // ================== draw panel =================
    private class DrawPanel extends JPanel {
        // paint compoent for mouse - blue
        protected void paintMouseComponent(Graphics gMouse) {
            super.paintComponent(gMouse);
            if (goBtn.isEnabled()) {
                gMouse.setColor(Color.BLUE); 
                gMouse.fillOval((int)mouseBallX, (int)mouseBallY, mouseBallSize, mouseBallSize); 
            }
        } // end paintComponent(Graphics gMouse)
        // paint component for cat - red
        protected void paintCatComponent(Graphics gCat) {
            super.paintComponent(gCat);
            if (goBtn.isEnabled()) {
                gCat.setColor(Color.RED);
                gCat.fillOval((int)catBallX, (int)catBallY, catBallSize, catBallSize);
            }
        } // end paintComponent(Graphics gCat)
    }

} // end of Board



// ================ OLD ===============================
/*
 public class Board extends JFrame {
    // Panels
    private JPanel top, middle, bottom; // panels
    private JLabel titleLabel;         // titel
    // Input fields: angle (radians)
    private JTextField catDirField, catSpeedIn, mouseDirField, mouseSpeedOut;
    // Buttons
    private JButton initBtn, goBtn, exitBtn;
    // Coordinate output
    private JTextField catSpeedIn, mouseSpeedOut;
    // Timer
    private Timer timer;
    // Ball properties
    private double ballX, ballY;  // now double
    private int ballSize = 30;
    private double dx = 0, dy = 0;   // velocity components
    private boolean running = false;
    
    // Constructor
    public Board() {
        setTitle("Cat and Mouse");
        setSize(900, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        // ---------------- TOP PANEL ----------------
        top = new JPanel();
        top.setBackground(new Color(153, 255, 51));
        top.setLayout(new GridBagLayout());
        titleLabel = new JLabel("Cat and Mouse by Stephen Anaba", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        top.add(titleLabel);
        // ---------------- MIDDLE PANEL ----------------
        middle = new DrawPanel();
        middle.setBackground(new Color(255, 255, 153)); 
        
        // ---------------- BOTTOM PANEL -----------------
        // create bottom, set background color, layout
        bottom = new JPanel();
        bottom.setBackground(new Color(255, 204, 51));
        bottom.setLayout(new GridLayout(3, 1));  // 3 rows, 1 cols

        // BUTTON SIZES for adjustment
         Dimension btnSize1 = new Dimension(110, 32); // cat/mouse fields
         Dimension btnSize2 = new Dimension(110, 32); // init/Go
         Dimension btnSize3 = new Dimension(110, 32); // Exit

        // ---- ROW 1: Initialize, Go ----
        // create row1, initField & goField
        JPanel row1 = new JPanel(new GridLayout(1, 2));
        row1.setBackground(new Color(255, 204, 51));
        initBtn = new JButton("Initialize");
        goBtn = new JButton("Go");
        goBtn.setEnabled(false);

        // apply compact sizes
        initBtn.setPreferredSize(btnSize2); // resize initBtn
        goBtn.setPreferredSize(btnSize2);  // resize goBtn

        // add initBtn and goBtn to row1 
        row1.add(initBtn);
        row1.add(goBtn);

        // ---- ROW 2: catDirField | catVarSpeed | catCoord A,B ---
        JPanel row2 = new JPanel(new GridLayout(1, 3));
        // group 1: create catDirField to hold label "Cat initial direction" & text field vertically. Add tehm accordingly.
        JPanel catDirField = new JPanel(); // create catDirField 
        catDirField.setLayout(new BoxLayout(catDirField,BoxLayout.Y_AXIS));
        catDirField.add(new JLabel("Cat initial direction"));
        JTextField catDirInput = new JTextField(10);
        catDirField.add(catDirInput);
        // group 2: Cat variable speed
        JPanel catSpeed = new JPanel();
        catSpeedIn.setLayout(new BoxLayout(catSpeedIn, BoxLayout.Y_AXIS));
        catSpeedIn.add(new JLabel("Cat Variable Speed"));
        JTextField catSpeedIn = new JTextField(10);
        catSpeed.add(catSpeedIn);
        // group 3: Cat Coordinates X and Y
        JPanel catCoord = new JPanel();
        catCoord.setLayout(new BoxLayout(catCoord, BoxLayout.Y_AXIS));
        catCoord.add(new JLabel("Cat Coordinates"));
        JTextField coordX = new JTextField(10);
        JTextField coordY = new JTextField(10);
        coordX.setEditable(false);
        coordY.setEditable(false);
        // add groups to row2
        row2.add(catDirField);
        row2.add(catSpeed);
        row2.add(catCoord);

        // ---- ROW 3: mouseInitDir | mouseSpeed | mouseCoord X,Y ---
        JPanel row3 = new JPanel(new GridLayout(1, 3));
        // group 3: Mouse initial direction
        JPanel mouseInitDir = new JPanel();
        mouseInitDir.setLayout(new BoxLayout(mouseInitDir, BoxLayout.Y_AXIS));
        mouseInitDir.add(new JLabel("Mouse initial direction"));
        JTextField mouseDirField = new JTextField(10);
        mouseInitDir.add(mouseDirField);
        // group 4: Mouse variable speed
        JPanel mouseSpeed = new JPanel();
        mouseSpeed.setLayout(new BoxLayout(mouseSpeed, BoxLayout.Y_AXIS));
        mouseSpeed.add(new JLabel("Cat Variable Speed"));
        catSpeedIn = new JTextField(10);
        mouseSpeed.add(catSpeedIn);
        // group 5: Cat Coordinates X and Y
        JPanel mouseCoord = new JPanel();
        mouseCoord.setLayout(new BoxLayout(mouseCoord, BoxLayout.Y_AXIS));
        mouseCoord.add(new JLabel("Cat Coordinates"));
        coordX = new JTextField(10);
        coordY = new JTextField(10);
        coordX.setEditable(false);
        coordY.setEditable(false);
        // add groups to row3
        row3.add(mouseInitDir);
        row3.add(mouseSpeed);
        row3.add(mouseCoord);
        
        // add row1, row2, row3 to bottom panel
        bottom.add(row1); 
        bottom.add(row2); 
        bottom.add(row3); 

        // ---------------- add panels ----------------
        add(top);
        add(middle);
        add(bottom);

        layoutPanels();
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                layoutPanels();
            }
        });

        // ---------------- events ----------------
        initBtn.addActionListener(e -> initializeBall());
        goBtn.addActionListener(e -> toggleGoPause());
        exitBtn.addActionListener(e -> System.exit(0));

        // timer (60 fps)
        timer = new Timer(16, e -> updateBall());

        setVisible(true);
    } // end of constructor
    
    // ------- PlaceholderTextField helper class --------
    class PlaceholderTextField extends JTextField {
        private String placeholder;

        public PlaceholderTextField(String placeholder) {
            this.placeholder = placeholder;
        }

        @Override
         protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (getText().isEmpty() && !isFocusOwner()) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(Color.GRAY);
                g2.drawString(placeholder, 5, getHeight() - 7);
                g2.dispose();
            }
        }
    } // end of PlaceholderTextField class


    // --------------
    // panel layout
    // --------------
    private void layoutPanels() {
        int w = getWidth();
        int h = getHeight();

        int topHeight = h / 10;
        int bottomHeight = (h / 4);
        int middleHeight = h - topHeight - bottomHeight;

        top.setBounds(0, 0, w, topHeight);
        middle.setBounds(0, topHeight, w, middleHeight);
        bottom.setBounds(0, topHeight + middleHeight, w, bottomHeight);
    }

    // ---------------------------------------
    // initialize ball after input validation
    // ---------------------------------------
    private void initializeBall() {
        // variables
        String s = speedField.getText().trim();
        String d = dirField.getText().trim();
        // input validation
        if (s.isEmpty() || d.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Both Speed and Direction must be entered.");
            return;
        }

        double speed, angleDeg;
        try {
            speed = Double.parseDouble(s);
            angleDeg = Double.parseDouble(d);

            if (speed <= 0) throw new NumberFormatException();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter valid positive numbers.");
            return;
        }

        double angleRad = Math.toRadians(angleDeg);

        // Velocity per frame
        dx = (speed * Math.cos(angleRad)) / 60.0;
        dy = -(speed * Math.sin(angleRad)) / 60.0; // negative = upward

        // Start ball in middle
        ballX = middle.getWidth() / 2.0 - ballSize / 2.0;
        ballY = middle.getHeight() / 2.0 - ballSize / 2.0;

        middle.repaint();
        goBtn.setEnabled(true);
    }

    // --------------
    // go / pause
    // --------------
    private void toggleGoPause() {
        if (!running) {
            timer.start();
            running = true;
            goBtn.setText("Pause");
        } else {
            timer.stop();
            running = false;
            goBtn.setText("Go");
        }
    }

    // ------------------------------------
    // general vector reflection function
    // ------------------------------------
    private void reflect(double nx, double ny) {
        double len = Math.sqrt(nx * nx + ny * ny);
        nx /= len;
        ny /= len;

        double dot = dx * nx + dy * ny;

        dx = dx - 2 * dot * nx;
        dy = dy - 2 * dot * ny;
    }

    // ----------------------------------------------
    // update ball position using physics reflection
    // ----------------------------------------------
    private void updateBall() {
        int panelW = middle.getWidth();
        int panelH = middle.getHeight();

        // Move with double precision
        ballX += dx;
        ballY += dy;

        // left wall
        if (ballX <= 0) {
            ballX = 0;
            reflect(1, 0);  // normal = (1, 0)
        }

        // right wall
        if (ballX + ballSize >= panelW) {
            ballX = panelW - ballSize;
            reflect(-1, 0); // normal = (-1, 0)
        }

        // top wall
        if (ballY <= 0) {
            ballY = 0;
            reflect(0, 1);  // normal = (0, 1)
        }

        // bottom wall
        if (ballY + ballSize >= panelH) {
            ballY = panelH - ballSize;
            reflect(0, -1); // normal = (0, -1)
        }

        // Update center outputs
        xOutput.setText(Integer.toString((int)(ballX + ballSize / 2)));
        yOutput.setText(Integer.toString((int)(ballY + ballSize / 2)));

        middle.repaint();
    }

    // -----------
    // draw panel
    // -----------
    private class DrawPanel extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (goBtn.isEnabled()) {
                g.setColor(Color.BLUE);
                g.fillOval((int)ballX, (int)ballY, ballSize, ballSize);
            }
        }
    }

    public static void main(String[] args) {
        new Board();
    }
} */


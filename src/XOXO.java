package src;
import javax.swing.*; // interactive interface - JFrame
import java.awt.*; // Grids & customization
import java.awt.event.ActionEvent; // events after clicking
import java.awt.event.ActionListener; // ability to register clicks
import java.util.LinkedList;

/**
 * Tic Tac Toe <3
 */
public class XOXO extends JFrame {
    private JButton[][] board = new JButton[3][3];
    private boolean xTurn = true;
    private LinkedList<int[]> moveHistor = new LinkedList<>();
    private int xWins = 0;
    private boolean xWon = false;
    private int oWins = 0;
    private boolean oWon = false;
    private ImageIcon xIcon, oIcon, heartIcon, backgroundImage, noahIcon, cathlynIcon;
    
    /**
     * Sets up the interfacex
     */
    public XOXO() {
        setTitle("Tic Tac Toe");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 3));

        // images for background
        xIcon = new ImageIcon("images/X.png");
        oIcon = new ImageIcon("images/O.png");
        heartIcon = new ImageIcon("images/heart.png");
        noahIcon = new ImageIcon("images/Noah.png");
        cathlynIcon = new ImageIcon("images/Cathlyn.png");
        backgroundImage = new ImageIcon("images/Background.png");

        JPanel panel = new BackgroundPanel();
        panel.setLayout(new GridLayout(3,3));
        
        // Initialize board
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = new JButton();
                board[i][j].setIcon(null);
                board[i][j].setFocusPainted(false);
                board[i][j].setContentAreaFilled(false);
                board[i][j].setBorderPainted(false);
                board[i][j].setFont(new Font("Arial", Font.BOLD, 80));
                board[i][j].addActionListener(new ButtonClickListener(i, j));
                panel.add(board[i][j]);
            }
        }
        setContentPane(panel);
        setVisible(true);
    }

    /**
     * Paints panel before adding the buttons
     */
    private class BackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
    
    /**
     * Makes it so we can click buttons in the interface
     */
    private class ButtonClickListener implements ActionListener {
        private int row, col;
        
        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if (board[row][col].getIcon() == null) {
                if (xTurn) {
                    board[row][col].setIcon(xIcon);
                } else {
                    board[row][col].setIcon(oIcon);
                }
                xTurn = !xTurn;
                checkWin();
            }
        }
    }
    
    
    /**
     * Checks who wins, X or O
     */
    private void checkWin() {
        // horizontal - rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0].getIcon() != null && 
            board[i][0].getIcon() == board[i][1].getIcon() && 
            board[i][0].getIcon() == board[i][2].getIcon()) {
                if (board[i][0].getIcon() == xIcon) {
                    xWins++;
                    xWon = true;
                    winner("X");
                } else {
                    oWins++;
                    oWon = true;
                    winner("O");
                }
                return;
            }
        }

        // vertical - cols
        for (int i = 0; i < 3; i++) {
            if (board[0][i].getIcon() != null && 
            board[0][i].getIcon() == board[1][i].getIcon() && 
            board[0][i].getIcon() == board[2][i].getIcon()) {
                if (board[0][i].getIcon() == xIcon) {
                    xWins++;
                    xWon = true;
                    winner("X");
                } else {
                    oWins++;
                    oWon = true;
                    winner("O");
                }
                return;
            }
        }

        // Diagonal
        if (board[0][0].getIcon() != null && 
            board[0][0].getIcon() == board[2][2].getIcon() && 
            board[1][1].getIcon() == board[0][0].getIcon()) {
            if (board[1][1].getIcon() == xIcon) {
                xWins++;
                xWon = true;
                winner("X");
            } else {
                oWins++;
                oWon = true;
                winner("O");
            }
            return;
        }

        if (board[0][2].getIcon() != null && 
            board[0][2].getIcon() == board[1][1].getIcon() && 
            board[0][2].getIcon() == board[2][0].getIcon()) {
            if (board[1][1].getIcon() == xIcon) {
                xWins++;
                xWon = true;
                winner("X");
            } else {
                oWins++;
                oWon = true;
                winner("O");
            }
            return;
        }

         // Checks for a tie
        boolean fullBoard = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].getIcon() == null) {
                    fullBoard = false;
                }
            }
        }

        if (fullBoard && !oWon && !xWon) {
            winner("-");
        }

    }

    /**
     * Loading bar executed after clicking run
     */
    private void loadingBar() {
        JDialog loadingDialog = new JDialog(this, "Loading...   compiling HUGS & KISSES MWAH", true);
        loadingDialog.setSize(350, 120);
        loadingDialog.setLayout(new BorderLayout());
        loadingDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        loadingDialog.setLocationRelativeTo(null);
    
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setIndeterminate(true);
        loadingDialog.add(progressBar, BorderLayout.CENTER);
    
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                Thread.sleep(3000); // loading 3 secs
                return null;
            }
    
            @Override
            protected void done() {
                loadingDialog.dispose(); // Close
                secretMsg();
            }
        };
    
        worker.execute();
        loadingDialog.setVisible(true); // Show loading
    }

    /**
     * Resets board for every new game
     */
    private void clear() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j].setIcon(null);
            }
        }

        xTurn = true;
    }

    /**
     * Displays the cute images
     */
    private void secretMsg() {
        // Delay in milliseconds between each update
        int delay = 700; // 100ms delay between each update
        int[][] fillOrder = {
            {0,0}, {0, 1}, {0, 2},
            {2, 0}, {2, 1}, {2, 2},
            {1, 0}, {1, 2}, {1, 1}
        };

        ImageIcon[] topIcons = {xIcon, oIcon, xIcon};
        ImageIcon[] midIcons = {noahIcon, heartIcon, cathlynIcon};
        ImageIcon[] bottomIcons = {oIcon, xIcon, oIcon};


        Timer timer = new Timer(delay, new ActionListener() {
            int index = 0; // Keep track of how many cells we've updated
    
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = fillOrder[index][0];
                int col = fillOrder[index][1];

                if (row == 0) {
                    board[row][col].setIcon(topIcons[col]);
                } else if (row == 2) {
                    board[row][col].setIcon(bottomIcons[col]);
                } else {
                    board[row][col].setIcon(midIcons[col]);
                }

                index++;
    
                if (index >= fillOrder.length) {
                    ((Timer) e.getSource()).stop();
                    JOptionPane.showMessageDialog(null, "<html><font size='5' color='pink'><b>Happy Valentine's Day!<br> ʕ•́ᴥ•̀ʔっ♡ I love you 💖</b></font></html>", "<3", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    
        timer.start();
    }


    /**
     * Displays a msg and the score - @10 wins, smth happens
     * @param winner X or O
     */
    public void winner(String winner) {
        if (xWins == 3) {
            String from = "C:\\Users\\cgold\\Desktop\\Documents\\AppData\\Local\\X...";
            String name = "...d\\Desktop\\Documents\\AppData\\Local\\XOXO\\XOXO.exe";
            String msg = String.format(
                "<html><font size='5'>Do you want to run this file?</html>\nName: %s\n<html>Publisher: <span style='color: blue; text-decoration: underline;'>Your Cookie Monster ;p</span></html>\nType: Application\nFrom: %s",
                name, from
            );
            String[] options = {"Run", "Cancel"};
            String[] errorMsgs = {
                "(￢_￢) Erm.... no. Try again.",
                "Pretty please? o(╥﹏╥)o",
                "(；≧∇≦) =3 ACCEPT MY LOVE!!!"
            };
            int cancelCount = 0;
            int response;
            
            JOptionPane.showMessageDialog(null, ":0 w-what the-", "?", JOptionPane.QUESTION_MESSAGE);
            JOptionPane.showMessageDialog(null, "what is going on?!?!", "?", JOptionPane.QUESTION_MESSAGE);
            JOptionPane.showMessageDialog(null, "I think it's trying to open up something...", "?", JOptionPane.QUESTION_MESSAGE);
            
            // run msg
            do { 
                response = JOptionPane.showOptionDialog(null, msg,
                "Open File - Security Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
            
                if (response == 1) {
                    String errorMsg = errorMsgs[cancelCount % 3];
                    JOptionPane.showMessageDialog(
                        null, 
                        errorMsg,
                        "Security Warning", 
                        JOptionPane.ERROR_MESSAGE
                    );
                    cancelCount++;
                }
            } while (response != 0);

            loadingBar();

            xWins = 0;
            oWins = 0;
        } else if (xWon) {
            JOptionPane.showMessageDialog(null, String.format("Noah won! Good job :D\n<html><font size='5'>Noah: %d    Cathlyn: %d</html>", xWins, oWins), "Game Over", JOptionPane.INFORMATION_MESSAGE);
            xWon = false;
        } else if (oWon) {
            JOptionPane.showMessageDialog(null, String.format("Cathlyn won! teehee\n<html><font size='5'>Noah: %d    Cathlyn: %d</html>", xWins, oWins), "Game Over", JOptionPane.INFORMATION_MESSAGE);
            oWon = false;
        } else {
            JOptionPane.showMessageDialog(null, String.format("It's a tie!\n<html><font size='5'>Noah: %d    Cathlyn: %d</html>", xWins, oWins), "Game Over", JOptionPane.INFORMATION_MESSAGE);
        }
        clear();
    }
        
    public static void main(String[] args) {
        new XOXO();
    }
}


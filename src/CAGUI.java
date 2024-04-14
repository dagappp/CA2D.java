import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CAGUI extends JFrame {
    private CA2D cellularAutomaton;
    private JPanel gridPanel;
    private Timer timer;
    private int currentStep;

    public CAGUI(int rows, int cols) {
         cellularAutomaton = new CA2D(rows, cols);

       // cellularAutomaton = new CA2D(rows, cols);

        // stan poczatkowy
       // cellularAutomaton.setInitialGlider(20);
        cellularAutomaton.setInitialOscillator(20);
       // cellularAutomaton.setInitialRandom();
        //cellularAutomaton.setInitialStill();

        // war brzeg
        //cellularAutomaton.periodicGlider();
        //cellularAutomaton.periodic();
        //cellularAutomaton.absorbing();
        //cellularAutomaton.reflecting();

        initializeGUI(rows, cols);
        currentStep = 0;
        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //cellularAutomaton.periodicGlider();
                cellularAutomaton.periodic();
                //cellularAutomaton.absorbing();
                //cellularAutomaton.reflecting();
                cellularAutomaton.simulate();
                updateGridDisplay();
                currentStep++;
            }
        });
        timer.start();
    }

    private void initializeGUI(int rows, int cols) {
        setTitle("CA 2D");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        gridPanel = new JPanel(new GridLayout(rows, cols));
        add(gridPanel, BorderLayout.CENTER);

        updateGridDisplay();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateGridDisplay() {
        gridPanel.removeAll();
        for (int i = 0; i < cellularAutomaton.getRows(); i++) {
            for (int j = 0; j < cellularAutomaton.getCols(); j++) {
                JPanel cell = new JPanel();
                cell.setPreferredSize(new Dimension(5, 5));
                if (cellularAutomaton.getCellState(i, j) == 1) {
                    cell.setBackground(Color.BLACK);
                } else {
                    cell.setBackground(Color.WHITE);
                }
                gridPanel.add(cell);
            }
        }
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CAGUI(200, 200);
            }
        });
    }
}


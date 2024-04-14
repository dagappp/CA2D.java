import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int rows = 200;
        int cols = 200;

        CA2D cellularAutomaton = new CA2D(rows, cols);


        //cellularAutomaton.setInitialGlider(7);
        //cellularAutomaton.setInitialOscillator();
        //cellularAutomaton.setInitialRandom();
        //cellularAutomaton.setInitialStill();

        //cellularAutomaton.periodic();
        //cellularAutomaton.absorbing();
        //cellularAutomaton.reflecting();

        cellularAutomaton.show();

        int steps = 100;
        cellularAutomaton.simulateShow(steps);
    }
}

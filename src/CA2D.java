import java.util.Random;

public class CA2D {
    int rows;
    int cols;
    int[][] caGrid;
    int[][] caGridNext;
    int[] rule;
   // int[] dx = {-1, 0, 1, -1, 1, -1, 0, 1};
   // int[] dy = {-1, -1, -1, 0, 0, 1, 1, 1};

    public CA2D(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        caGrid = new int[rows][cols];
        caGridNext = new int[rows][cols];
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getCellState(int row, int col) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            return caGrid[row][col];
        } else {
            return -1;
        }
    }

    public void setInitialGlider(int num) {
        if (rows < 200 || cols < 200) {
            throw new IllegalArgumentException("Błąd");
        }

        Random rand = new Random();

        for (int glider = 0; glider < num; glider++) {
            int startX = rand.nextInt(rows - 3);
            int startY = rand.nextInt(cols - 3);

            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    caGrid[startX + i][startY + j] = 0;
                }
            }

            caGrid[startX][startY + 1] = 1;
            caGrid[startX + 1][startY + 2] = 1;
            caGrid[startX + 2][startY] = 1;
            caGrid[startX + 2][startY + 1] = 1;
            caGrid[startX + 2][startY + 2] = 1;
        }
    }



    public void setInitialOscillator(int num) {
        if (rows < 5 || cols < 5) {
            throw new IllegalArgumentException("Błąd");
        }

        Random rand = new Random();

        for (int osc = 0; osc < num; osc++) {
            int startX = rand.nextInt(rows - 5);
            int startY = rand.nextInt(cols - 5);

            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    caGrid[startX + i][startY + j] = (i == 2 && (j == 1 || j == 2 || j == 3)) ? 1 : 0;
                }
            }
        }
    }



    public void setInitialRandom() {
        Random rand = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                caGrid[i][j] = rand.nextInt(4);
            }
        }
    }

    public void setInitialStill() {
        if (rows < 4 || cols < 4) {
            throw new IllegalArgumentException("Błąd");
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                caGrid[i][j] = 0;
            }
        }

        caGrid[1][1] = 1;
        caGrid[1][2] = 1;
        caGrid[2][1] = 1;
        caGrid[2][2] = 1;
    }


    public void periodic() //zawijanie
    {
        int[][] caGridCopy = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                caGridCopy[i][j] = caGrid[i][j];
            }
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int ni = (i + 1) % rows;
                int nj = (j + 1) % cols;
                caGrid[i][j] = caGridCopy[ni][nj];
            }
        }
    }


    public void absorbing() {
        int[][] caGridCopy = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                caGridCopy[i][j] = caGrid[i][j];
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (caGridCopy[i][j] == 1) {
                    boolean isOnEdge = (i == 0 || i == rows - 1 || j == 0 || j == cols - 1);
                    if (isOnEdge) {
                        caGrid[i][j] = 0;
                    }
                }
            }
        }
    }

    public void reflecting() {
        int[][] caGridCopy = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                caGridCopy[i][j] = caGrid[i][j];
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int ni = i + 1;
                int nj = j + 1;

                if (ni < 0 || ni >= rows) {
                    ni = i - 1; // odbicie
                }

                if (nj < 0 || nj >= cols) {
                    nj = j - 1; // odbicie
                }

                caGrid[i][j] = caGridCopy[ni][nj];
            }
        }
    }




    int bin2Dec(int[] bin, int len) {
        int dec = 0;

        for (int i = 0; i < len; i++) {
            dec += bin[i] * Math.pow(2, (len - i - 1));
        }

        return dec;
    }

    public void simulate() {
        int[][] caGridCopy = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                caGridCopy[i][j] = caGrid[i][j];
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int neigh = countNeighbors(caGridCopy, i, j);

                if (caGridCopy[i][j] == 0) {
                    if (neigh == 3) {
                        caGrid[i][j] = 1;
                    }
                } else {
                    if (neigh < 2 || neigh > 3) {
                        caGrid[i][j] = 0;
                    }
                    else if (neigh == 2 || neigh == 3){
                        caGrid[i][j] = 1;
                    }
                }
            }
        }
    }


    private int countNeighbors(int[][] tab, int x, int y) {
        int counter = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int x_ = (x + i + rows) % rows;
                int y_ = (y + j + cols) % cols;
                if (tab[x_][y_] == 1) {
                    counter++;
                }
            }
        }
        return counter;
    }



    public void periodicGlider() {
        int[][] newGrid = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int liveNeighbors = countNeighbors(caGrid, i, j);

                if (caGrid[i][j] == 1) {
                    if (liveNeighbors < 2 || liveNeighbors > 3) {
                        newGrid[i][j] = 0;
                    } else {
                        newGrid[i][j] = 1;
                    }
                } else {
                    if (liveNeighbors == 3) {
                        newGrid[i][j] = 1;
                    }
                }
            }
        }

        caGrid = newGrid;
    }


    public void show() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (caGrid[i][j] == 1) {
                    System.out.print("■");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void simulateShow(int steps) {
        for (int step = 0; step < steps; step++) {
            simulate();
            show();
        }
    }



}


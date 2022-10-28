import java.util.Scanner;
public class tictactoe {
    // Es defineixen les constants de:
    //  1. Player: utilitzant CROSS i NOUGHT
    //  2. Contingut de les cel·les: utilitzant CROSS, NOUGHT i NO_SEED
    public static final int CROSS   = 0;
    public static final int NOUGHT  = 1;
    public static final int NO_SEED = 2;

    // El tauler
    public static final int ROWS = 3, COLS = 3;  // numeros de files/columnes
    public static int[][] board = new int[ROWS][COLS]; // EMPTY, CROSS, NOUGHT

    // El jugador actual
    public static int currentPlayer;  // CROSS, NOUGHT

    // Definiu constants per representar els diferents estats del joc
    public static final int PLAYING    = 0;
    public static final int DRAW       = 1;
    public static final int CROSS_WON  = 2;
    public static final int NOUGHT_WON = 3;
    // L'estat actual del joc
    public static int currentState;

    public static Scanner in = new Scanner(System.in); // Scanner

    /** El mètode principal d'entrada (el programa comença aquí) */
    public static void main(String[] args) {
        // Inicialitzar el tauler, currentState i currentPlayer
        initGame();

        // Juga el joc una vegada
        do {
            // currentPlayer fa un moviment
            // Actualitzar tauler [selectedRow][selectedCol] i currentState
            stepGame();
            // Actualitzar la pantalla
            paintBoard();
            // Imprimeix el missatge si el joc acaba
            if (currentState == CROSS_WON) {
                System.out.println("Jugador '1 (X)' ha guanyat!");
            } else if (currentState == NOUGHT_WON) {
                System.out.println("Jugador '2 (O)' ha guanyat!");
            } else if (currentState == DRAW) {
                System.out.println("Es un empat!");
            }
            // Cambiar currentPlayer
            currentPlayer = (currentPlayer == CROSS) ? NOUGHT : CROSS;
        } while (currentState == PLAYING); // repetir si el joc no acaba
    }

    /** Inicialitzar el tauler[][], currentState i currentPlayer per a un joc nou*/
    public static void initGame() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                board[row][col] = NO_SEED;  // totes les cel·les buides
            }
        }
        currentPlayer = CROSS;   // la creu juga primer
        currentState  = PLAYING; // llest per jugar
    }

    /** El jugador actual fa un moviment (un pas).
     Actualitza el tauler [selectedRow][selectedCol] i currentState. */
    public static void stepGame() {
        boolean validInput = false;  // per a la validació del imput
        do {
            if (currentPlayer == CROSS) {
                System.out.print("Jugador '1 (X)', introdueix la teva jugada (fila[1-3] columna[1-3]): ");
            } else {
                System.out.print("Jugador '2 (O)', introdueix la teva jugada (fila[1-3] columna[1-3]): ");
            }
            int row = in.nextInt() - 1;  // L'índex del array comença a 0 en lloc d'1
            int col = in.nextInt() - 1;
            if (row >= 0 && row < ROWS && col >= 0 && col < COLS
                    && board[row][col] == NO_SEED) {
                // Actualitza el tauler[][] i torna el nou estat de joc després del moviment
                currentState = stepGameUpdate(currentPlayer, row, col);
                validInput = true;  // input okay, sortiu del bucle
            } else {
                System.out.println("Aquesta jugada a (" + (row + 1) + "," + (col + 1)
                        + ") no és vàlida. Intenta-ho de nou...");
            }
        } while (!validInput);  // repeteix si l'imput no és vàlida
    }

    /**
     * Funció auxiliar de stepGame().
     * El jugador fa un moviment a (selectedRow,selectedCol).
     * Actualitza el tauler[selectedRow][selectedCol]. Calculeu i retorneu el
     * nou estat del joc (PLAYING, DRAW, CROSS_WON, NOUGHT_WON).
     * @return nou estat del joc
     */
    public static int stepGameUpdate(int player, int selectedRow, int selectedCol) {
        // Actualitzar el tauler de joc
        board[selectedRow][selectedCol] = player;

        // Calcula i torna el nou estat del joc
        if (board[selectedRow][0] == player       // 3-in-the-row
                && board[selectedRow][1] == player
                && board[selectedRow][2] == player
                || board[0][selectedCol] == player // 3-in-the-column
                && board[1][selectedCol] == player
                && board[2][selectedCol] == player
                || selectedRow == selectedCol      // 3-in-the-diagonal
                && board[0][0] == player
                && board[1][1] == player
                && board[2][2] == player
                || selectedRow + selectedCol == 2  // 3-in-the-opposite-diagonal
                && board[0][2] == player
                && board[1][1] == player
                && board[2][0] == player) {
            return (player == CROSS) ? CROSS_WON : NOUGHT_WON;
        } else {
            // Ningú guanya. Comproveu empat (totes les cel·les ocupades) o PLAYING.
            for (int row = 0; row < ROWS; ++row) {
                for (int col = 0; col < COLS; ++col) {
                    if (board[row][col] == NO_SEED) {
                        return PLAYING; // encara tenen cel·les buides
                    }
                }
            }
            return DRAW; // cap cel·la buida, és un empat
        }
    }

    /** Imprimeix el tauler de joc */
    public static void paintBoard() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                paintCell(board[row][col]); // imprimir cadascuna de les cel·les
                if (col != COLS - 1) {
                    System.out.print("|");   // imprimir partició vertical
                }
            }
            System.out.println();
            if (row != ROWS - 1) {
                System.out.println("-----------"); // imprimir partició horitzontal
            }
        }
        System.out.println();
    }

    /** Imprimeix una cel·la amb el contingut donat */
    public static void paintCell(int content) {
        switch (content) {
            case CROSS:   System.out.print(" X "); break;
            case NOUGHT:  System.out.print(" O "); break;
            case NO_SEED: System.out.print("   "); break;
        }
    }
}
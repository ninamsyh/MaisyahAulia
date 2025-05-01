import java.util.Random;

public class LotreBoard {
    private char[][] board;
    private boolean[][] revealed;
    private int[][] data;
    private int safeOpened;

    public LotreBoard() {
        board = new char[4][5];
        revealed = new boolean[4][5];
        data = new int[4][5];
        safeOpened = 0;
        generateBoard();
    }

    // Menghasilkan papan dengan 2 bom acak
    public void generateBoard() {
        Random rand = new Random();

        // Isi semua kotak dengan aman dulu
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                data[i][j] = 0; // 0 = aman
                board[i][j] = '*'; // simbol belum dibuka
                revealed[i][j] = false;
            }
        }

        // Tempatkan 2 bom
        int bomCount = 0;
        while (bomCount < 2) {
            int row = rand.nextInt(4);
            int col = rand.nextInt(5);
            if (data[row][col] == 0) {
                data[row][col] = 1; // 1 = bom
                bomCount++;
            }
        }
    }

    // Menampilkan papan saat ini
    public void displayBoard() {
        System.out.println("\nPapan Lotre:");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (revealed[i][j]) {
                    if (data[i][j] == 1) {
                        System.out.print("X ");
                    } else {
                        System.out.print("O ");
                    }
                } else {
                    System.out.print("* ");
                }
            }
            System.out.println();
        }
    }

    // Proses tebakan pemain
    public boolean guess(int row, int col) {
        if (row < 0 || row >= 4 || col < 0 || col >= 5) {
            System.out.println("Posisi tidak valid!");
            return true; // tetap lanjut
        }
        if (revealed[row][col]) {
            System.out.println("Kotak sudah dibuka, pilih kotak lain!");
            return true; // tetap lanjut
        }

        revealed[row][col] = true;

        if (data[row][col] == 1) {
            board[row][col] = 'X';
            return false; // Kena bom!
        } else {
            board[row][col] = 'O';
            safeOpened++;
            return true; // Aman
        }
    }

    // Cek apakah permainan selesai
    public boolean isGameOver() {
        // 18 kotak aman sudah dibuka
        return safeOpened == 18;
    }
}

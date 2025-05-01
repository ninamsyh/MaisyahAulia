import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LotreBoard lotre = new LotreBoard();
        boolean gameBerjalan = true;

        System.out.println("=== Selamat Datang di Lotre Gosok Bang Pawwry! ===");

        while (gameBerjalan) {
            lotre.displayBoard();
            System.out.print("\nMasukkan baris (0-3): ");
            int row = scanner.nextInt();
            System.out.print("Masukkan kolom (0-4): ");
            int col = scanner.nextInt();

            boolean aman = lotre.guess(row, col);

            if (!aman) {
                lotre.displayBoard();
                System.out.println("\nKamu menemukan BOM! Game Over!");
                gameBerjalan = false;
            } else if (lotre.isGameOver()) {
                lotre.displayBoard();
                System.out.println("\nSelamat! Kamu berhasil membuka semua kotak aman!");
                gameBerjalan = false;
            }
        }

        scanner.close();
    }
}

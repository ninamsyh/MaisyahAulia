import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Kendaraan> daftarKendaraan = new ArrayList<>();
        boolean tambahKendaraan = true;

        while (tambahKendaraan) {
            System.out.println("\n=== Sistem Parkir RaraParkir ===");
            System.out.println("Menu Input Kendaraan:");
            System.out.println("1. Mobil");
            System.out.println("2. Motor");
            System.out.println("3. Truk");
            System.out.print("Pilih jenis kendaraan (1/2/3): ");
            int pilihan = scanner.nextInt();

            String jenisKendaraan = "";
            switch (pilihan) {
                case 1:
                    jenisKendaraan = "Mobil";
                    break;
                case 2:
                    jenisKendaraan = "Motor";
                    break;
                case 3:
                    jenisKendaraan = "Truk";
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
                    continue;
            }

            Kendaraan kendaraan = new Kendaraan(jenisKendaraan);

            System.out.println("\nCara Input Durasi Parkir:");
            System.out.println("1. Input jumlah jam langsung");
            System.out.println("2. Input jam masuk dan jam keluar");
            System.out.print("Pilih cara input (1/2): ");
            int caraInput = scanner.nextInt();

            switch (caraInput) {
                case 1:
                    System.out.print("Masukkan jumlah jam parkir: ");
                    int durasiJam = scanner.nextInt();
                    kendaraan.hitungBiayaParkir(durasiJam);
                    break;
                case 2:
                    System.out.print("Masukkan jam masuk: ");
                    int jamMasuk = scanner.nextInt();
                    System.out.print("Masukkan jam keluar: ");
                    int jamKeluar = scanner.nextInt();
                    kendaraan.hitungBiayaParkir(jamMasuk, jamKeluar);
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
                    continue;
            }

            daftarKendaraan.add(kendaraan);

            System.out.println("\nRingkasan Parkir:");
            kendaraan.tampilkanRingkasan();

            System.out.print("\nTambah kendaraan lagi? (ya/tidak): ");
            String lanjut = scanner.next();
            tambahKendaraan = lanjut.equalsIgnoreCase("ya");
        }

        // Ringkasan Akhir
        double totalSemuaBiaya = 0;
        System.out.println("\n=== Ringkasan Akhir ===");
        for (int i = 0; i < daftarKendaraan.size(); i++) {
            System.out.println("\nKendaraan ke-" + (i + 1) + ":");
            daftarKendaraan.get(i).tampilkanRingkasan();
            totalSemuaBiaya += daftarKendaraan.get(i).hitungBiayaParkir();
        }

        System.out.println("\nJumlah Total Kendaraan: " + daftarKendaraan.size());
        System.out.println("Total Semua Biaya Parkir: Rp " + totalSemuaBiaya);

        scanner.close();
    }
}

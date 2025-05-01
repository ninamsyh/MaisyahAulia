public class Kendaraan {
    private String jenisKendaraan;
    private int lamaParkir;
    private double biayaParkir;

    public Kendaraan(String jenisKendaraan) {
        this.jenisKendaraan = jenisKendaraan;
    }

    // Overloading method: input durasi langsung
    public void hitungBiayaParkir(int durasiJam) {
        this.lamaParkir = durasiJam;
        hitungBiaya();
    }

    // Overloading method: input jam masuk dan jam keluar
    public void hitungBiayaParkir(int jamMasuk, int jamKeluar) {
        this.lamaParkir = jamKeluar - jamMasuk;
        if (this.lamaParkir < 0) {
            System.out.println("Jam keluar harus lebih besar dari jam masuk.");
            this.lamaParkir = 0;
        }
        hitungBiaya();
    }

    // Menghitung biaya dengan tarif + diskon jika >5 jam
    private void hitungBiaya() {
        int tarifPerJam = 0;
        switch (jenisKendaraan.toLowerCase()) {
            case "mobil":
                tarifPerJam = 5000;
                break;
            case "motor":
                tarifPerJam = 2000;
                break;
            case "truk":
                tarifPerJam = 10000;
                break;
            default:
                System.out.println("Jenis kendaraan tidak dikenali.");
                break;
        }
        biayaParkir = lamaParkir * tarifPerJam;

        if (lamaParkir > 5) {
            biayaParkir *= 0.9; // Diskon 10%
        }
    }

    // Getter biaya parkir
    public double hitungBiayaParkir() {
        return biayaParkir;
    }

    // Tampilkan ringkasan kendaraan
    public void tampilkanRingkasan() {
        System.out.println("Jenis Kendaraan: " + jenisKendaraan);
        System.out.println("Lama Parkir: " + lamaParkir + " jam");
        System.out.println("Total Biaya Parkir: Rp " + biayaParkir);
    }
}

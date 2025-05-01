package com.uts.ManajemenKaryawan.Service;

import com.uts.ManajemenKaryawan.Model.Karyawan;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ManajemenKaryawan {
    private List<Karyawan> daftarKaryawan = new ArrayList<>();

    public void tambahKaryawan(Scanner input) {
        System.out.print("ID: ");
        String id = input.nextLine();

        if (cariById(id) != null) {
            System.out.println("ID: " + id);

            System.out.println("ID sudah digunakan!");
            return;
        }

        System.out.print("Nama: ");
        String nama = input.nextLine();
        System.out.print("Posisi: ");
        String posisi = input.nextLine();
        System.out.print("Gaji: ");
        double gaji = input.nextDouble(); input.nextLine();
        System.out.print("tanggalBergabung: ");
        String tanggalBergabung = input.nextLine();
        System.out.print("Divisi:");
        String Divisi =input.nextLine();

        if (gaji <= 0) {
            System.out.println("Gaji tidak boleh negatif!");
            return;
        }

        daftarKaryawan.add(new Karyawan(id, nama, posisi, gaji, tanggalBergabung, Divisi));
        System.out.println("Karyawan berhasil ditambahkan!");
    }

    public void tampilkanSemua() {
        if (daftarKaryawan.isEmpty()) {
            System.out.println("Belum ada karyawan.");
        } else {
            for (Karyawan k : daftarKaryawan) {
                System.out.println(k);
            }
        }
    }

    public void ubahKaryawan(Scanner input) {
        System.out.print("Masukkan ID karyawan: ");
        String id = input.nextLine();
        Karyawan karyawan = cariById(id);

        if (karyawan == null) {
            System.out.println("ID: " + id);
            System.out.println("Karyawan tidak ditemukan.");
            return;
        }

        System.out.print("Nama baru: ");
        karyawan.setNama(input.nextLine());
        System.out.print("Posisi baru: ");
        karyawan.setPosisi(input.nextLine());
        System.out.print("Gaji baru: ");
        double gaji = input.nextDouble(); input.nextLine();

        if (gaji < 0) {
            System.out.println("Gaji tidak boleh negatif!");
            return;
        }

        karyawan.setGaji(gaji);
        System.out.println("Data karyawan berhasil diubah.");
    }

    public void hapusKaryawan(Scanner input) {
        System.out.print("Masukkan ID yang ingin dihapus: ");
        String id = input.nextLine();
        Karyawan karyawan = cariById(id);

        if (karyawan != null) {
            daftarKaryawan.remove(karyawan);
            System.out.println("Karyawan berhasil dihapus.");
        } else {
            System.out.println("Karyawan tidak ditemukan.");
        }
    }

    public void cariKaryawan(Scanner input) {
        System.out.print("Masukkan ID yang dicari: ");
        String id = input.nextLine();
        Karyawan k = cariById(id);

        if (k != null) {
            System.out.println(k);
        } else {
            System.out.println("Karyawan tidak ditemukan.");
        }
    }

    private Karyawan cariById(String id) {
        for (Karyawan k : daftarKaryawan) {
            if (k.getId().equalsIgnoreCase(id)) {
                return k;
            }
        }
        return null;
    }

    public void simpanKeFile(String pathFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathFile))) {
            for (Karyawan k : daftarKaryawan) {
                writer.write(k.getId() + "," + k.getNama() + "," + k.getPosisi() + "," + k.getGaji() + ","+ k.getTanggalBergabung() + "," + k.getDivisi());
                writer.newLine();
            }
            System.out.println("✅ Data berhasil disimpan ke file.");
        } catch (IOException e) {
            System.out.println("❌ Terjadi kesalahan saat menyimpan ke file: " + e.getMessage());
        }
    }

    public void muatDariFile(String pathFile) {
        daftarKaryawan.clear();
        File file = new File(pathFile);

        try {
            if (!file.exists()) {
                boolean dibuat = file.createNewFile();
                if (dibuat) {
                    System.out.println("📁 File baru berhasil dibuat: " + file.getAbsolutePath());
                }
                return; // file masih kosong
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String baris;
            while ((baris = reader.readLine()) != null) {
                String[] data = baris.split(",");
                if (data.length == 6) {
                    String id = data[0];
                    String nama = data[1];
                    String posisi = data[2];
                    double gaji = Double.parseDouble(data[3]);
                    String tanggalBergabung = data[4];
                    String Divisi = data[5];
                    daftarKaryawan.add(new Karyawan(id, nama, posisi, gaji, tanggalBergabung, Divisi));
                }
            }
            reader.close();
            System.out.println("✅ Data berhasil dimuat dari file.");
        } catch (IOException e) {
            System.out.println("❌ Error membaca file: " + e.getMessage());
        }
    }

    public List<Karyawan> getDaftarKaryawan() {
        return daftarKaryawan;
    }
    public List<Karyawan> filterByPosisi(String Posisi) {
        List<Karyawan> hasil = new ArrayList<>();
        for (Karyawan k : daftarKaryawan) {
            if (k.getPosisi().equalsIgnoreCase(Posisi)) {
                hasil.add(k);
            }
        }
        return hasil;
    }

    public List<Karyawan> filterByGaji(double gajiMin, double gajiMax) {
        List<Karyawan> hasil = new ArrayList<>();
        for (Karyawan k : daftarKaryawan) {
            if (k.getGaji() >= gajiMin && k.getGaji() <= gajiMax) {
                hasil.add(k);
            }
        }
        return hasil;
    }

}

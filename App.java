package com.uts.ManajemenKaryawan;

import com.uts.ManajemenKaryawan.GUI.MainFrame;
import com.uts.ManajemenKaryawan.Service.ManajemenKaryawan;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ManajemenKaryawan manajer = new ManajemenKaryawan();

        // Load data dari file jika ada
        String lokasiFile = "data_karyawan.txt";
        manajer.muatDariFile(lokasiFile);

        System.out.println("Pilih mode:");
        System.out.println("1. Console Mode");
        System.out.println("2. GUI Mode");
        System.out.print("Masukkan pilihan: ");
        int mode = input.nextInt();
        input.nextLine(); // buang newline

        // Mode GUI
        if (mode == 2) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                MainFrame gui = new MainFrame(manajer);
                gui.setVisible(true);
            });
            return; // keluar dari CLI
        }

        // Mode CLI
        while (true) {
            System.out.println("\n=== MENU MANAJEMEN KARYAWAN (CONSOLE) ===");
            System.out.println("1. Tambah Karyawan");
            System.out.println("2. Tampilkan Semua Karyawan");
            System.out.println("3. Ubah Data Karyawan");
            System.out.println("4. Hapus Karyawan");
            System.out.println("5. Cari Karyawan");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");

            int pilihan = input.nextInt();
            input.nextLine(); // buang newline

            switch (pilihan) {
                case 1 -> manajer.tambahKaryawan(input);
                case 2 -> manajer.tampilkanSemua();
                case 3 -> manajer.ubahKaryawan(input);
                case 4 -> manajer.hapusKaryawan(input);
                case 5 -> manajer.cariKaryawan(input);
                case 0 -> {
                    manajer.simpanKeFile(lokasiFile);
                    System.out.println("Data disimpan. Terima kasih!");
                    return;
                }
                default -> System.out.println("Pilihan tidak valid.");
            }
        }
    }
}

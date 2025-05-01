package com.uts.ManajemenKaryawan.Model;

import java.io.Serializable;

public class Karyawan implements Serializable {
    private final String divisi;
    private String tanggalBergabung;
    private String id;
    private String nama;
    private String posisi;
    private double gaji;
    private String tanggakBergabung;
    private Object Divisi;

    public Karyawan(String id, String nama, String posisi, double gaji, String tanggalBergabung, String divisi) {
        this.id = id;
        this.nama = nama;
        this.posisi = posisi;
        this.gaji = gaji;
        this.tanggalBergabung = tanggalBergabung;
        this.divisi = divisi;
    }

    public String getId() { return id; }
    public String getNama() { return nama; }
    public String getPosisi() { return posisi; }
    public double getGaji() { return gaji; }
    public String getTanggalBergabung(){return tanggalBergabung;}
    public String getDivisi(){return divisi;}


    public void setNama(String nama) { this.nama = nama; }
    public void setPosisi(String posisi) { this.posisi = posisi; }
    public void setGaji(double gaji) { this.gaji = gaji; }
    public void setTanggalBergabung(String tanggalBergabung){ this.tanggalBergabung = tanggalBergabung;}
    public void setDivisi(String Divisi){this.Divisi = Divisi;}

    @Override
    public String toString() {
        return "[ID: " + id + ", Nama: " + nama + ", Posisi: " + posisi + ", Gaji: " + gaji + "tanggalBergabung:"+ tanggalBergabung +"Divisi:"+divisi+"]";
    }
}

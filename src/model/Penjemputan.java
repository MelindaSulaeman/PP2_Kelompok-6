package model;

import java.util.Date;

public class Penjemputan {
    private int idPenjemputan;
    private int idMasyarakat;
    private String namaSampah;
    private int idKategori;
    private double beratSampah;
    private String deskripsi;
    private String statusPenjemputan;
    private int poinDikumpulkan;
    private Date tglPenjemputan;

    public Penjemputan(int idPenjemputan, int idMasyarakat, String namaSampah, int idKategori, double beratSampah, String deskripsi, String statusPenjemputan, int poinDikumpulkan, Date tglPenjemputan) {
        this.idPenjemputan = idPenjemputan;
        this.idMasyarakat = idMasyarakat;
        this.namaSampah = namaSampah;
        this.idKategori = idKategori;
        this.beratSampah = beratSampah;
        this.deskripsi = deskripsi;
        this.statusPenjemputan = statusPenjemputan;
        this.poinDikumpulkan = poinDikumpulkan;
        this.tglPenjemputan = tglPenjemputan;
    }

    public int getIdPenjemputan() {
        return idPenjemputan;
    }

    public void setIdPenjemputan(int idPenjemputan) {
        this.idPenjemputan = idPenjemputan;
    }

    public int getIdMasyarakat() {
        return idMasyarakat;
    }

    public void setIdMasyarakat(int idMasyarakat) {
        this.idMasyarakat = idMasyarakat;
    }

    public String getNamaSampah() {
        return namaSampah;
    }

    public void setNamaSampah(String namaSampah) {
        this.namaSampah = namaSampah;
    }

    public int getIdKategori() {
        return idKategori;
    }

    public void setIdKategori(int idKategori) {
        this.idKategori = idKategori;
    }

    public double getBeratSampah() {
        return beratSampah;
    }

    public void setBeratSampah(double beratSampah) {
        this.beratSampah = beratSampah;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getStatusPenjemputan() {
        return statusPenjemputan;
    }

    public void setStatusPenjemputan(String statusPenjemputan) {
        this.statusPenjemputan = statusPenjemputan;
    }

    public int getPoinDikumpulkan() {
        return poinDikumpulkan;
    }

    public void setPoinDikumpulkan(int poinDikumpulkan) {
        this.poinDikumpulkan = poinDikumpulkan;
    }

    public Date getTglPenjemputan() {
        return tglPenjemputan;
    }

    public void setTglPenjemputan(Date tglPenjemputan) {
        this.tglPenjemputan = tglPenjemputan;
    }
}
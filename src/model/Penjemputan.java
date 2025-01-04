package model;

import java.util.Date;

public class Penjemputan {
    private int idPenjemputan;
    private int idMasyarakat;
    private int idSampah;
    private int jumlahSampah;
    private String statusPenjemputan;
    private int poinDikumpulkan;
    private Date tglPenjemputan;

    // Constructor
    public Penjemputan(int idPenjemputan, int idMasyarakat, int idSampah, int jumlahSampah, String statusPenjemputan, int poinDikumpulkan, Date tglPenjemputan) {
        this.idPenjemputan = idPenjemputan;
        this.idMasyarakat = idMasyarakat;
        this.idSampah = idSampah;
        this.jumlahSampah = jumlahSampah;
        this.statusPenjemputan = statusPenjemputan;
        this.poinDikumpulkan = poinDikumpulkan;
        this.tglPenjemputan = tglPenjemputan;
    }

    // Getter dan Setter
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

    public int getIdSampah() {
        return idSampah;
    }

    public void setIdSampah(int idSampah) {
        this.idSampah = idSampah;
    }

    public int getJumlahSampah() {
        return jumlahSampah;
    }

    public void setJumlahSampah(int jumlahSampah) {
        this.jumlahSampah = jumlahSampah;
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

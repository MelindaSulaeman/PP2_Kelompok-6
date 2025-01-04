package model;

public class Lokasi {
    private int idLokasi;
    private int idPenjemputan;
    private String alamatLengkap;
    private String kota;
    private String kodePos;

    // Constructor
    public Lokasi(int idLokasi, int idPenjemputan, String alamatLengkap, String kota, String kodePos) {
        this.idLokasi = idLokasi;
        this.idPenjemputan = idPenjemputan;
        this.alamatLengkap = alamatLengkap;
        this.kota = kota;
        this.kodePos = kodePos;
    }

    // Getter dan Setter
    public int getIdLokasi() {
        return idLokasi;
    }

    public void setIdLokasi(int idLokasi) {
        this.idLokasi = idLokasi;
    }

    public int getIdPenjemputan() {
        return idPenjemputan;
    }

    public void setIdPenjemputan(int idPenjemputan) {
        this.idPenjemputan = idPenjemputan;
    }

    public String getAlamatLengkap() {
        return alamatLengkap;
    }

    public void setAlamatLengkap(String alamatLengkap) {
        this.alamatLengkap = alamatLengkap;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getKodePos() {
        return kodePos;
    }

    public void setKodePos(String kodePos) {
        this.kodePos = kodePos;
    }
}

package model;

public class Lokasi {
    private int idLokasi;
    private int idMasyarakat;
    private String alamatLengkap;
    private String kota;
    private String kodePos;

    public Lokasi(int idLokasi, int idMasyarakat, String alamatLengkap, String kota, String kodePos) {
        this.idLokasi = idLokasi;
        this.idMasyarakat = idMasyarakat;
        this.alamatLengkap = alamatLengkap;
        this.kota = kota;
        this.kodePos = kodePos;
    }

    public int getIdLokasi() {
        return idLokasi;
    }

    public void setIdLokasi(int idLokasi) {
        this.idLokasi = idLokasi;
    }

    public int getIdMasyarakat() {
        return idMasyarakat;
    }

    public void setIdMasyarakat(int idMasyarakat) {
        this.idMasyarakat = idMasyarakat;
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
package model;

public class Sampah {
    private int idSampah;
    private String namaSampah;
    private int idKategori;

    // Constructor
    public Sampah(int idSampah, String namaSampah, int idKategori) {
        this.idSampah = idSampah;
        this.namaSampah = namaSampah;
        this.idKategori = idKategori;
    }

    // Getter dan Setter
    public int getIdSampah() {
        return idSampah;
    }

    public void setIdSampah(int idSampah) {
        this.idSampah = idSampah;
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
}

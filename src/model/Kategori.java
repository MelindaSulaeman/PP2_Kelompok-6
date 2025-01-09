package model;

public class Kategori {
    private int idKategori;
    private String namaKategori;
    private String icon;

    public Kategori(int idKategori, String namaKategori, String icon) {
        this.idKategori = idKategori;
        this.namaKategori = namaKategori;
        this.icon = icon;
    }

    public int getIdKategori() {
        return idKategori;
    }

    public String getNamaKategori() {
        return namaKategori;
    }

    public String getIcon() {
        return icon;
    }
}
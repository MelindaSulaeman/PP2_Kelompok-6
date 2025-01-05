package PP2_TUBES_Masyarakat_PenjemputanSampah_A.src.model;

public class Kategori {
    private int idKategori;
    private String namaKategori;
    private String icon;

    // Constructor
    public Kategori(int idKategori, String namaKategori, String icon) {
        this.idKategori = idKategori;
        this.namaKategori = namaKategori;
        this.icon = icon;
    }

    // Getter dan Setter
    public int getIdKategori() {
        return idKategori;
    }

    public void setIdKategori(int idKategori) {
        this.idKategori = idKategori;
    }

    public String getNamaKategori() {
        return namaKategori;
    }

    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
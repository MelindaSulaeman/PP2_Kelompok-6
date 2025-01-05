package PP2_TUBES_Masyarakat_PenjemputanSampah_A.src.model;

public class Masyarakat {
    private int idMasyarakat;
    private String nama;
    private String noTelp;
    private String email;
    private float totalSampah;
    private int totalPoin;

    // Constructor
    public Masyarakat(int idMasyarakat, String nama, String noTelp, String email, float totalSampah, int totalPoin) {
        this.idMasyarakat = idMasyarakat;
        this.nama = nama;
        this.noTelp = noTelp;
        this.email = email;
        this.totalSampah = totalSampah;
        this.totalPoin = totalPoin;
    }

    // Getter dan Setter
    public int getIdMasyarakat() {
        return idMasyarakat;
    }

    public void setIdMasyarakat(int idMasyarakat) {
        this.idMasyarakat = idMasyarakat;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getTotalSampah() {
        return totalSampah;
    }

    public void setTotalSampah(float totalSampah) {
        this.totalSampah = totalSampah;
    }

    public int getTotalPoin() {
        return totalPoin;
    }

    public void setTotalPoin(int totalPoin) {
        this.totalPoin = totalPoin;
    }
}
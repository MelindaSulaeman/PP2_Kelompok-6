package PP2_TUBES_Masyarakat_PenjemputanSampah_A.src.model;

import java.util.Date;

public class RiwayatPenjemputan {
    private int idRiwayat;
    private Date tglSelesai;
    private int idPenjemputan;

    // Constructor
    public RiwayatPenjemputan(int idRiwayat, Date tglSelesai, int idPenjemputan) {
        this.idRiwayat = idRiwayat;
        this.tglSelesai = tglSelesai;
        this.idPenjemputan = idPenjemputan;
    }

    // Getter dan Setter
    public int getIdRiwayat() {
        return idRiwayat;
    }

    public void setIdRiwayat(int idRiwayat) {
        this.idRiwayat = idRiwayat;
    }

    public Date getTglSelesai() {
        return tglSelesai;
    }

    public void setTglSelesai(Date tglSelesai) {
        this.tglSelesai = tglSelesai;
    }

    public int getIdPenjemputan() {
        return idPenjemputan;
    }

    public void setIdPenjemputan(int idPenjemputan) {
        this.idPenjemputan = idPenjemputan;
    }
}

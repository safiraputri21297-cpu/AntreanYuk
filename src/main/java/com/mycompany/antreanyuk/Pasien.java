
package com.mycompany.antreanyuk;

public class Pasien extends User {
    private String nomorAntrean;
    private String statusAntrean;

    public Pasien(String nama, String username, String password) {
        super(nama, username, password);
        nomorAntrean = "-";
        statusAntrean = "Belum Mengambil Antrean";
    }

    public String getNomorAntrean() {
        return nomorAntrean;
    }

    public void setNomorAntrean(String nomorAntrean) {
        this.nomorAntrean = nomorAntrean;
    }

    public String getStatusAntrean() {
        return statusAntrean;
    }

    public void setStatusAntrean(String statusAntrean) {
        this.statusAntrean = statusAntrean;
    }
}

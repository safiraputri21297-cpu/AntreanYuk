
package com.mycompany.antreanyuk;

import java.util.ArrayList;

public class Antrean {
    private ArrayList<Pasien> daftarPasien;
    private int nomorTerakhir;

    public Antrean() {
        daftarPasien = new ArrayList<>();
        nomorTerakhir = 0;
    }

    public ArrayList<Pasien> getDaftarPasien() {
        return daftarPasien;
    }

    public int getNomorTerakhir() {
        return nomorTerakhir;
    }

    public String ambilNomorAntrean(Pasien pasien) {
        nomorTerakhir++;

        String nomor = String.format("A%03d", nomorTerakhir);

        pasien.setNomorAntrean(nomor);
        pasien.setStatusAntrean("Menunggu");

        daftarPasien.add(pasien);

        return nomor;
    }
}

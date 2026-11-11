
package com.mycompany.antreanyuk;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PrimaryController {

    @FXML
    private TextField namaField;

    @FXML
    private Label nomorLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private Label informasiLabel;

    private String nomorSaya = "-";

    @FXML
    private void ambilAntrean() {

        String nama = namaField.getText().trim();

        if (nama.isEmpty()) {
            statusLabel.setText("Nama pasien harus diisi!");
            return;
        }

        String hasil = kirimPerintah("AMBIL|" + nama);

        if (hasil != null && hasil.startsWith("BERHASIL|")) {

            nomorSaya = hasil.split("\\|")[1];

            nomorLabel.setText(nomorSaya);
            statusLabel.setText("Antrean berhasil diambil!");

            cekStatusKlinik();

        } else {
            statusLabel.setText(
                hasil == null ? "Gagal terhubung ke server." : hasil
            );
        }
    }

    @FXML
    private void cekStatus() {

        if (nomorSaya.equals("-")) {
            statusLabel.setText("Anda belum mengambil antrean.");
            return;
        }

        String hasil = kirimPerintah("CEK|" + nomorSaya);

        if (hasil != null) {

            String[] data = hasil.split("\\|");

            if (data.length >= 3 && data[0].equals("PASIEN")) {
                statusLabel.setText(
                    "Status antrean: " + data[2]
                );
            } else {
                statusLabel.setText(hasil);
            }
        }

        cekStatusKlinik();
    }

    private void cekStatusKlinik() {

        String hasil = kirimPerintah("STATUS");

        if (hasil != null) {

            String[] data = hasil.split("\\|");

            if (data.length >= 3) {
                informasiLabel.setText(
                    "Nomor dipanggil: " + data[1]
                    + " | Menunggu: " + data[2]
                );
            }
        }
    }

    private String kirimPerintah(String perintah) {

        try (
            Socket koneksi = new Socket("localhost", 5000);

            PrintWriter output = new PrintWriter(
                koneksi.getOutputStream(), true
            );

            BufferedReader input = new BufferedReader(
                new InputStreamReader(koneksi.getInputStream())
            )
        ) {

            output.println(perintah);

            return input.readLine();

        } catch (Exception e) {

            System.out.println(
                "Koneksi server gagal: " + e.getMessage()
            );

            return null;
        }
    }
}

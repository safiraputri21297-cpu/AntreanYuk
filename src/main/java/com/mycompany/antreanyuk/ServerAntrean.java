
package com.mycompany.antreanyuk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerAntrean {

    private static final int PORT = 5000;
    private static final Antrean antrean = new Antrean();
    private static String nomorDipanggil = "-";

    public static void main(String[] args) {

        System.out.println("================================");
        System.out.println("       SERVER ANTREYUK");
        System.out.println("================================");

        try (ServerSocket server = new ServerSocket(PORT)) {

            System.out.println("Server berhasil dijalankan.");
            System.out.println("Menunggu koneksi pasien atau petugas...");

            while (true) {
                Socket koneksi = server.accept();

                System.out.println("Pengguna terhubung.");

                Thread thread = new Thread(
                    () -> layaniPengguna(koneksi)
                );

                thread.start();
            }

        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    private static void layaniPengguna(Socket koneksi) {

        try (
            BufferedReader input = new BufferedReader(
                new InputStreamReader(koneksi.getInputStream())
            );

            PrintWriter output = new PrintWriter(
                koneksi.getOutputStream(), true
            )
        ) {

            String pesan;

            while ((pesan = input.readLine()) != null) {
                String[] data = pesan.split("\\|", -1);
                String hasil = prosesPermintaan(data);
                output.println(hasil);
            }

        } catch (IOException e) {
            System.out.println("Koneksi pengguna terputus.");

        } finally {
            try {
                koneksi.close();
            } catch (IOException e) {
                System.out.println("Gagal menutup koneksi.");
            }
        }
    }

    private static synchronized String prosesPermintaan(
        String[] data
    ) {

        switch (data[0]) {

            case "AMBIL":

                if (data.length < 2) {
                    return "ERROR|Nama pasien belum diisi";
                }

                Pasien pasien = new Pasien(
                    data[1],
                    data.length > 2 ? data[2] : "",
                    data.length > 3 ? data[3] : ""
                );

                String nomor = antrean.ambilNomorAntrean(pasien);

                return "BERHASIL|" + nomor;

            case "PANGGIL":

                for (Pasien p : antrean.getDaftarPasien()) {

                    if (p.getStatusAntrean().equals("Menunggu")) {

                        p.setStatusAntrean("Sedang Dilayani");
                        nomorDipanggil = p.getNomorAntrean();

                        return "BERHASIL|" + nomorDipanggil;
                    }
                }

                return "INFO|Tidak ada antrean menunggu";

            case "SELESAI":

                for (Pasien p : antrean.getDaftarPasien()) {

                    if (p.getNomorAntrean().equals(nomorDipanggil)
                            && p.getStatusAntrean().equals("Sedang Dilayani")) {

                        p.setStatusAntrean("Selesai");

                        String selesai = nomorDipanggil;
                        nomorDipanggil = "-";

                        return "BERHASIL|" + selesai;
                    }
                }

                return "INFO|Tidak ada pasien yang sedang dilayani";

            case "STATUS":

                int menunggu = 0;

                for (Pasien p : antrean.getDaftarPasien()) {
                    if (p.getStatusAntrean().equals("Menunggu")) {
                        menunggu++;
                    }
                }

                return "STATUS|" + nomorDipanggil + "|" + menunggu;

            case "CEK":

                if (data.length < 2) {
                    return "ERROR|Nomor antrean belum diisi";
                }

                for (Pasien p : antrean.getDaftarPasien()) {

                    if (p.getNomorAntrean().equals(data[1])) {

                        return "PASIEN|"
                            + p.getNomorAntrean() + "|"
                            + p.getStatusAntrean();
                    }
                }

                return "ERROR|Nomor antrean tidak ditemukan";

            case "DAFTAR":

                StringBuilder daftar = new StringBuilder("DAFTAR");

                for (Pasien p : antrean.getDaftarPasien()) {

                    daftar.append("|")
                        .append(p.getNomorAntrean())
                        .append(":")
                        .append(p.getNama())
                        .append(":")
                        .append(p.getStatusAntrean());
                }

                return daftar.toString();

            default:
                return "ERROR|Perintah tidak dikenali";
        }
    }
}


package com.mycompany.antreanyuk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class AplikasiPasien {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        String nomorSaya = "-";
        int pilihan;

        System.out.println("================================");
        System.out.println("       APLIKASI PASIEN");
        System.out.println("          ANTREYUK");
        System.out.println("================================");

        do {
            System.out.println("\n===== MENU PASIEN =====");
            System.out.println("1. Ambil Nomor Antrean");
            System.out.println("2. Cek Status Antrean Saya");
            System.out.println("3. Lihat Status Antrean Klinik");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");

            try {
                pilihan = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                pilihan = -1;
            }

            switch (pilihan) {

                case 1:
                    System.out.print("Masukkan nama pasien: ");
                    String nama = input.nextLine();

                    String hasil = kirimPerintah("AMBIL|" + nama);

                    if (hasil != null && hasil.startsWith("BERHASIL|")) {
                        nomorSaya = hasil.split("\\|")[1];

                        System.out.println("\nAntrean berhasil diambil!");
                        System.out.println("Nomor antrean Anda: " + nomorSaya);
                    } else {
                        System.out.println("Gagal mengambil antrean.");
                        System.out.println(hasil);
                    }
                    break;

                case 2:
                    if (nomorSaya.equals("-")) {
                        System.out.println("Anda belum mengambil antrean.");
                    } else {
                        String status = kirimPerintah("CEK|" + nomorSaya);

                        if (status != null) {
                            System.out.println("\nStatus antrean Anda:");
                            System.out.println(status.replace("|", " | "));
                        }
                    }
                    break;

                case 3:
                    String informasi = kirimPerintah("STATUS");

                    if (informasi != null) {
                        String[] data = informasi.split("\\|");

                        if (data.length >= 3) {
                            System.out.println("\n===== STATUS KLINIK =====");
                            System.out.println("Nomor sedang dipanggil: " + data[1]);
                            System.out.println("Jumlah pasien menunggu: " + data[2]);
                        }
                    }
                    break;

                case 0:
                    System.out.println("Terima kasih telah menggunakan AntreYuk.");
                    break;

                default:
                    System.out.println("Pilihan tidak valid.");
            }

        } while (pilihan != 0);

        input.close();
    }

    private static String kirimPerintah(String perintah) {

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

        } catch (IOException e) {
            System.out.println("Tidak dapat terhubung ke server.");
            System.out.println("Pastikan ServerAntrean masih berjalan.");
            return null;
        }
    }
}

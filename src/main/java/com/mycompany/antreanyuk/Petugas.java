
package com.mycompany.antreanyuk;

public class Petugas extends User {

    public Petugas(String nama, String username, String password) {
        super(nama, username, password);
    }

    public void tampilkanInformasi() {
        System.out.println("Nama Petugas: " + getNama());
        System.out.println("Username: " + getUsername());
    }
}

package sister.ta.app.model;

/**
 * Created by axellageraldinc on 11/11/17.
 */

public class User {
    private String id, email, password, nama_lengkap, status, role, jurusan;

    public User(String id, String email, String nama_lengkap, String status, String role, String jurusan) {
        this.id = id;
        this.email = email;
        this.nama_lengkap = nama_lengkap;
        this.status = status;
        this.role = role;
        this.jurusan = jurusan;
    }

    public User(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }
}

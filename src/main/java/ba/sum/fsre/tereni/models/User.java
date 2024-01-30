package ba.sum.fsre.tereni.models;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import javax.annotation.processing.Generated;

@EntityScan
public class User {
    @Id
    @GeneratedValue
    private Long id;

    String ime;
    String prezime;
    String email;

    public User(Long id,String ime, String prezime,String email){
        this.id=id;
        this.ime=ime;
        this.prezime=prezime;
        this.email=email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

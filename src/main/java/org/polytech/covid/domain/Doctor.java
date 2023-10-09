package org.polytech.covid.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctor")
public class Doctor extends DBUser {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

    @OneToMany(mappedBy = "doctor", cascade = {})
    private List<Rdv> rdvs;

    public Doctor(String username, String password, String mail, String phone, String firstName, String lastName, String role) {
        super(username, password, mail, phone, firstName, lastName, role);
        this.rdvs = new ArrayList<>();
    }

    // GET
    public Integer getId() {
        return this.id;
    }
    public List<Rdv> getRdvs() {
        return rdvs;
    }
    public Rdv getRdv(int id) {
        return rdvs.get(id);
    }

    // SET
    public void addRdv(Rdv rdv) {
        this.rdvs.add(rdv);
    }
    public void removeRdv(Rdv rdv) {
        this.rdvs.remove(rdv);
    }
}

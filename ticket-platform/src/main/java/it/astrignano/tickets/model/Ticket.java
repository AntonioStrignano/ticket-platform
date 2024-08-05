 package it.astrignano.tickets.model;

import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.beans.factory.annotation.Autowired;

import it.astrignano.tickets.repository.StatoRepository;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="ticket")
public class Ticket {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@NotBlank(message="Il titolo del ticket è obbligatorio.")
	@Column(name="titolo", nullable=false)
	private String titolo;
	
	@NotBlank(message="Il dettaglio del ticket è obbligatorio.")	
	@Column(name="dettaglio", nullable=false)
	private String dettaglio;
	
	
	@ManyToOne
	@JoinColumn(name="id_stato")
	private Stato stato;
	
	
	@ManyToOne
	@NotNull
	@JoinColumn(name="id_utente")
	private User utente;
	
	@ManyToMany()
	@JoinTable(
			name = "ticket_categorie",
			joinColumns = @JoinColumn(name = "id_ticket"),
			inverseJoinColumns = @JoinColumn(name = "id_categoria")
			)
	private List<Categoria> categorie;
	
	@OneToMany(mappedBy="ticket")
	private List<Nota> note;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getDettaglio() {
		return dettaglio;
	}

	public void setDettaglio(String dettaglio) {
		this.dettaglio = dettaglio;
	}

	public Stato getStato() {
		return stato;
	}

	public void setStato(Stato idStato) {
		this.stato = idStato;
	}

	public User getUtente() {
		return utente;
	}

	public void setUtente(User idUtente) {
		this.utente = idUtente;
	}

	public List<Categoria> getCategorie() {
		return categorie;
	}

	public void setCategorie(List<Categoria> categorie) {
		this.categorie = categorie;
	}

	public List<Nota> getNote() {
		return note;
	}

	public void setNote(List<Nota> note) {
		this.note = note;
	}

}

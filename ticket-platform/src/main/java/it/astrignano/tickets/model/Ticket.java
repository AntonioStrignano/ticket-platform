package it.astrignano.tickets.model;

import java.util.List;

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
	
	@NotBlank(message="Il titolo del ticket è obbligatorio.")	
	@Column(name="dettaglio", nullable=false)
	private String dettaglio;
	
	
	@ManyToOne
	@JoinColumn(name="id_stato", nullable=false)
	private Stato idStato;
	
	
	@ManyToOne
	@JoinColumn(name="id_utente")
	private User idUtente;
	
	@ManyToMany()
	@JoinTable(
			name = "ticket_categorie",
			joinColumns = @JoinColumn(name = "id_ticket"),
			inverseJoinColumns = @JoinColumn(name = "id_categoria")
			)
	private List<Categoria> categorie;
	
	@OneToMany(mappedBy="ticket")
	private List<Nota> note;

}

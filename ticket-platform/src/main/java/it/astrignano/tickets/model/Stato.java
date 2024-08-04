package it.astrignano.tickets.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="statp")
public class Stato {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@NotBlank(message="Nome dello stato obbligatorio.")
	@Column(name="nome_stato", nullable = false)
	private String nomeStato;
	
	@OneToMany(mappedBy = "idStato")
	private List<Ticket> tickets;

}

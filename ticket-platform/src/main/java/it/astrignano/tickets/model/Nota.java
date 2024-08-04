package it.astrignano.tickets.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="nota")
public class Nota {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="id_utente")
	private User utente;
	
	@ManyToOne
	@JoinColumn(name="id_ticket")
	private Ticket ticket;

	@Column(name="data_creazione")
	private LocalDateTime dataCreazione;
	
	@NotBlank(message="Inserisci un testo nel corpo della nota.")
	@Column(name="corpo_nota")
	private String corpo;
}

package it.astrignano.tickets.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
	@JsonBackReference
	private User utente;
	
	@ManyToOne
	@JoinColumn(name="id_ticket")
	@JsonBackReference
	private Ticket ticket;

	@Column(name="data_creazione")
	@DateTimeFormat(pattern="dd-MM-yyyy")
	private LocalDate dataCreazione = LocalDate.now();
	
	@Column(name="ora-creazione")
	@DateTimeFormat(pattern="HH:mm")
	private LocalTime oraCreazione = LocalTime.now();
	
	@NotBlank(message="Inserisci un testo nel corpo della nota.")
	@Column(name="corpo_nota", nullable=false)
	private String corpo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUtente() {
		return utente;
	}

	public void setUtente(User utente) {
		this.utente = utente;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}


	public LocalDate getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(LocalDate dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public LocalTime getOraCreazione() {
		return oraCreazione;
	}

	public void setOraCreazione(LocalTime oraCreazione) {
		this.oraCreazione = oraCreazione;
	}

	public String getCorpo() {
		return corpo;
	}

	public void setCorpo(String corpo) {
		this.corpo = corpo;
	}
}

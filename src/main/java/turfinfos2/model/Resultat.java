package turfinfos2.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "resultat")
@SequenceGenerator(name = "RESULT_SEQ_GENERATOR", sequenceName = "RESULT_SEQ", initialValue = 1, allocationSize = 1)
public class Resultat {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESULT_SEQ_GENERATOR")
    @Column(name = "id")
	private Long id;

	private String jour;

	private String R;

	private Integer C;

	@ElementCollection
	@CollectionTable(name = "simple_gagnant", joinColumns = {
			@JoinColumn(name = "result_id", referencedColumnName = "id") })
	@MapKeyColumn(name = "type_name")
	@Column(name = "dividende")
	private Map<String, Double> simpleGagnant = new HashMap<String, Double>();

	@ElementCollection
	@CollectionTable(name = "simple_places", joinColumns = {
			@JoinColumn(name = "result_id", referencedColumnName = "id") })
	@MapKeyColumn(name = "type_name")
	@Column(name = "dividende")
	private Map<String, Double> simplePlaces = new HashMap<String, Double>();

	@ElementCollection
	@CollectionTable(name = "couple_gagnant", joinColumns = {
			@JoinColumn(name = "result_id", referencedColumnName = "id") })
	@MapKeyColumn(name = "type_name")
	@Column(name = "dividende")
	private Map<String, Double> coupleGagnant = new HashMap<String, Double>();

	@ElementCollection
	@CollectionTable(name = "couple_places", joinColumns = {
			@JoinColumn(name = "result_id", referencedColumnName = "id") })
	@MapKeyColumn(name = "type_name")
	@Column(name = "dividende")
	private Map<String, Double> couplePlaces = new HashMap<String, Double>();

	@ElementCollection
	@CollectionTable(name = "couple_ordre", joinColumns = {
			@JoinColumn(name = "result_id", referencedColumnName = "id") })
	@MapKeyColumn(name = "type_name")
	@Column(name = "dividende")
	private Map<String, Double> coupleOrdre = new HashMap<String, Double>();

	@ElementCollection
	@CollectionTable(name = "deux_sur_quatre", joinColumns = {
			@JoinColumn(name = "result_id", referencedColumnName = "id") })
	@MapKeyColumn(name = "type_name")
	@Column(name = "dividende")
	private Map<String, Double> deuxSurQuatre = new HashMap<String, Double>();

	@ElementCollection
	@CollectionTable(name = "trio", joinColumns = {
			@JoinColumn(name = "result_id", referencedColumnName = "id") })
	@MapKeyColumn(name = "type_name")
	@Column(name = "dividende")
	private Map<String, Double> trio = new HashMap<String, Double>();

	@ElementCollection
	@CollectionTable(name = "trio_ordre", joinColumns = {
			@JoinColumn(name = "result_id", referencedColumnName = "id") })
	@MapKeyColumn(name = "type_name")
	@Column(name = "dividende")
	private Map<String, Double> trioOrdre = new HashMap<String, Double>();

	@ElementCollection
	@CollectionTable(name = "multis", joinColumns = {
			@JoinColumn(name = "result_id", referencedColumnName = "id") })
	@MapKeyColumn(name = "type_name")
	@Column(name = "dividende")
	private Map<String, Double> multis = new HashMap<String, Double>();

	@ElementCollection
	@CollectionTable(name = "pick_5", joinColumns = {
			@JoinColumn(name = "result_id", referencedColumnName = "id") })
	@MapKeyColumn(name = "type_name")
	@Column(name = "dividende")
	private Map<String, Double> pick5 = new HashMap<String, Double>();

	@ElementCollection
	@CollectionTable(name = "super_4", joinColumns = {
			@JoinColumn(name = "result_id", referencedColumnName = "id") })
	@MapKeyColumn(name = "type_name")
	@Column(name = "dividende")
	private Map<String, Double> super4 = new HashMap<String, Double>();

	@ElementCollection
	@CollectionTable(name = "tierce", joinColumns = {
			@JoinColumn(name = "result_id", referencedColumnName = "id") })
	@MapKeyColumn(name = "type_name")
	@Column(name = "dividende")
	private Map<String, Double> tierce = new HashMap<String, Double>();

	@ElementCollection
	@CollectionTable(name = "quarte", joinColumns = {
			@JoinColumn(name = "result_id", referencedColumnName = "id") })
	@MapKeyColumn(name = "type_name")
	@Column(name = "dividende")
	private Map<String, Double> quarte = new HashMap<String, Double>();

	@ElementCollection
	@CollectionTable(name = "quinte", joinColumns = {
			@JoinColumn(name = "result_id", referencedColumnName = "id") })
	@MapKeyColumn(name = "type_name")
	@Column(name = "dividende")
	private Map<String, Double> quinte = new HashMap<String, Double>();

	public Resultat(Long id, String jour, String r, Integer c, Map<String, Double> simpleGagnant,
			Map<String, Double> simplePlaces, Map<String, Double> coupleGagnant, Map<String, Double> couplePlaces,
			Map<String, Double> coupleOrdre, Map<String, Double> deuxSurQuatre, Map<String, Double> trio,
			Map<String, Double> trioOrdre, Map<String, Double> multis, Map<String, Double> pick5,
			Map<String, Double> super4, Map<String, Double> tierce,
			Map<String, Double> quarte, Map<String, Double> quinte) {
		super();
		this.id = id;
		this.jour = jour;
		this.R = r;
		this.C = c;
		this.simpleGagnant = simpleGagnant;
		this.simplePlaces = simplePlaces;
		this.coupleGagnant = coupleGagnant;
		this.couplePlaces = couplePlaces;
		this.coupleOrdre = coupleOrdre;
		this.deuxSurQuatre = deuxSurQuatre;
		this.trio = trio;
		this.trioOrdre = trioOrdre;
		this.multis = multis;
		this.pick5 = pick5;
		this.super4 = super4;
		this.tierce = tierce;
		this.quarte = quarte;
		this.quinte = quinte;
	}

	public Resultat() {
		super();
	}

//	@Override
//	public String toString() {
//		return "Resultat [id=" + id + ", jour=" + jour + ", R=" + R + ", C=" + C + ", simpleGagnant=" + simpleGagnant
//				+ ", simplePlaces=" + simplePlaces + ", coupleGagnant=" + coupleGagnant + ", couplePlaces="
//				+ couplePlaces + ", coupleOrdre=" + coupleOrdre + ", deuxSurQuatre=" + deuxSurQuatre + ", trio=" + trio
//				+ ", trioOrdre=" + trioOrdre + ", multis=" + multis + ", pick5=" + pick5 + ", super4=" + super4
//				+ ", tierce=" + tierce + ", quarte=" + quarte + ", quinte=" + quinte + "]";
//	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getJour() {
		return jour;
	}

	public void setJour(String jour) {
		this.jour = jour;
	}

	public String getR() {
		return R;
	}

	public void setR(String r) {
		R = r;
	}

	public Integer getC() {
		return C;
	}

	public void setC(Integer c) {
		C = c;
	}

	public Map<String, Double> getSimpleGagnant() {
		return simpleGagnant;
	}

	public void setSimpleGagnant(Map<String, Double> simpleGagnant) {
		this.simpleGagnant = simpleGagnant;
	}

	public Map<String, Double> getSimplePlaces() {
		return simplePlaces;
	}

	public void setSimplePlaces(Map<String, Double> simplePlaces) {
		this.simplePlaces = simplePlaces;
	}

	public Map<String, Double> getCoupleGagnant() {
		return coupleGagnant;
	}

	public void setCoupleGagnant(Map<String, Double> coupleGagnant) {
		this.coupleGagnant = coupleGagnant;
	}

	public Map<String, Double> getCouplePlaces() {
		return couplePlaces;
	}

	public void setCouplePlaces(Map<String, Double> couplePlaces) {
		this.couplePlaces = couplePlaces;
	}

	public Map<String, Double> getCoupleOrdre() {
		return coupleOrdre;
	}

	public void setCoupleOrdre(Map<String, Double> coupleOrdre) {
		this.coupleOrdre = coupleOrdre;
	}

	public Map<String, Double> getDeuxSurQuatre() {
		return deuxSurQuatre;
	}

	public void setDeuxSurQuatre(Map<String, Double> deuxSurQuatre) {
		this.deuxSurQuatre = deuxSurQuatre;
	}

	public Map<String, Double> getTrio() {
		return trio;
	}

	public void setTrio(Map<String, Double> trio) {
		this.trio = trio;
	}

	public Map<String, Double> getTrioOrdre() {
		return trioOrdre;
	}

	public void setTrioOrdre(Map<String, Double> trioOrdre) {
		this.trioOrdre = trioOrdre;
	}

	public Map<String, Double> getMultis() {
		return multis;
	}

	public void setMultis(Map<String, Double> multis) {
		this.multis = multis;
	}

	public Map<String, Double> getPick5() {
		return pick5;
	}

	public void setPick5(Map<String, Double> pick5) {
		this.pick5 = pick5;
	}

	public Map<String, Double> getSuper4() {
		return super4;
	}

	public void setSuper4(Map<String, Double> super4) {
		this.super4 = super4;
	}

	public Map<String, Double> getTierce() {
		return tierce;
	}

	public void setTierce(Map<String, Double> tierce) {
		this.tierce = tierce;
	}

	public Map<String, Double> getQuarte() {
		return quarte;
	}

	public void setQuarte(Map<String, Double> quarte) {
		this.quarte = quarte;
	}

	public Map<String, Double> getQuinte() {
		return quinte;
	}

	public void setQuinte(Map<String, Double> quinte) {
		this.quinte = quinte;
	}



}
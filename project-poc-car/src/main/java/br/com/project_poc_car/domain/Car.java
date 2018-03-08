package br.com.project_poc_car.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author alan.franco
 *
 *         Classe entidade que representa em forma de objeto relacional a tabela
 *         tb_car e todas as suas colunas
 */

@Entity
@Table(name = "tb_car")
public class Car extends BaseDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1404660546661778719L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id; /* Atributo identificador */

	@Column(name = "brand", nullable = false, length = 40)
	private String brand; /* Marca, texto, não nulo, 40 caracteres */

	@Column(name = "model", nullable = false, length = 50)
	private String model; /* Modelo, texto, não nulo, 50 caracteres */

	@Column(name = "color", nullable = false, length = 30)
	private String color; /* Cor, texto, não nulo, 30 caracteres */

	@Column(name = "year", nullable = false)
	private Integer year; /* Ano, inteiro positivo, não nulo */

	@Column(name = "price", nullable = false, precision = 18, scale = 3)
	private Double price; /* Preço, decimal positivo, não nulo */

	@Column(name = "description", nullable = false)
	private String description; /* Descrição, texto */

	@Column(name = "is_new", nullable = false)
	private Boolean isNew; /* É novo?, boleano, não nulo */

	@Column(name = "registration_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date registrationDate; /* Data cadastro, data e hora, não nulo */

	@Column(name = "update_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate; /* Data atualização, data e hora, nulo */

	public Car() {
		super();
		this.isNew = false;
	}

	public Car(Integer id, String brand, String model, String color, Integer year, Double price, String description,
			Boolean isNew, Date registrationDate, Date updateDate) {
		super();
		this.id = id;
		this.brand = brand;
		this.model = model;
		this.color = color;
		this.year = year;
		this.price = price;
		this.description = description;
		this.isNew = isNew;
		this.registrationDate = registrationDate;
		this.updateDate = updateDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Car other = (Car) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", brand=" + brand + ", model=" + model + ", color=" + color + ", year=" + year
				+ ", price=" + price + ", description=" + description + ", isNew=" + isNew + ", registrationDate="
				+ registrationDate + ", updateDate=" + updateDate + "]";
	}

}
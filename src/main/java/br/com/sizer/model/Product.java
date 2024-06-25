package br.com.sizer.model;

import java.util.Map;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "product")

@Getter
@Setter
@ToString
@AllArgsConstructor

public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", length = 255)
	private String name;

	@Column(name = "product_id", length = 7)
	private int productId;

	@Column(name = "sku_id", length = 7)
	private int skuId;

	@Column(name = "category_id")
	private String category;

	@ElementCollection
	@CollectionTable(name = "product_dimensions", joinColumns = @JoinColumn(name = "product_id"))
	@MapKeyColumn(name = "dimension_name")
	@Column(name = "dimension_value")
	private Map<String, Double> dimensions;

	@ManyToOne
	@JoinColumn(name = "store_id", nullable = false)

	private Store store;

}

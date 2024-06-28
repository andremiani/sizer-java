package br.com.sizer.model;

import java.util.Map;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "product")

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", length = 255)
	private String name;

	@Column(name = "product_id", length = 7)
	private String productId;

	@Column(name = "sku_id", length = 7)
	private String skuId;

	@Column(name = "category_id", length = 7)
	private String category;

	@ElementCollection
	@CollectionTable(name = "product_dimensions", joinColumns = @JoinColumn(name = "product_id"))
	@MapKeyColumn(name = "dimension_name")
	@Column(name = "dimension_value")
	private Map<String, Double> dimensions;

	// @ManyToOne
	// @JoinColumn(name = "store_id", nullable = false)

	@Column(name = "store_id")
	private String store;

}

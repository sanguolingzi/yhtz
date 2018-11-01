package com.yinhetianze.business.shoppingcart.model;

import com.yinhetianze.core.business.httprequest.PageModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public class ShopcartModel extends PageModel {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 购物车主键
	 */
	private Integer id;
	/**
	 * 用户ID
	 */
	private Integer customerId;
	/**
	 * 商品数量
	 */
	private Integer number;
	/**
	 * 商品ID
	 */
	private Integer productId;
	/**
	 * 商品SKU
	 */
	private String skuCode;
	/**
	 * 店铺ID
	 */
	private String shopId;

	private BigDecimal price;
	/**
	 * 购物车主键数组
	 */
	private Integer[] shopcartIds;

	/**
	 * 批量修改的时候需要（id,number）
	 */
	private List<Map<String, Object>> shopcartList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer[] getShopcartIds() {
		return shopcartIds;
	}

	public void setShopcartIds(Integer[] shopcartIds) {
		this.shopcartIds = shopcartIds;
	}

	public List<Map<String, Object>> getShopcartList() {
		return shopcartList;
	}

	public void setShopcartList(List<Map<String, Object>> shopcartList) {
		this.shopcartList = shopcartList;
	}

}

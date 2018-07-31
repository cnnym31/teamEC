package com.internousdev.kagiya.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.kagiya.dao.ProductInfoDAO;
import com.internousdev.kagiya.dto.MCategoryDTO;
import com.internousdev.kagiya.dto.ProductInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class ProductDetailsAction extends ActionSupport implements SessionAware {
	private String productId;
	private String categoryId;
	private List<MCategoryDTO> mCategoryDtoList = new ArrayList<MCategoryDTO>();
	private List<ProductInfoDTO> productInfoDTOList = new ArrayList<ProductInfoDTO>();
	private Map<String, Object> session;

	public String execute() {
		String result = ERROR;
		int product = 0;

		try {
		      product = Integer.valueOf(productId);
		    } catch (NumberFormatException e) {
		      //数値に変換できない場合は何もしない
		    }

		//指定した(前ページで選択した)商品の情報を取得
		ProductInfoDAO productInfoDAO = new ProductInfoDAO();
		ProductInfoDTO productInfoDTO = new ProductInfoDTO();
		productInfoDTO = productInfoDAO.selectByProductId(product);

		//指定した(前ページで選択した)商品の情報を取得をsessionにいれる
		session.put("id", productInfoDTO.getId());
		session.put("productId", productInfoDTO.getProductId());
		session.put("productName", productInfoDTO.getProductName());
		session.put("productNameKana", productInfoDTO.getProductNameKana());
		session.put("imageFilePath", productInfoDTO.getImageFilePath());
		session.put("imageFileName", productInfoDTO.getImageFileName());
		session.put("price", productInfoDTO.getPrice());
		session.put("releaseCompany", productInfoDTO.getReleaseCompany());
		session.put("releaseDate", productInfoDTO.getReleaseDate());
		session.put("productDescription", productInfoDTO.getProductDescription());
		session.put("categoryId", productInfoDTO.getCategoryId());

		System.out.println(session.get("categoryId"));

		List<Integer> productCountList = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5));
		session.put("productCountList", productCountList);

		//関連商品3つのリスト
		productInfoDTOList = productInfoDAO.selectByCetegoryId(productInfoDTO.getCategoryId(),
				productInfoDTO.getProductId(), 0, 3);

		//Iteratorの宣言
		Iterator<ProductInfoDTO> iterator = productInfoDTOList.iterator();

		if (!(iterator.hasNext())) {
			productCountList = null;
		}

		// productInfoDTOListが空でない、または、productCountListがnullであれば
		// 関連商品3つのリストをsessionにいれて結果をSUCCESSにする
		if (!productInfoDTOList.isEmpty() || productCountList == null) {
			session.put("productInfoDTOList", productInfoDTOList);
			result = SUCCESS;
		}

		session.put("checked", 1);
		return result;

	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public List<MCategoryDTO> getmCategoryDtoList() {
		return mCategoryDtoList;
	}

	public void setmCategoryDtoList(List<MCategoryDTO> mCategoryDtoList) {
		this.mCategoryDtoList = mCategoryDtoList;
	}

	public List<ProductInfoDTO> getProductInfoDTOList() {
		return productInfoDTOList;
	}

	public void setProductInfoDtoList(List<ProductInfoDTO> productInfoDTOList) {
		this.productInfoDTOList = productInfoDTOList;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}

package com.internousdev.kagiya.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.internousdev.kagiya.dto.ProductInfoDTO;
import com.internousdev.kagiya.util.DBConnector;

public class ProductInfoDAO {

	/* 全ての商品情報を取得 */
	public List<ProductInfoDTO> selectAll() {

		DBConnector dbConnector = new DBConnector();
		Connection connection = dbConnector.getConnection();
		List<ProductInfoDTO> productInfoDTOList = new ArrayList<ProductInfoDTO>();

		String sql = "SELECT * FROM product_info";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				ProductInfoDTO productInfoDTO = new ProductInfoDTO();
				productInfoDTO.setId(resultSet.getInt("id"));
				productInfoDTO.setProductId(resultSet.getInt("product_id"));
				productInfoDTO.setProductName(resultSet.getString("product_name"));
				productInfoDTO.setProductNameKana(resultSet.getString("product_name_kana"));
				productInfoDTO.setProductDescription(resultSet.getString("product_description"));
				productInfoDTO.setCategoryId(resultSet.getInt("category_id"));
				productInfoDTO.setPrice(resultSet.getInt("price"));
				productInfoDTO.setImageFilePath(resultSet.getString("image_file_path"));
				productInfoDTO.setImageFileName(resultSet.getString("image_file_name"));
				productInfoDTO.setReleaseDate(resultSet.getDate("release_date"));
				productInfoDTO.setReleaseCompany(resultSet.getString("release_company"));
				productInfoDTO.setStatus(resultSet.getInt("status"));
				productInfoDTO.setRegistDate(resultSet.getDate("regist_date"));
				productInfoDTO.setUpdateDate(resultSet.getDate("update_date"));
				productInfoDTOList.add(productInfoDTO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return productInfoDTOList;
	}

	/* productIdから商品情報取得 */
	public ProductInfoDTO selectByProductId(int productId) {
		DBConnector dbConnector = new DBConnector();
		Connection connection = dbConnector.getConnection();
		ProductInfoDTO productInfoDTO = new ProductInfoDTO();

		String sql = "SELECT * FROM product_info where product_id=?";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, productId);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				productInfoDTO.setId(resultSet.getInt("id"));
				productInfoDTO.setProductId(resultSet.getInt("product_id"));
				productInfoDTO.setProductName(resultSet.getString("product_name"));
				productInfoDTO.setProductNameKana(resultSet.getString("product_name_kana"));
				productInfoDTO.setProductDescription(resultSet.getString("product_description"));
				productInfoDTO.setCategoryId(resultSet.getInt("category_id"));
				productInfoDTO.setPrice(resultSet.getInt("price"));
				productInfoDTO.setImageFilePath(resultSet.getString("image_file_path"));
				productInfoDTO.setImageFileName(resultSet.getString("image_file_name"));
				productInfoDTO.setReleaseDate(resultSet.getDate("release_date"));
				productInfoDTO.setReleaseCompany(resultSet.getString("release_company"));
				productInfoDTO.setStatus(resultSet.getInt("status"));
				productInfoDTO.setRegistDate(resultSet.getDate("regist_date"));
				productInfoDTO.setUpdateDate(resultSet.getDate("update_date"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return productInfoDTO;
	}

	/*  同カテゴリ別商品表示用
		同じcategory_idの商品情報を、指定したproductId除いて指定した件数取得 */
	public List<ProductInfoDTO> selectByCetegoryId(int categoryId, int productId, int limitOffset, int limitRowCount) {
		DBConnector dbConnector = new DBConnector();
		Connection connection = dbConnector.getConnection();
		List<ProductInfoDTO> productInfoDTOList = new ArrayList<ProductInfoDTO>();

		String sql = "SELECT * FROM product_info WHERE category_id=? AND product_id NOT IN(?) ORDER BY rand() limit ?,?";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, categoryId);
			preparedStatement.setInt(2, productId);
			preparedStatement.setInt(3, limitOffset);
			preparedStatement.setInt(4, limitRowCount);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				ProductInfoDTO productInfoDTO = new ProductInfoDTO();
				productInfoDTO.setId(resultSet.getInt("id"));
				productInfoDTO.setProductId(resultSet.getInt("product_id"));
				productInfoDTO.setProductName(resultSet.getString("product_name"));
				productInfoDTO.setProductNameKana(resultSet.getString("product_name_kana"));
				productInfoDTO.setProductDescription(resultSet.getString("product_description"));
				productInfoDTO.setCategoryId(resultSet.getInt("category_id"));
				productInfoDTO.setPrice(resultSet.getInt("price"));
				productInfoDTO.setImageFilePath(resultSet.getString("image_file_path"));
				productInfoDTO.setImageFileName(resultSet.getString("image_file_name"));
				productInfoDTO.setReleaseDate(resultSet.getDate("release_date"));
				productInfoDTO.setReleaseCompany(resultSet.getString("release_company"));
				productInfoDTO.setStatus(resultSet.getInt("status"));
				productInfoDTO.setRegistDate(resultSet.getDate("regist_date"));
				productInfoDTO.setUpdateDate(resultSet.getDate("update_date"));
				productInfoDTOList.add(productInfoDTO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return productInfoDTOList;
	}

	//カテゴリーすべてからkeywordsで商品情報を検索
	public List<ProductInfoDTO> getProductListAllByKeywords(String[] keywordList) {

		DBConnector dbConnector = new DBConnector();
		Connection connection = dbConnector.getConnection();
		List<ProductInfoDTO> productInfoDTOList = new ArrayList<ProductInfoDTO>();

		String sql = "SELECT * FROM product_info WHERE ";

		boolean initializeFlag = true;
		for (String keyword : keywordList) {
			if (initializeFlag) {
				sql += "(product_name like '%" + keyword + "%' OR product_name_kana like '%" + keyword + "%')";
				initializeFlag = false;
			} else {
				sql += " AND (product_name like '%" + keyword + "%' OR product_name_kana like '%" + keyword + "%')";
			}
		}

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				ProductInfoDTO productInfoDTO = new ProductInfoDTO();
				productInfoDTO.setId(resultSet.getInt("id"));
				productInfoDTO.setProductId(resultSet.getInt("product_id"));
				productInfoDTO.setProductName(resultSet.getString("product_name"));
				productInfoDTO.setProductNameKana(resultSet.getString("product_name_kana"));
				productInfoDTO.setProductDescription(resultSet.getString("product_description"));
				productInfoDTO.setCategoryId(resultSet.getInt("category_id"));
				productInfoDTO.setPrice(resultSet.getInt("price"));
				productInfoDTO.setImageFilePath(resultSet.getString("image_file_path"));
				productInfoDTO.setImageFileName(resultSet.getString("image_file_name"));
				productInfoDTO.setReleaseDate(resultSet.getDate("release_date"));
				productInfoDTO.setReleaseCompany(resultSet.getString("release_company"));
				productInfoDTO.setStatus(resultSet.getInt("status"));
				productInfoDTO.setRegistDate(resultSet.getDate("regist_date"));
				productInfoDTO.setUpdateDate(resultSet.getDate("update_date"));
				productInfoDTOList.add(productInfoDTO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return productInfoDTOList;
	}

	//カテゴリーとkeywordsで商品情報を検索
	public List<ProductInfoDTO> getProductInfoListByKeywords(String[] keywordList, int categoryId) {

		DBConnector dbConnector = new DBConnector();
		Connection connection = dbConnector.getConnection();
		List<ProductInfoDTO> productInfoDTOList = new ArrayList<ProductInfoDTO>();

		String sql = "SELECT * FROM product_info WHERE category_id = " + categoryId;

		//		boolean initializeFlag = true;
		for (String keyword : keywordList) {

			sql += " AND (product_name like '%" + keyword + "%' OR product_name_kana like '%" + keyword + "%')";

			//			if(initializeFlag) {
			//				sql += " AND (product_name like '%" + keyword + "%' OR product_name_kana like '%" + keyword + "%')";
			//				initializeFlag = false;
			//			} else {
			//				sql += " AND (product_name like '%" + keyword + "%' OR product_name_kana like '%" + keyword + "%')";
			//			}
		}

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				ProductInfoDTO productInfoDTO = new ProductInfoDTO();
				productInfoDTO.setId(resultSet.getInt("id"));
				productInfoDTO.setProductId(resultSet.getInt("product_id"));
				productInfoDTO.setProductName(resultSet.getString("product_name"));
				productInfoDTO.setProductNameKana(resultSet.getString("product_name_kana"));
				productInfoDTO.setProductDescription(resultSet.getString("product_description"));
				productInfoDTO.setCategoryId(resultSet.getInt("category_id"));
				productInfoDTO.setPrice(resultSet.getInt("price"));
				productInfoDTO.setImageFilePath(resultSet.getString("image_file_path"));
				productInfoDTO.setImageFileName(resultSet.getString("image_file_name"));
				productInfoDTO.setReleaseDate(resultSet.getDate("release_date"));
				productInfoDTO.setReleaseCompany(resultSet.getString("release_company"));
				productInfoDTO.setStatus(resultSet.getInt("status"));
				productInfoDTO.setRegistDate(resultSet.getDate("regist_date"));
				productInfoDTO.setUpdateDate(resultSet.getDate("update_date"));
				productInfoDTOList.add(productInfoDTO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return productInfoDTOList;
	}

	//商品追加機能用
	public int productAddInfo(int productId, String productName, String productNameKana, String productDescription,
			int categoryId, int price, String imageFilePath, String release_company) throws SQLException {
		DBConnector dbConnector = new DBConnector();
		int count = 0;
		Connection connection = dbConnector.getConnection();
		String sql = "INSERT INTO product_info(product_id,product_name,product_name_kana,product_description,category_id,price,image_file_path,release_date,release_company,regist_date) "
				+ " VALUES(?,?,?,?,?,?,?,now(),?,now())";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, productId);
			preparedStatement.setString(2, productName);
			preparedStatement.setString(3, productNameKana);
			preparedStatement.setString(4, productDescription);
			preparedStatement.setInt(5, categoryId);
			preparedStatement.setInt(6, price);
			preparedStatement.setString(7, imageFilePath);
			preparedStatement.setString(8, release_company);
			count = preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		return count;
	}

	//ID重複チェック用
	public ArrayList<ProductInfoDTO> productIdCheck() {

		DBConnector dbConnector = new DBConnector();
		Connection connection = dbConnector.getConnection();
		ArrayList<ProductInfoDTO> productInfo = new ArrayList<ProductInfoDTO>();

		String sql = "SELECT product_id, product_name, product_name_kana FROM product_info";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				ProductInfoDTO productInfoDTO = new ProductInfoDTO();

				productInfoDTO.setProductId(resultSet.getInt("product_id"));
				productInfoDTO.setProductName(resultSet.getString("product_name"));
				productInfoDTO.setProductNameKana(resultSet.getString("product_name_kana"));
				productInfo.add(productInfoDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return productInfo;
	}

	//商品削除用 削除する商品情報取得
//	public ProductInfoDTO getSelectProductInfoName(String productName) {
//		DBConnector dbConnector = new DBConnector();
//		Connection connection = dbConnector.getConnection();
//		ProductInfoDTO productInfoDTO = new ProductInfoDTO();
//
//		String sql = "SELECT id, product_id, product_name, product_name_kana, product_description, "
//				+ "category_id, price, image_file_path, image_file_name, release_date, release_company, "
//				+ "status FROM product_info WHERE product_name = ?";
//
//		try {
//			PreparedStatement preparedStatement = connection.prepareStatement(sql);
//			preparedStatement.setString(1, productName);
//			ResultSet resultSet = preparedStatement.executeQuery();
//
//			if (resultSet.next()) {
//				productInfoDTO.setId(resultSet.getInt("id"));
//				productInfoDTO.setProductId(resultSet.getInt("product_id"));
//				productInfoDTO.setProductName(resultSet.getString("product_name"));
//				productInfoDTO.setProductNameKana(resultSet.getString("product_name_kana"));
//				productInfoDTO.setProductDescription(resultSet.getString("product_description"));
//				productInfoDTO.setCategoryId(resultSet.getInt("category_id"));
//				productInfoDTO.setPrice(resultSet.getInt("price"));
//				productInfoDTO.setImageFilePath(resultSet.getString("image_file_path"));
//				productInfoDTO.setImageFileName(resultSet.getString("image_file_name"));
//				productInfoDTO.setReleaseDate(resultSet.getDate("release_date"));
//				productInfoDTO.setReleaseCompany(resultSet.getString("release_company"));
//			} else {
//				return null;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally{
//			try {
//				connection.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return productInfoDTO;
//	}

	//商品削除用 DBから選択した商品と同じproductnameを検索
	public int productDeleteInfo(int productId) throws SQLException {
		DBConnector dbConnector = new DBConnector();
		Connection connection = dbConnector.getConnection();

		String sql = "DELETE FROM product_info WHERE product_id = ?";
		int result = 0;

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, productId);
			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		return result;
	}

//	//商品削除用 DBから選択した商品と同じproductnameを検索 リストから商品を見えなくする
//		public int productDeleteInfo(int productId) throws SQLException {
//			DBConnector dbConnector = new DBConnector();
//			Connection connection = dbConnector.getConnection();
//
//			String sql = "UPDATE product_info SET delete_flg = 1 WHERE product_id = ?";
//			int result = 0;
//
//			try {
//				PreparedStatement preparedStatement = connection.prepareStatement(sql);
//				preparedStatement.setInt(1, productId);
//				result = preparedStatement.executeUpdate();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			} finally {
//				connection.close();
//			}
//			return result;
//		}

}
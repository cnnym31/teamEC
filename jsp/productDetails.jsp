<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="./css/productDetails.css">
		<title>商品詳細</title>
	</head>
<body>

	<!-- ヘッダー -->
	<jsp:include page="header.jsp"/>

	<!-- 商品詳細情報表示 -->
	<div class="productDetails-main">

		<div class="top">
			<h1>商品詳細</h1>
		</div>

		<s:if test="#session.categoryId != 0">
		<s:form action="AddCartAction">

			<!-- 商品画像 -->
			<div class="productDetails-left">
				<img src='<s:property value="#session.imageFilePath"/><s:property value="#session.imageFileName"/>' ><br>
			</div>

			<div class="productDetails-right">
				<table>
					<tr>
						<th>商品名：</th>
						<td><s:property value="#session.productName"/></td>
					</tr>
					<tr>
						<th>ふりがな：</th>
						<td><s:property value="#session.productNameKana"/></td>
					</tr>
					<tr>
						<th>商品詳細情報：</th>
						<td><s:property value="#session.productDescription"/></td>
					</tr>
					<tr>
						<th>発売会社名：</th>
						<td><s:property value="#session.releaseCompany"/></td>
					</tr>
					<tr>
						<s:if test="#session.categoryId==4">
							<th>入社年月日：</th>
						</s:if>
						<s:else>
							<th>発売年月日：</th>
						</s:else>
						<td><s:property value="#session.releaseDate"/></td>
					</tr>
					<tr>
						<th>値段：</th>
						<td><s:property value="#session.price"/>円</td>
					</tr>
					<tr>
						<s:if test="#session.categoryId==4">
							<th>利用時間：</th>
						</s:if>
						<s:else>
							<th>購入個数：</th>
						</s:else>
						<td>
							<select name="productCount">
								<option value="1" selected="selected">1</option>
								<option value="2">2</option>
								<option value="3">3</option>
								<option value="4">4</option>
								<option value="5">5</option>
							</select>
							<s:if test="#session.categoryId==4">時間</s:if>
						</td>
					</tr>
				</table>

				<div class="btn">
					<input type="image" class="submit" src="images/btn_cartin.jpg"/>
				</div>

			</div>

			<s:hidden name="productId" value="%{#session.productId}"/>
			<s:hidden name="productName" value="%{#session.productName}"/>
			<s:hidden name="productNameKana" value="%{#session.productNameKana}"/>
			<s:hidden name="imageFilePath" value="%{#session.imageFilePath}"/>
			<s:hidden name="imageFileName" value="%{#session.imageFileName}"/>
			<s:hidden name="price" value="%{#session.price}"/>
			<s:hidden name="releaseCompany" value="%{#session.releaseCompany}"/>
			<s:hidden name="releaseDate" value="%{#session.releaseDate}"/>
			<s:hidden name="productDescription" value="%{#session.productDescription}"/>
		</s:form>

		<!-- 同カテゴリ別の別商品を表示 -->
		<div class="productDetails-recommend">
			<div>
				<h4>〜この商品を見た人はこんな商品も見ています〜</h4>
			</div>
			<s:iterator value="#session.productInfoDTOList">
			<div class="recommend-box">
				<a href='<s:url action="ProductDetailsAction"> <s:param name="productId" value="%{productId}"/></s:url>'>
				<img src='<s:property value="imageFilePath"/><s:property value="imageFileName"/>'></a>
				<p><s:property value="productName"/></p><br>
			</div>
			</s:iterator>
		</div>
		</s:if>

		<s:else>
			<p>指定した商品は存在しません</p>
		</s:else>
	</div>
	<div class="clearfix"></div>

	<!-- フッター -->
	<jsp:include page="footer.jsp"/>

</body>
</html>
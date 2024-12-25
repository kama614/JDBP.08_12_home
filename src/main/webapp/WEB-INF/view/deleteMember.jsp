<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>会員管理</title>
</head>
<body>
	<h1>会員削除</h1>
	<form action="" method="post">

		<table border="1">
			<tr>
				<th>名前</th>
				<td><c:out value="${member.name}" /></td>
			</tr>
			<tr>
				<th>年齢</th>
				<td><c:out value="${member.age}" /></td>
			</tr>
			<tr>
				<th>住所</th>
				<td><c:out value="${member.address}" /></td>
			</tr>
			<tr>
				<th>会員種別</th>
				<td><c:out value="${member.typeName}" /></td>
			</tr>
			<tr>
				<th>登録日</th>
				<td><c:out value="${member.created}" /></td>
			</tr>
		</table>
		<p>
			<input type="submit" value="削除">
		</p>
	</form>
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script>
		$(document).ready(function() {
			$("form").submit(function() {
				return confirm("本当に削除しますか？");
			});
		});
	</script>
</body>
</html>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/12/6
  Time: 12:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>edit</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <form method="post">
            <%--隐藏域--%>
            <input type="hidden" name="id" value="${product.id}">
            <div class="form-group">
                <label>商品名称</label>
                <input type="text" name="productName" class="form-control" value="${product.productName}">
            </div>
            <div class="form-group">
                <label>价格</label>
                <input type="text" name="price" class="form-control" value="${product.price}">
            </div>

            <div class="form-group">
                <label>市场价格</label>
                <input type="text" name="marketPrice" class="form-control" value="${product.marketPrice}">
            </div>

            <div class="form-group">
                <label>产地</label>
                <input type="text" name="place" class="form-control" value="${product.place}">
            </div>

            <div class="form-group">
                <label>品论数量</label>
                <input type="text" name="commentNum" class="form-control" value="${product.commentNum}">
            </div>
            <div class="form-group">
                <button class="btn btn-primary">保存</button>
            </div>
        </form>
    </div>
</body>
</html>

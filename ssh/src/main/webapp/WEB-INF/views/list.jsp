<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/12/6
  Time: 9:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>product</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div calss=container>
        <%--搜索商品--%>
            <div class="panel panel-default">
                <div class="panel-body">
                    <form action="" class="form-inline">
                        <input type="text" placeholder="商品名称" name="q_like_s_productName" class="form-control">
                        <input type="text" placeholder="价格或市场价格" name="q_eq_bd_price_or_marketPrice" class="form-control">
                        <button class="btn btn-default">搜索</button>
                    </form>
                </div>
            </div>


        <a href="/product/new" class="btn btn-success">添加商品</a>

        <table class="table">
            <thead>
            <tr>
                <th>商品名称</th>
                <th>价格</th>
                <th>市场价</th>
                <th>产地</th>
                <th>评论数量</th>
            </tr>
            </thead>
            <tbody>
               <c:forEach items="${page.items}" var="product">
                   <tr>
                       <td><a href="/product/${product.id}">${product.productName}</a> </td>
                       <td>${product.price}</td>
                       <td>${product.marketPrice}</td>
                       <td>${product.place}</td>
                       <td>${product.commentNum}</td>
                   </tr>
               </c:forEach>
            </tbody>
        </table>
    </div>

</body>
</html>

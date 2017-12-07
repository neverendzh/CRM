<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/12/6
  Time: 11:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>product</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
   <div class="container">
       <h3>${product.productName}</h3>
       <ul>
           <li>商品价格：${product.price}</li>
           <li>市场价格：${product.marketPrice}</li>
           <li>产地：${product.place}</li>
           <li>品论数量：${product.commentNum}</li>
           <li>所属分类：${product.productType.typeName}</li>
       </ul>

       <a class="btn btn-success" href="/product/edit/${product.id}">修改</a>
       <a  class="btn btn-danger" href="/product/delete/${product.id}">删除</a>
   </div>
</body>
</html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/11/10
  Time: 12:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>凯盛软件CRM-编辑客户资料</title>
    <%@include file="../include/css.jsp"%>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <!-- 顶部导航栏部分 -->
    <%@include file="../include/header.jsp"%>

    <!-- =============================================== -->

    <!-- 左侧菜单栏 -->
     <jsp:include page="../include/sider.jsp">
         <jsp:param name="menu" value="customer_my"/>
     </jsp:include>
    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">编辑客户</h3>
                    <div class="box-tools pull-right">
                        <a href="/customer/my/${customer.id}" class="btn btn-primary btn-sm"><i class="fa fa-arrow-left"></i> 返回列表</a>
                    </div>
                </div>
                <div class="box-body">
                    <form method="post" id="newCustomerForm">
                        <input type="hidden" name="id" value="${customer.id}">
                        <div class="form-group">
                            <label>姓名</label>
                            <input type="text" class="form-control" name="custName" value="${customer.custName}">
                        </div>
                        <div class="form-group">
                            <label>性别</label>
                            <div>
                                <label class="radio-inline">
                                    <input type="radio" name="sex" value="男" ${customer.sex == '男' ? 'checked' : ''}>男
                                </label>

                                <label class="radio-inline">
                                    <input type="radio" name="sex" value="女" ${customer.sex == '女' ? 'checked' : ''}>女
                                </label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>职位</label>
                            <input type="text" class="form-control" name="jobTitle" value="${customer.jobTitle}">
                        </div>
                        <div class="form-group">
                            <label>联系方式</label>
                            <input type="text" class="form-control" name="mobile" value="${customer.mobile}">
                        </div>
                        <div class="form-group">
                            <label>地址</label>
                            <input type="text" class="form-control" name="address" value="${customer.address}">
                        </div>
                        <div class="form-group">
                            <label>所属行业</label>
                            <select class="form-control" name="trade">
                                <option value=""></option>
                                <c:forEach items="${trades}" var="trade">
                                    <option value="${trade}" ${customer.trade == trade ? 'selected' : ''}>${trade}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>客户来源</label>
                            <select name="source" class="form-control">
                                <option value=""></option>
                                <c:forEach items="${suorces}" var="source">
                                    <option value="${source}" ${customer.source == source ? 'selected' : ''}>${source}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>级别</label>
                            <select name="level" class="form-control">
                                <option value=""></option>
                                <option ${customer.level == '★' ? 'selected' : ''} value="★">★</option>
                                <option ${customer.level == '★★' ? 'selected' : ''} value="★★">★★</option>
                                <option ${customer.level == '★★★' ? 'selected' : ''} value="★★★">★★★</option>
                                <option ${customer.level == '★★★★' ? 'selected' : ''} value="★★★★">★★★★</option>
                                <option ${customer.level == '★★★★★' ? 'selected' : ''} value="★★★★★">★★★★★</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>备注</label>
                            <input type="text" name="mark" class="form-control" value="${customer.mark}">
                        </div>
                    </form>
                </div>
                <div class="box-footer">
                    <button class="btn btn-primary" id="saveCustomerBtn">保存</button>
                </div>
                <!-- /.box-body -->
            </div>
            <!-- /.box -->
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->




    <!-- 底部 -->
    <%@include file="../include/footer.jsp"%>
</div>
<!-- ./wrapper -->
<%@include file="../include/js.jsp"%>
<script src="/static/plugins/validate/jquery.validate.min.js"></script>
<script>
    $(function () {
        $("#saveCustomerBtn").click(function () {
            $("#newCustomerForm").submit();
        });

        $("#newCustomerForm").validate({
           errorClass:"text-danger",
           errorElement:"span",
           rules:{
               custName:{
                   required:true
               },
               mobile:{
                   required:true
               }
           },
            messages:{
                custName:{
                    required:"请输入姓名"
                },
                mobile:{
                    required:"请输入联系方式"
                }
            },
        });
    })
</script>
</body>
</html>

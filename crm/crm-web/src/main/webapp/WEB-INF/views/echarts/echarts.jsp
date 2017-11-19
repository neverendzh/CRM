<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>凯盛软件CRM-客户级别统计</title>
    <%@ include file="../include/css.jsp"%>
    <style>
        #bar{
            display: inline-block;
            margin-right: 20px;
            margin-left: 30px;
        }
        #bars{
            display: inline-block;
            margin-left: 20px;
        }

        #mouthNum{
            display: inline-block;
            margin-left: 30px;
            margin-top: 25px;
        }
    </style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
    <%@include file="../include/header.jsp"%>
    <!-- =============================================== -->

    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="charts_customer"/>
    </jsp:include>
    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">客户级别数量统计</h3>
                </div>
                <div class="box-body">
                    <div id="bar" style="height: 300px;width: 45%"></div>
                    <div id="bars" style="height: 300px;width: 45%"></div>
                    <div id="mouthNum" style="height: 300px;width: 45%"></div>
                </div>
            </div>
        </section>

    </div>
    <!-- /.content-wrapper -->

    <%@ include file="../include/footer.jsp"%>

</div>
<!-- ./wrapper -->

<%@include file="../include/js.jsp"%>
<script src="/static/plugins/layer/layer.js"></script>

<%--<div id="charts" style="width:650px;height:400px"></div>--%>

<script src="/static/plugins/echarts/echarts.min.js"></script>
<script src="/static/plugins/echarts/chalk.js"></script>

<script>
    $(function () {
        //客户级别统计图
        var bar = echarts.init(document.getElementById("bar"),'chalk');
        var option = {
            title: {
                text: "客户级别数量统计",
                left: 'center'
            },
            tooltip: {},
            legend: {
                data: ['人数'],
                left: 'right'
            },
            xAxis: {
                type: 'category',
                name: '星级',
                data: []
            },
            yAxis: {
                name: '人数'
            },
            series: {
                name: "人数",
                type: 'bar',
                data:[]
            }
        }
        bar.setOption(option);
        $.get("/echarts/customer/level").done(function (resp) {
            if(resp.state == "success"){
                var dataArray = resp.data;
                var nameArray = [];
                var valueArray = [];
                for(var i = 0; i<dataArray.length; i++){
                    var obj = dataArray[i];
                    nameArray.push(obj.level);
                    valueArray.push(obj.count);
                }
                bar.setOption({
                    xAxis:{
                        data:nameArray
                    },
                    series:{
                        data:valueArray
                    }

                    }
                );

            }else{
                layer.msg(resp.message);
            }
        }).error(function () {
            layer.msg("数据加载失败");
        });



        //每月客户新增趋势图
        var mouth = echarts.init(document.getElementById("mouthNum"),'chalk');
        var optionNum = {
            title: {
                text: "每月新增客户统计",
                left: 'center'
            },
            tooltip: {},
            legend: {
                data: ['人数'],
                left: 'right'
            },
            xAxis: {
                type: 'category',
                name: '月份',
                data: []
            },
            yAxis: {
                name: '人数',
            },
            series: {
                name: "人数",
                type: 'line',
                data:[]
            }
        }
        mouth.setOption(optionNum);
        $.get("/echarts/customer/mouth/num").done(function (resp) {
            if(resp.state == "success"){
                var dataArray = resp.data;
                var nameArray = [];
                var valueArray = [];
                for(var i = 0; i<dataArray.length; i++){
                    var obj = dataArray[i];
                    nameArray.push(obj.mouth);
                    valueArray.push(obj.num);
                }
                mouth.setOption({
                        xAxis:{
                            data:nameArray
                        },
                        series:{
                            data:valueArray
                        }

                    }
                );

            }else{
                layer.msg(resp.message);
            }
        }).error(function () {
            layer.msg("数据加载失败");
        });



        //漏斗图
        var bars = echarts.init(document.getElementById("bars"),'chalk');
        var options = {
            title: {
                text: '漏斗图',
                subtext: '纯属虚构'
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c}%"
            },
            toolbox: {
                feature: {
                    dataView: {readOnly: false},
                    restore: {},
                    saveAsImage: {}
                }
            },
            legend: {
                data: ['初访','意向','成交','报价','搁置']
            },
            calculable: true,
            series: [
                {
                    name:'漏斗图',
                    type:'funnel',
                    left: '10%',
                    top: 60,
                    //x2: 80,
                    bottom: 60,
                    width: '80%',
                    // height: {totalHeight} - y - y2,
                    min: 0,
                    max: 10,
                    minSize: '0%',
                    maxSize: '100%',
                    sort: 'descending',
                    gap: 2,
                    label: {
                        normal: {
                            show: true,
                            position: 'inside'
                        },
                        emphasis: {
                            textStyle: {
                                fontSize: 20
                            }
                        }
                    },
                    labelLine: {
                        normal: {
                            length: 10,
                            lineStyle: {
                                width: 1,
                                type: 'solid'
                            }
                        }
                    },
                    itemStyle: {
                        normal: {
                            borderColor: '#fff',
                            borderWidth: 1
                        }
                    },
                    data: []
                }
            ]
        }
        bars.setOption(options);

        $.get("/echarts/customer/sale/num").done(function (resp) {
            if(resp.state == "success"){
                var dataArray = resp.data;

                bars.setOption({
                        series:{
                            data:dataArray
                        }

                    }
                );

            }else{
                layer.msg(resp.message);
            }
        }).error(function () {
            layer.msg("数据加载失败");
        });

    });
</script>

</body>
</html>

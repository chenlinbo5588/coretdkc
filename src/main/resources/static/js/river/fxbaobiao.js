

$(function(){
    $(".mask").click(function () {

        $("#fxBaobiaoBox").empty();
        $("#fxBaobiaoBox").hide();
    })

    $(".baobiaoBox").draggable();

    setTimeout(function () {
        initTb();
    },500)
    function initTb() {
        var dom = document.getElementById("fxbb");
        var myChart = echarts.init(dom);
        var app = {};

        var option;



        option = {
            title: {
                left:50,
                subtext: '单位(平方千米)'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: data.typename
            },
            toolbox: {
                show: true,
                feature: {
                    saveAsImage: {show: true}
                }
            },
            calculable: true,
            xAxis: [
                {
                    type: 'category',
                    data: data.xname
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: data.fxTableList,
        };

        if (option && typeof option === 'object') {
            myChart.setOption(option);
        }

    }
})


$(function(){
    $(".mask").click(function () {

        $("#baobiaoBox").empty();
        $("#baobiaoBox").hide();
        baobiaoSelect = false;
    })



    init()
    function init() {
        $(".typeItem").each(function () {
            var type = $(this).data("name");
            if(type =="河道"){
                $(this).addClass("hedao typeItemPointer");
            }else if(type =="水库"){
                $(this).addClass("shuiku typeItemPointer");
            }else if(type =="山塘"){
                $(this).addClass("shantan typeItemPointer");
            }else if(type =="湖泊"){
                $(this).addClass("hupo");
            }else if(type =="其他水域"){
                $(this).addClass("ow");
            }else if(type =="人工水道"){
                $(this).addClass("ac typeItemPointer");
            }
        })

        $(".typeItemPointer").click(function () {
            $(".typeItem").removeClass("typeItemClick");
            $(this).addClass("typeItemClick");
            var type = $(this).data("name");

            $.get(BASE_URL + "river/baobiao?type="+type,function(resp){
                $("#baobiaoBox").html(resp);
                $("#baobiaoBox").show();
            })
        })
    }
    setTimeout(function () {
        for(i=0;i<data.length;i++){
            initTubiao(i);
        }
    },800)

    function initTubiao(index) {
        var dom = document.getElementsByName("tubiao").item(index)
        var myChart = echarts.init(dom);
        var app = {};
        var option;

        option = {
            tooltip: {
                trigger: 'item'
            },
            series: [
                {
                    name: data[index].name,
                    type: 'pie',
                    radius: ['40%', '80%'],
                    avoidLabelOverlap: false,
                    itemStyle: {
                        borderRadius: 10,
                        borderColor: '#fff',
                        borderWidth: 2
                    },
                    label: {
                        normal: {
                            show: true,
                            formatter: "{b}",
                        },
                    },
                    data:data[index].data,
                }
            ]
        };

        if (option && typeof option === 'object') {
            myChart.setOption(option);
        }
    }

})
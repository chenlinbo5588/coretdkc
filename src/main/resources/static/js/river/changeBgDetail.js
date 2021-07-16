

$(function(){

    $("[name=bgListButton]").click(function () {
        $("#bgList").hide();
    })
    $("#bgList").draggable();
    $("[name=item]").click(function () {
        var id  = $(this).data("id");

        $.get(BASE_URL + "river/bgDetailItem?id="+id,function(resp){
            $("#bgDetail").html(resp);
            $("#bgDetail").show();
            $("#bgDetail").draggable();
        })

    })
})
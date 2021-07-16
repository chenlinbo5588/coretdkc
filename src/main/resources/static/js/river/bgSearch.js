

$(function(){

    $("[name=bgSearchItem]").click(function () {

        var code = $(this).data("code");
        var id = $(this).data("id");
        $("#bgList").show();

        $.get(BASE_URL + "river/changeDetail?id=" + id, function (resp) {
            $("#changeDetail").html(resp);
        })
        changeFindByCode(code,leftView,rightView);
    })
})
$(function () {
    var url ="";
    var first =true;
    var data = [];
    $(".uploaditemBoxTitle img").click(function () {
        $(this).parent().find("img").show();
        $(this).hide();
        $(this).parent().parent().find(".uploaditemBox").toggle();
    })

    $(".uploaditemBox").mouseenter(function () {
        $(this).find(".uploaditemName").css("overflow","visible");
        $(this).find(".uploadTime").hide();
        $(this).find("[name=deleteFile]").show();
    });
    $(".uploaditemBox").mouseleave(function () {
        $(this).find(".uploaditemName").css("overflow","auto");
        $(this).find(".uploadTime").show();
        $(this).find("[name=deleteFile]").hide();
    });
    $(".uploaditemName").click(function () {
         var url = $(this).data("url");
         window.open(BASE_URL + "download/file?url="+url);
    });
    $("[name=deleteFile]").click(function () {
        var name = $(this).parent().find(".uploaditemName").text();
        var fileid = $(this).parent().find(".uploaditemName").data("id");
        openxmglTc("是否确定删除" + name);
        $("#xmglTcDelet").click(function () {
            $.get(BASE_URL + "/manage/delete/file?id="+fileid+"&glxmId="+id, function (result) {
                $("#right").html(result);
                showMessage('删除成功',2000,true,'bounceInUp-hastrans','bounceOutDown-hastrans');
            });
            $("#tc").hide();
        })
        $("#xmglTcCancel").click(function () {
            $("#tc").hide();
        })


    })
    $(".uploadFileButton").click(function () {
        url ="";
        data =[];
        openUploadFile();
        $(".fileSelect .input").val("");
        $("#ulFile").val("");
        if(first){
            first=false;
            $(".fileSelect img").unbind();
            $("#ulFile").unbind();
            $("#upload").unbind();
            $(".fileSelect img").click(function () {
                var file = $("#ulFile");
                file.click();
            })
            $("#ulFile").change(function () {
                var files = this.files;
                var formData = new FormData();

                for (var i = 0; i < files.length; i++) {
                    formData.append('files',files[i]);
                    if(files[i].name !=undefined){
                        url += files[i].name +";";
                    }
                    var st =  files[i].name.split(".");
                    var val  =  $("#folder option:selected").val();
                    var  item = {};
                    item.name = files[i]['name'];
                    item.size = files[i]['size'];
                    item.folderVal = val;
                    data.push(item);
                }

                $.ajax({
                    url : '/saveFile',
                    type : 'post',
                    dataType : "json",
                    cache : false,//上传文件不需要缓存
                    data : formData,
                    processData : false,
                    contentType : false,
                    success : function(result) {
                        console.log(result);

                    }
                });
                $(".fileSelect .input").val(url);
            })
            $("#upload").click(function () {

                uploadFile(data);

                $("#tc").hide();

            })
            $("#cancel").click(function () {
                $("#tc").hide();
            })
        }else{

        }


    })



    function uploadFile(data) {
        if(data.length>0){
            var param = {
                data: JSON.stringify(data),
                projectId: projectId,
            };
            $.post(BASE_URL + "/uploadFile", param, function (result) {
                $.get(BASE_URL +"manage/file/list?glxmId="+id,function(resp){
                    $("#right").html(resp);
                    showMessage('上传成功',2000,true,'bounceInUp-hastrans','bounceOutDown-hastrans');
                })
            });
        }else{
            showMessage('未选择文件，上传失败',2000,true,'bounceInUp-hastrans','bounceOutDown-hastrans');
        }
    }
})
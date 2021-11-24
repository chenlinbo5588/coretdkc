var commonDialog,
    formLock = [],
    RespCode = { 'OK' : 0 };



function getInputRings(txt){
	var mt,i = 0,rings = [],
		regMultiPolygon = /(.*)/mgi,
		polygonKeyword = /闭合/i,
		polygon = [],
		idx,
		regAr = new RegExp('(X=.*Z=)','gi');
	
	if(typeof(txt) == 'undefined'){
		txt = '';
	}
	
	idx = txt.indexOf('于端点');
	
	if(-1 == idx){
		idx = txt.indexOf('于 点');
	}
	
	if(-1 != idx){
		var s = txt.substring(0,idx);
		if(-1 == s.indexOf('闭合')){
			txt = "闭合\r\n" + txt;
		}
	}
	
	mt = txt.match(regMultiPolygon);
	
	if(mt && mt.length != 0){
		
		for(i = 0; i < mt.length; i++){
			var temp = $.trim(mt[i]);
			var coord ;
			if(polygonKeyword.test(temp)){
				//let's collect a polygen
				polygon.push([]);
			}
			
			coord = temp.match(regAr);
			if(coord){
				//console.log(coord);
				var pt = coord[0].replace(/X=/g,'').replace(/Y=/g,',').replace(/Z=/g,'').replace(/ /g,'').split(',');
				pt[0] = parseFloat($.trim(pt[0]));
				pt[1] = parseFloat($.trim(pt[1]));
				
				polygon[polygon.length - 1].push(pt);
				
			}
		}
	}
	
	console.log(polygon);
	return polygon;
}

//闭合图形
function bhPolygen(ringsParam){
	for(var i = 0; i < ringsParam.length; i++){
		ringsParam[i][ringsParam[i].length] = ringsParam[i][0];
	}
	
	return ringsParam;
}

function refreshFormHash(data){
	if(typeof(data.formhash) != "undefined"){
		formhash = data.formhash;
		$("input[name=formhash]").val(formhash);
	}
}


function getFileSize(size) {
	if (!size)
		return "";

	var num = 1024.00; //byte

	if (size < num)
		return size + "B";
	if (size < Math.pow(num, 2))
		return (size / num).toFixed(2) + "K"; //kb
	if (size < Math.pow(num, 3))
		return (size / Math.pow(num, 2)).toFixed(2) + "M"; //M
	if (size < Math.pow(num, 4))
		return (size / Math.pow(num, 3)).toFixed(2) + "G"; //G
	return (size / Math.pow(num, 4)).toFixed(2) + "T"; //T
}



function showToast(icon,message,moreSetting){
	var defaultSetting = {
			position:'top-center',
			text: message,
			icon: icon,
			hideAfter:5000,
			loader:false
		};
	
	var setting = $.extend(defaultSetting,moreSetting);
	
	$.toast(setting);
}

/*
 * ajax
 */
function doAjaxPost(postdata,url,datatype,successFn,errorFn){
	$.ajax({
		type:"POST",
		url:url,
		dataType:datatype,
		data: postdata,
		success:successFn,
		error:errorFn
	});
}


function ui_confirm(pParam){
	var lock = false;
	var postData = $.extend({ "formhash" : formhash} ,pParam.postData );
	
	commonDialog = $("#showDlg" ).dialog({
		title: "提示",
		autoOpen: false,
		width: 280,
		position: pParam.trigger ? { my: "center", at: "center", of: pParam.trigger } : null,
		modal: true,
	      buttons: {
	        "确定": function(){
	        	if(lock == true){
	        		showToast('info','操作进行中,请耐心等待');
	        		return;
	        	}
	        	lock = true;
	        	
	        	commonDialog.html('<div class="loading_bg">操作进行中,请稍候...</div>');
	        	
	        	if($.type(pParam.customDataFn) == 'function'){
	        		postData = $.extend(postData ,pParam.customDataFn() );
	        	}else if($.type(pParam.customDataFn) == 'object') {
	        		postData = $.extend(postData ,pParam.customDataFn );
	        	}
	        	
	        	doAjaxPost(postData, pParam.url, pParam.dataType, function(json){
	        		lock = false;
	        		commonDialog.dialog( "close" );
	        		if(typeof(pParam.successFn) != 'undefined'){
	        			pParam.successFn(postData.id,json);
	        		}
	        	},function(XMLHttpRequest, textStatus, thrownError){
	        		lock = false;
	        		commonDialog.dialog( "close" );
	        		if(typeof(pParam.errorFn) != 'undefined'){
	        			pParam.errorFn(XMLHttpRequest, textStatus, thrownError);
	        		}
	        	});
	        },
	        "关闭": function() {
	        	commonDialog.dialog( "close" );
	        }
	   },
	   open:function(){
		  
	   }
	}).html('<span class="ui-icon ui-icon-alert fl"></span><strong>' + pParam.titleHTML + '</strong>').dialog("open");
	
}

function check_success(message){
	var reg = new RegExp(/成功/);
	return reg.test(message);
}


function bindOpEvent(pSetting,customDataFn, customSuccessFn,customErrorFn){
	var defaultSetting = { rowid:"#row", selector: '.notexists',forceChecked: true};
	var setting = $.extend(defaultSetting,pSetting);
	
	var successCallback = function(ids,json){
		if(check_success(json.message)){
			showToast('success',json.message);
			
			if(typeof(ids) != 'undefined'){
				for(var i = 0; i < ids.length; i++){
					$(setting.rowid + ids[i]).remove();
				}
			}
		}else{
			showToast('error',json.message);
		}
	}
	
	var errorCallback = function(){
		showToast('error',"执行出错,服务器异常，请稍后再次尝试");
	}
	
	
	$(setting.selector).bind("click",function(){
		var triggerObj = $(this);
		var ids = getIDS(triggerObj);
		var url = triggerObj.attr("data-url");
  		var title = triggerObj.attr('data-title');
  		
  		if(setting.forceChecked && ids.length == 0){
			showToast('error','请选勾选.');
			return ;
		}
  		
		ui_confirm({
			'trigger':triggerObj,
			'postData': { id : ids },
			'customDataFn': customDataFn,
			'url': url,
			'titleHTML': title,
			'dataType' : 'json',
			'successFn': customSuccessFn ? customSuccessFn : successCallback,
			'errorFn'  : customErrorFn ? customErrorFn : errorCallback
		});
  	});
	
}

function getIDS(obj){
	var ids = [];
	
	var checkboxName = obj.attr('data-checkbox');
	var dataId = obj.attr('data-id');
	
	if(typeof(checkboxName) != 'undefined'){
		$("input[name='" + checkboxName + "']:checked").each(function(){
			ids.push($(this).val());
		});
	}else if(typeof(dataId) != 'undefined'){
		ids.push(obj.attr('data-id'));
	}
	
	return ids;
}


/**
 * 绑定
 * @param selector
 */
function bindCheckAllEvent(selector){
	$("body").delegate(selector,'click',function(){
		var group = $(this).prop("name");
		$("input[group='"+group+"']").prop("checked" , $(this).prop("checked") );
	});
}

/**
 * 
 * @param sucFn 成功事件回调
 * @param errorFn 失败事件回调
 */
function bindDeleteEvent(customSuccessFn,customErrorFn){
	var successCallback = function(ids,json){
		if(check_success(json.message)){
			showToast('success',json.message);
			
			for(var i = 0; i < ids.length; i++){
				$("#row" + ids[i]).remove();
			}
		}else{
			showToast('error',json.message);
		}
	}
	
	var errorCallback = function(){
		showToast('error',"删除出错，服务器异常，请稍后再次尝试");
	}
	
	$("a.delete").bind("click",function(){
		var triggerObj = $(this);
		
		var title = triggerObj.attr('data-title');
		if(typeof(title) == 'undefined'){
			title = '';
		}
		
		ui_confirm({
			'trigger':triggerObj,
			'postData': { id : [$(this).attr("data-id")] },
			'customDataFn': { } ,
			'url': triggerObj.attr("data-url"),
			'titleHTML': '你确定要删除<span class="hightlight">' + title + '</span>吗？',
			'dataType' : 'json',
			'successFn':customSuccessFn ? customSuccessFn : successCallback,
			'errorFn'  : customErrorFn ? customErrorFn : errorCallback
		});
		
  	});
  	
  	$(".deleteBtn").bind("click",function(){
  		var triggerObj = $(this);
  		var ids = getIDS(triggerObj);
  		var title = triggerObj.attr('data-title');
		if(typeof(title) == 'undefined'){
			title = '';
		}
		
  		if(ids.length == 0){
  			showToast('error','请选勾选.');
  		}else{
  		
	  		ui_confirm({
				'trigger':triggerObj,
				'postData': { id : ids },
				'customDataFn': { } ,
				'url': triggerObj.attr("data-url"),
				'titleHTML': '你确定要删除<span class="hightlight">' + title + '</span>吗？',
				'dataType' : 'json',
				'successFn':customSuccessFn ? customSuccessFn : successCallback,
				'errorFn'  : customErrorFn ? customErrorFn : errorCallback
			});
  		}
  	});
}


/**
 * ajax 提交
 * @param classname
 */
function bindAjaxSubmit(classname,optionParam){
	
	var lockFn = function(btn,name,lock){
		btn.attr('disabled',lock);
		formLock[name] = lock;
		
		if(lock == true){
			btn.addClass("disabled");
		}else{
			btn.removeClass("disabled");
		}
	}
	
	optionParam = optionParam ? optionParam : {};
	
	var ajaxSubmitFn = function(submitBtn){
		
		var opName = submitBtn.prop('value');
		var formObj = $(submitBtn).parents('form');
		
		//console.log(formObj);
		
		var formName = formObj.prop("name");
		var formActionUrl = formObj.prop("action");
		
		if(formLock[formName]){
			return false;
		}
		
		lockFn(submitBtn,formName,true);
		
		if(formActionUrl.indexOf('?') == -1){
			formActionUrl = formActionUrl + '?op=' + encodeURIComponent(opName);
		}else{
			formActionUrl = formActionUrl + '&op=' + encodeURIComponent(opName);
		}

		
		$.ajax({
			type:'POST',
			url: formActionUrl,
			dataType:'json',
			data:formObj.serialize(),
			success:function(resp){
				refreshFormHash(resp.data);
				
				$(".error").removeClass('error');
				$(".errtip").hide();
				
				if(!/成功/.test(resp.message)){
					showToast('error',resp.message, typeof(resp.data.toastSetting) != 'undefined' ? resp.data.toastSetting : { });
					
					var errors = resp.data.errors;
					var first = null;
					
					for(var f in errors){
						if(first == null){
							first = f;
						}
						
						$("#error_" + f).html(errors[f]).addClass("error").show();
					}
					
					if($("input[name=" + first + "]").size()){
						$("input[name=" + first + "]").addClass('error').focus();
					}else if($("select[name=" + first + "]").size()){
						$("select[name=" + first + "]").focus();
					}else if($("textarea[name=" + first + "]").size()){
						$("textarea[name=" + first + "]").addClass('error').focus();
					}
					
				}else{
					showToast('success',resp.message,typeof(resp.data.toastSetting) != 'undefined' ? resp.data.toastSetting : { });
					
					if(optionParam.success){
						optionParam.success(resp);
					}
				}
				
				
				//如果是非ajax 则解锁
				if(typeof(resp.data.formActionUrl) != 'undefined'){
					formObj.attr('action',resp.data.formActionUrl);
				}
				
				lockFn(submitBtn,formName,false);
				
				
				var runafter = function(pData){
			        if(pData.redirectUrl){
			            location.href = pData.redirectUrl;
			        }

			        if(pData.redirectInfo){
			            if(pData.redirectInfo.jsReload){
			                location.reload();
			            }else{
			                location.href = pData.redirectInfo.url;
			            }
			        }
			    }
			    
			    if(typeof(resp.data.wait) != "undefined"){
			        setTimeout(function(){
			            runafter(resp);
			        },resp.data.wait ? resp.data.wait * 1000 : 2000);
			    }else{
			        runafter(resp);
			    }
				    
			
			},
			error:function(xhr, textStatus, errorThrown){
				lockFn(submitBtn,formName,false);
				
				showToast('error',"服务器发生错误,请联系管理员");
			}
		});
	}
	
	$(classname + " input[type=submit]").bind('click',function(){
		//console.log($(this));
		var thatButton = $(this);
		
		if(optionParam.confirm){
			commonDialog = $("#showDlg" ).dialog({
				title: "提示",
				autoOpen: false,
				width: 280,
				modal: true,
			      buttons: {
			        "确定": function(){
			        	ajaxSubmitFn(thatButton);
			        	commonDialog.dialog( "close" );
			        },
			        "关闭": function() {
			        	commonDialog.dialog( "close" );
			        }
			   },
			   open:function(){
				  
			   }
			}).html('<span class="ui-icon ui-icon-alert fl"></span><strong>' + optionParam.confirmTitle + '</strong>').dialog("open");
			
		}else{
			
			ajaxSubmitFn(thatButton);
		}
		
		
		
		return false;
	});
	
}

//加载谈层
$.loadingbar = function(settings) {
    var defaults = {
        autoHide:true,
        replaceText:"正在刷新,请稍后...",
        container: 'body',
        showClose: false,
        wrapperClass:'',
        text:'正在操作，请稍候…',
        template:''
    };
    var xhr;
    var cfg = $.extend({},defaults,settings);
    var postext;

    if(cfg.container==='body'){
        postext = 'fixed';
    }else{
        postext = 'absolute';
        $(cfg.container).css({position:'relative'});
    }


    var spin_wrap,content_tpl;

    if(cfg.template && $(cfg.template).length){
    	content_tpl = $(cfg.template).html();
    }else{
        content_tpl = '<div class="loading_box '+cfg.wrapperClass+'"><div class="lightbox-content">\
                          <span class="loading_close">×</span>\
                          <i class="loading_icon">&nbsp;</i><span class="loading_text">'+cfg.text+'</span>\
                          </div></div>';
    }

    spin_wrap  = $('<div class="lightbox" style="display:none;position:'+postext+'">\
        <table cellspacing="0" class="ct"><tbody><tr><td class="ct_content"></td></tr></tbody></table>\
        </div>');

    spin_wrap.find(".ct_content").html(content_tpl);

    if(!cfg.showClose){
        spin_wrap.find(".loading_close").hide();
    }

    if(0 == $(cfg.container).find("> .lightbox").length){
        $(cfg.container).append(spin_wrap);
    }else{
        spin_wrap = $("> .lightbox",$(cfg.container));
    }

    $(document).ajaxSend(function(event, jqxhr, settings) {
        var surl = settings.url;
        var state = false;
        if(typeof cfg.urls != 'undefined'){
            $.each(cfg.urls,function(i,item){
                if($.type(item) === 'regexp'){
                    if(item.exec(surl)) {
                        state = true;
                        return false;
                    }
                }else if($.type(item) === 'string'){
                    if(item === surl) {
                        state = true;
                        return false;
                    }
                }else{
                    throw new Error('[urls] type error,string or regexp required');
                }
            });
        } else {
            spin_wrap.show();
        }

        if(state){
            spin_wrap.show();
        }

        if(cfg.showClose){
            $('.loading_close').on('click',function(e){
                jqxhr.abort();
                $.active = 0;
                spin_wrap.hide();
                $(this).off('click');
            });
        }
    });

    $(document).ajaxStop(function(e) {
        if(cfg.autoHide){
            spin_wrap.hide();
        }else{
            spin_wrap.find(".loading_text").html(cfg.replaceText);
        }
    });

    return spin_wrap;
}


function setIframeHeight(iframe) {
	if (iframe) {
		var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
		if (iframeWin.document.body) {
			iframe.height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
		}
	}
}


function doPrint(iframeId) {
	if($.browser.msie ){
		window.frames[iframeId].focus();
		window.frames[iframeId].print();
	}else{
		document.getElementById(iframeId).contentWindow.focus();
		document.getElementById(iframeId).contentWindow.print();
	}
}


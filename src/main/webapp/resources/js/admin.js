

/**
 * ajax
 * **/
function ajax(type, url, params){
    let result;
    console.log('params',params)
    $.ajax({
        type : type
        , url : url
        , contentType : "application/json"
        , data : JSON.stringify(params)
        , async : false
        , success : function(data){
            result = data;
        }, error : function(request, status, error){
            result = 'FAIL';
        }
    })
    return result;
}

function ajaxReturn(type, url, params){
    let result = ajax(type, url, params);
    console.log(result)
}




/**
 * 관리자 계정생성
 * **/
$('#managerSave').click(function(){
    let result = true;
    let jsonObj = {};
    let form = new FormData(document.querySelector('#managerSaveForm'));

    form.forEach((value, key) => {
        jsonObj[key] = value

        if(value == null || value == ''){
            result = false;
            alert('필수 값을 입력해주세요.')
        }

        $('input[name='+key+']').val('')

    })

    if(result){
        ajax("POST", "/admin/member/managerSave", jsonObj)
    }
})

function dataLoad(data){
    console.log(data)
}

function managerOne(one){
    let idx = one.getAttribute('data-idx');
    let result = ajax("POST","/admin/member/managerSelectOne", idx);
    console.log(result)
}

function statusNaming(status){
    let val = status.getAttribute('data-status');
    let res = '';
    switch(val){
        case '1' : res = '사용 중';
            break;
        case '2' : res = '휴면';
            break;
        case '3' : res = '탈퇴';
            break;
    }
    console.log(status)
    status.text(res);
    return res;
}



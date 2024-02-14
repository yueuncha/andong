

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

    $('#userFilter').change(function(){
        let type = this.options[this.selectedIndex].value;
        let result = ajax("POST", "/admin/user/filter",{"type":type})
        $('#dataTable')._fnAjaxUpdateDraw(settings, result)
        //console.log($('#dataTable'))
        //let listData = JSON.parse($('#dataTable').attr('data-list'))

        /*for (let i = 0; i < listData.length; i++) {
            console.log(listData[i])
        }*/



    })




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

  /*  $(document).ready(function(){
        var mapContainer = document.getElementById('map'), // 지도를 표시할 div
            mapOption = {
                center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
                level: 3 // 지도의 확대 레벨
            };

        // 지도를 생성합니다
        var map = new kakao.maps.Map(mapContainer, mapOption);

        // 주소-좌표 변환 객체를 생성합니다
        var geocoder = new kakao.maps.services.Geocoder();

    // 주소로 좌표를 검색합니다
            geocoder.addressSearch('제주특별자치도 제주시 첨단로 242', function(result, status) {

            // 정상적으로 검색이 완료됐으면
            if (status === kakao.maps.services.Status.OK) {

                var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

                // 결과값으로 받은 위치를 마커로 표시합니다
                var marker = new kakao.maps.Marker({
                    map: map,
                    position: coords
                });

                // 인포윈도우로 장소에 대한 설명을 표시합니다
                var infowindow = new kakao.maps.InfoWindow({
                    content: '<div style="width:150px;text-align:center;padding:6px 0;">우리회사</div>'
                });
                infowindow.open(map, marker);

                // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
                map.setCenter(coords);
            }
        });

    })*/

    function addressChange(store){
        let address = store.getAttribute("data-juso");
        console.log(address);
/*
        var geocoder = new kakao.maps.servicie.Geocoder();

        geocoder.addressSearch(address, function (result , status){

            if(status === kakao.maps.sercive.Status.OK){
                var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
                console.log(coords)
            }

        })*/

    }

    let jsonList = [];

    $(document).ready(function (){
        let jsonObj = {};
        let code = $('#codeList').val();

        /*$.ajax({
            type : 'GET'
            , url : 'https://mukkebi.com/app/mk081_new_business_list.php?sp_arr=0&AccessToken=c246023a61bfe92b&sp_bcode='+code[i]+'&lat=37.420025&AccessID=2886088&new_version=17&sp_category=0&page=2&bcode_count=10&lng=127.126778&mc_type=1&050_yn=Y'
            , contentType : "application/json"
            , async : false
            , success : function(data){
                jsonList.add(data)
            }, error : function(request, status, error){
                result = 'FAIL';
            }
        })*/

    })

    $('#codeName').change(function(){
        //console.log(code)
        //console.log(code.val())

        let code = document.getElementById('codeName');

        let bd_code = code.options[code.selectedIndex].value;
        console.log(bd_code)

        $.ajax({
            type : 'GET'
            , url : 'https://mukkebi.com/app/mk081_new_business_list.php?sp_arr=0&AccessToken=c246023a61bfe92b&sp_bcode='+bd_code+'&lat=37.420025&AccessID=2886088&new_version=17&sp_category=0&page=2&bcode_count=10&lng=127.126778&mc_type=1&050_yn=Y'
            , contentType : "multipart/file"
            , async : false
            , success : function(data){
                //ajax("POST", "/admin/store/one", data);
                alert('SUCCESS : '+ bd_code);
            }, error : function(request, status, error){
                result = 'FAIL';
            }
        })
    })


    function clickStoreView(one){
        // data-toggle="modal" data-target="#storeViewCheck"
        //$('#storeViewCheck').modal();
        //let store_idx = one.getAttribute("data-index");
        //let result = ajax("POST", "/admin/store/one", store_idx);

       // console.log(jsonList)

        let andongCode = [];


        for (let i = 0; i < jsonList.length; i++) {
            let andongList = [];

            for (let j = 0; j < jsonList[i].length; j++) {
                let andongObj = {};
                andongObj['str_idx'] = jsonList[i][j].sp_idx;
                andongObj['str_category'] = jsonList[i][j].sp_category
                andongObj['str_lat'] = jsonList[i][j].sp_x_pos
                andongObj['str_lng'] = jsonList[i][j].sp_y_pos
                andongObj['str_address'] = jsonList[i][j].sp_addr1 + jsonList[i][j].sp_addr2
                andongObj['operating_hour'] = jsonList[i][j].operating_hour
                andongObj['str_phone'] = jsonList[i][j].sp_tel

                andongList.add(andongObj)
            }

            andongCode.add(andongList);
        }

        ajax("POST", "/admin/store/one", andongCode)


    }

    $('#storeEmailSendButton').click(function(){
        let result = true;
        let jsonObj = {};
        let form = new FormData(document.querySelector('#storeEmailSend'));

        form.forEach((value, key) => {
            jsonObj[key] = value

            if(value == null || value == ''){
                result = false;

                alert('필수 값을 입력해주세요.')
            }

            $('input[name='+key+']').val('')

        })



    })


    $('#storyContents').ready(function (){
        $('#storyContents > p > img').css("width", "50%")
    })

    function fnBannerView(value) {
        let imageTag = document.getElementById('bannerImage');
        imageTag.setAttribute('src',value.dataset['image'])
        $('#bannerUrl').text(value.dataset['url'])
    }

    function fnBannerUpdate(obj, idx){
        let tr = obj.parentNode.parentNode;

        for (let i = 0; i <tr.childElementCount; i++) {
            let td = tr.children.item(i)
            if(i < 3){
                let tdText = td.textContent
                let inputTag = document.createElement('input')
                td.textContent = ''
                inputTag.setAttribute('id', 'banner'+i)
                inputTag.setAttribute('type', 'text')
                inputTag.setAttribute('class', 'form-control mb-2')
                inputTag.setAttribute('name', td.getAttribute('name'))
                inputTag.setAttribute('value', tdText)
                td.append(inputTag)
            }else{
                td.textContent = ''
                let bannerSaveBtn = document.createElement('a')
                let bannerSaveBtnSpan = document.createElement('span')
                bannerSaveBtnSpan.setAttribute('class', 'text')
                bannerSaveBtnSpan.innerText = '저장'
                bannerSaveBtn.setAttribute('class', 'btn btn-dark btn-icon-split')
                bannerSaveBtn.setAttribute('onclick', 'fnBannerSave(this, '+idx+')')
                bannerSaveBtn.append(bannerSaveBtnSpan)
                td.append(bannerSaveBtn)
            }
        }
    }

    function fnBannerSave(obj, idx){
        let bannerValues = obj.parentNode.parentNode;
        let dataValues = {
            "bn_idx" : idx
            , "bn_title" : $('input[name="bn_title"]').val()
            , "bn_img" : $('input[name="bn_img"]').val()
            , "bn_url" : $('input[name="bn_url"]').val()
        }

        let result = ajax('POST', '/admin/banner/update', dataValues)

        if(result > 0){
            alert('배너가 수정되었습니다.')
        }else{
            alert('오류! 관리자에게 문의.')
        }
        location.href = '/admin/banner'
    }

    function fnCreate(){
        console.log($('input[type="text"]').val())
        $('input[type="text"]').removeAttr("disabled");
        $('select').removeAttr("disabled");
    }

    function fnClear(selectedName){
        $('input[type="text"]').val('');
        $('input[type="text"]').attr("disabled", "true");
        $('select').attr("disabled", "true");
    }




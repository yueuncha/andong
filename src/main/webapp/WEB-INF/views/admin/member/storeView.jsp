userView.jsp<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="ko">
<%@include file="/resources/common/header.jsp"%>
<body id="page-top">
<!-- Page Wrapper -->
<div id="wrapper">
    <!-- sidebar -->
    <%@include file="/resources/common/sidebar.jsp"%>
    <div id="content-wrapper" class="d-flex flex-column">
        <!-- Main Content -->
        <div id="content">
            <%@include file="/resources/common/navbar.jsp"%>
            <!-- Begin Page Content -->
            <div class="container-fluid">

                <div class="row">
                    <div class="col-xl-12 col-lg-12">
                        <!-- DataTales Example -->
                        <div class="card shadow mb-4">
                            <div class="card-header py-3">
                                <h6 class="m-0 font-weight-bold text-primary">가맹점</h6>
                            </div>
                            <div class="card-body">
                                <select id = "codeName">
                                    <c:forEach items="${code}" var="codeNum">
                                        <option value="${codeNum}">${codeNum}</option>
                                    </c:forEach>
                                </select>
                                <div class="table-responsive">
                                    <table class="table table-bordered" id="dataTable">
                                        <thead>
                                        <tr>
                                            <th>카테고리</th>
                                            <th>상호명</th>
                                            <th>주소</th>
                                            <th>연락처</th>
                                            <th>소개글</th>
                                            <th>조회수</th>
                                            <th>추천수</th>
                                            <th>좌표x</th>
                                            <th>좌표y</th>
                                        </tr>
                                        </thead>
                                        <tfoot>
                                        <tr>
                                            <th>카테고리</th>
                                            <th>상호명</th>
                                            <th>주소</th>
                                            <th>연락처</th>
                                            <th>소개글</th>
                                            <th>조회수</th>
                                            <th>추천수</th>
                                            <th>좌표x</th>
                                            <th>좌표y</th>
                                        </tr>
                                        </tfoot>
                                        <tbody>

                                        <tr data-code="">

                                        </tr>

                                        <%--<c:forEach var="item" items="${data}" >
                                            <tr class ="jusoClass" onclick="clickStoreView(this)" data-index="${item.str_idx}" data-toggle="modal" data-target="#storeViewCheck"
                                                onload="addressChange(this)" data-juso="${item.str_dt_address}">
                                                <td>${item.ct_ko_nm}</td>
                                                <td>${item.str_dt_name}</td>
                                                <td>${item.str_dt_address}</td>
                                                <td>${item.str_phone}</td>
                                                <td>${item.str_dt_desc}</td>
                                                <td>${item.str_view_cnt}</td>
                                                <td>${item.str_like_cnt}</td>
                                                <td class="xxx"></td>
                                                <td class="yyy"></td>
                                            </tr>
                                        </c:forEach>--%>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div></div>
                <input type="hidden" name="code" value="${code}" id="codeList"/>
                    <div class="row">
                        <div class="col-xl-12 col-lg-12">
                            <!-- DataTales Example -->
                            <div class="card shadow mb-4">
                                <div class="card-header py-3">
                                    <h6 class="m-0 font-weight-bold text-primary">지도</h6>
                                </div>
                                <div class="card-body">
                                    <div style=" float: right; padding-right: 0.75em;">
                                        <a href="#" class="btn btn-primary btn-icon-split">
                                            <span class="text">좌표</span>
                                        </a>
                                    </div>

                                    <div id="map" style="width:100%;height:350px;"></div>
                                    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d26c78a8594a97c7762c44b137ecb9c7&libraries=services"></script>
                                    <script>

                                        testLine1 = new kakao.maps.Polyline();
                                        var testPath = [
                                            new kakao.maps.LatLng(37.3848641743963, 127.111424265041), new kakao.maps.LatLng(36.5792024592182, 128.765050471549)
                                        ]
                                        testLine1.setPath(testPath)
                                        console.log(testLine1.getPath());
                                        console.log(testLine1.getLength());
                                        var jusoLength = Math.round(testLine1.getLength())

                                        console.log(jusoLength/1000)

                                        /*let jusoTable = document.getElementsByClassName('jusoClass');

                                        for (let i = 0; i <jusoTable.length; i++) {
                                            let store = jusoTable[i].getAttribute('data-index');
                                            let address = jusoTable[i].getAttribute("data-juso");
                                            xyReturn(address, store);
                                        }

*/
                                        function xyReturn (address, store) {
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
                                            geocoder.addressSearch(address, function(result, status) {

                                                // 정상적으로 검색이 완료됐으면
                                                if (status === kakao.maps.services.Status.OK) {

                                                    var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
                                                    //console.log('result[0].y : ' ,result[0].y )
                                                    //console.log('result[0].x : ' ,result[0].x )
                                                    // 결과값으로 받은 위치를 마커로 표시합니다

                                                    let jsonObj = {};
                                                    jsonObj['str_idx'] = store;
                                                    jsonObj['str_lat'] = result[0].x;
                                                    jsonObj['str_lng'] = result[0].y;

                                                    console.log(jsonObj)

                                                    /*$.ajax({
                                                        type : "POST"
                                                        , url : "/admin/store/xy"
                                                        , contentType : "application/json"
                                                        , data : JSON.stringify(jsonObj)
                                                        , async : false
                                                        , success : function(data){
                                                        }, error : function(request, status, error){}
                                                    })*/

                                                }
                                            });
                                        }
                                    </script>
                                </div></div></div></div>


                <!-- 관리자 계정생성 modal -->
                <div class="modal fade" id="storeViewCheck" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
                     aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h6 class="modal-title" id="storeView">상세보기</h6>
                                <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">×</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <span style="color:red; font-size: 7px">* 표시는 필수 입력</span>
                                <form id="storeEmailSend" action="">
                                    <div class="form-group">
                                        <label for="storeEmail">
                                            <span style="color:red">*</span>
                                            <span>계정 생성</span>
                                        </label>
                                        <input class="form-control mb-2 " name="mb_email" type="email" id="storeEmail">
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button id="storeEmailSendButton" class="btn btn-primary" type="button" data-dismiss="modal">비밀번호 생성</button>
                                <button class="btn btn-secondary" type="button" data-dismiss="modal">취소</button>
                            </div>
                        </div>
                    </div>
                </div>

                </div>

            </div>
            <!-- /.container-fluid -->
        </div>
        <!-- main end -->
    </div>
    <!-- content-wrapper end-->
</div>
<!-- wrapper end -->
<%@include file="/resources/common/script.jsp"%>
</body>
</html>
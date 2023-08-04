<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                    <div class="col-xl-12 ">
                        <!-- DataTales Example -->
                        <div class="card shadow mb-4">
                            <div class="card-header py-3">
                                <h6 class="m-0 font-weight-bold text-primary">이 부분 관리하는 회원별로 변경하고( 1page로 데이터만 다르게 어차피 틀 똑같으니까
                                등급, 상태 값들 자동으로 적용되게( 사용중 어찌고 머 이런걸로) 그리고 가입일자 모양 수정하기</h6>
                            </div>

                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-bordered" id="dataTable">
                                        <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>사용자명</th>
                                            <th>회원등급</th>
                                            <th>가입일자</th>
                                            <th>회원상태</th>
                                        </tr>
                                        </thead>
                                        <tfoot>
                                        <tr>
                                            <th>ID</th>
                                            <th>사용자명</th>
                                            <th>회원등급</th>
                                            <th>가입일자</th>
                                            <th>회원상태</th>
                                        </tr>
                                        </tfoot>
                                        <tbody>
                                        <c:forEach var="item" items="${List}">
                                            <tr onclick="managerOne(this)" data-idx="${item.mb_idx}" >
                                                <td>${item.mb_id}</td>
                                                <td>${item.mb_nickname}</td>
                                                <td>${item.mb_level}</td>
                                                <td>${item.mb_regdate}</td>
                                                <td onload="statusNaming(this)" data-status="${item.mb_status}"></td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>

                        </div>
                        <div style="float: right; padding-right: 0.75em;">
                            <a href="#" data-toggle="modal" data-target="#managerAdd" class="btn btn-primary btn-icon-split">
                                <span class="text">추가</span>
                            </a>
                        </div>
                    </div>


                    <!-- 관리자 계정생성 modal -->
                    <div class="modal fade" id="managerAdd" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
                         aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h6 class="modal-title" id="exampleModalLabel">관리자 추가</h6>
                                    <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">×</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <span style="color:red; font-size: 7px">* 표시는 필수 입력</span>
                                    <form id="managerSaveForm" action="">
                                        <div class="form-group">
                                            <label for="managerSaveName">
                                                <span style="color:red">*</span>
                                                <span>사용자명</span>
                                            </label>
                                            <input class="form-control mb-2 " name="mb_nickname" type="text" id="managerSaveName">
                                        </div>
                                        <div class="form-group">
                                            <label for="managerSaveId">
                                                <span style="color:red">*</span>
                                                <span>ID</span>
                                            </label>
                                            <input class="form-control mb-2 " name="mb_id" type="text" id="managerSaveId">
                                        </div>
                                        <div class="form-group">
                                            <label for="managerSavePassword">
                                                <span style="color:red">*</span>
                                                <span>비밀번호</span>
                                            </label>
                                            <input class="form-control mb-2 " name="mb_pw" type="password" id="managerSavePassword">
                                        </div>
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button id="managerSave" class="btn btn-primary" type="button" data-dismiss="modal">저장</button>
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
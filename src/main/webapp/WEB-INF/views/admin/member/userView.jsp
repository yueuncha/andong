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
                    <div class="col-xl-12 col-lg-12">
                        <!-- DataTales Example -->
                        <div class="card shadow mb-4">
                            <div class="card-header py-3">
                                <h6 class="m-0 font-weight-bold text-primary">사용자 조회</h6>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <select id="userFilter" class="custom-select custom-select-sm form-control form-control-sm col-1"  style="display: inline-block; margin-left: 30px; float: right">
                                        <option value="">전체</option>
                                        <option value="1">회원</option>
                                        <option value="2">게스트</option>
                                    </select>
                                    <table class="table table-bordered" id="dataTable" data-list="${list}">
                                        <thead>
                                        <tr>
                                            <th>사용자명</th>
                                            <th>활동상태</th>
                                            <th>마케팅동의</th>
                                            <th>알림동의</th>
                                            <th>SNS</th>
                                            <th>이메일</th>
                                            <th>성별</th>
                                            <th>생년월일</th>
                                            <th>가입일자</th>
                                            <th>거주지역(시/도)</th>
                                            <th>거주지역(구/군)</th>
                                            <th>내/외국인</th>
                                        </tr>
                                        </thead>
                                        <tfoot>
                                        <tr>
                                            <th>사용자명</th>
                                            <th>활동상태</th>
                                            <th>마케팅동의</th>
                                            <th>알림동의</th>
                                            <th>SNS</th>
                                            <th>이메일</th>
                                            <th>성별</th>
                                            <th>생년월일</th>
                                            <th>가입일자</th>
                                            <th>거주지역(시/도)</th>
                                            <th>거주지역(구/군)</th>
                                            <th>내/외국인</th>
                                        </tr>
                                        </tfoot>
                                        <tbody id ="userData">
                                        <c:forEach var="item" items="${list}">
                                            <tr>
                                                <td>${item.user_name}</td>
                                                <td>${item.user_type}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${item.marketing_use eq 'Y'}">동의</c:when>
                                                        <c:otherwise>미동의</c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td><c:choose>
                                                    <c:when test="${item.push_use eq 'Y'}">동의</c:when>
                                                    <c:otherwise>미동의</c:otherwise>
                                                </c:choose></td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${item.mb_sns eq 'A'}">애플</c:when>
                                                        <c:when test="${item.mb_sns eq 'N'}">네이버</c:when>
                                                        <c:when test="${item.mb_sns eq 'K'}">카카오</c:when>
                                                        <c:when test="${item.mb_sns eq 'G'}">구글</c:when>
                                                        <c:otherwise>일반</c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>${item.mb_email}</td>
                                                <td><c:choose>
                                                    <c:when test="${item.mb_gender eq 'M'}">남성</c:when>
                                                    <c:otherwise>여성</c:otherwise>
                                                </c:choose></td>
                                                <td>${item.mb_birth}</td>
                                                <td>${item.mb_regdate}</td>
                                                <td>${item.mb_local}</td>
                                                <td>${item.mb_local2}</td>
                                                <td>${item.mb_foreigner}</td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- Sidebar -->
<ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

    <!-- Sidebar - Brand -->
    <a class="sidebar-brand d-flex align-items-center justify-content-center" href="#">
        <div class="sidebar-brand-text mx-3" style="font-size: 1.0rem;">안동스마트관광플랫폼</div>
    </a>

    <!-- Divider -->
    <hr class="sidebar-divider my-0">

    <!-- Nav Item - Dashboard -->
    <li class="nav-item active">
        <a class="nav-link" href="/admin">
            <i class="fas fa-fw fa-tachometer-alt"></i>
            <span>메인</span></a>
    </li>



    <!-- Nav Item - Pages Collapse Menu -->
    <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapse_user"
           aria-expanded="true" aria-controls="collapse_user">
            <i class="fas fa-fw fa-cog"></i>
            <span>회원관리</span>
        </a>
        <div id="collapse_user" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
            <div class="bg-white py-2 collapse-inner rounded">
                <a class="collapse-item" href="/admin/user">사용자 조회</a>
                <a class="collapse-item" href="/admin/inquiry">1:1 문의</a>
                <a class="collapse-item" href="/admin/report">신고 리스트</a>
<%--                <a class="collapse-item" href="/admin/member/manager">관리자 계정 관리</a>--%>
                <a class="collapse-item" href="/admin/faq">FAQ</a>
            </div>
        </div>
    </li>

    <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapse_category"
           aria-expanded="true" aria-controls="collapse_tour">
            <i class="fas fa-fw fa-cog"></i>
            <span>카테고리 관리</span>
        </a>
        <div id="collapse_category" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
            <div class="bg-white py-2 collapse-inner rounded">
                <a class="collapse-item" href="/admin/category/step?type=1">상위 카테고리</a>
                <a class="collapse-item" href="/admin/category/step?type=2">하위 카테고리</a>
            </div>
        </div>
    </li>

    <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapse_store"
           aria-expanded="true" aria-controls="collapse_store">
            <i class="fas fa-fw fa-cog"></i>
            <span>관광 데이터 관리</span>
        </a>
        <div id="collapse_store" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
            <div class="bg-white py-2 collapse-inner rounded">
                <a class="collapse-item" href="/admin/store?type=10">관광지 관리</a>
                <a class="collapse-item" href="/admin/store?type=11">맛집 관리</a>
                <a class="collapse-item" href="/admin/store?type=5">숙박 관리</a>
                <a class="collapse-item" href="/admin/store?type=7">체험 관리</a>
                <a class="collapse-item" href="/admin/review">리뷰 관리</a>
            </div>
        </div>
    </li>

    <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapse_tour"
           aria-expanded="true" aria-controls="collapse_tour">
            <i class="fas fa-fw fa-cog"></i>
            <span>앱 관리</span>
        </a>
        <div id="collapse_tour" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
            <div class="bg-white py-2 collapse-inner rounded">
                <a class="collapse-item" href="/admin/story">안동이야기</a>
                <a class="collapse-item" href="/admin/banner">배너 추가</a>
                <a class="collapse-item" href="/admin/board">피드 관리</a>
                <a class="collapse-item" href="/admin/tour">추천코스 관리</a>
            </div>
        </div>
    </li>


    <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapse_stamp"
           aria-expanded="true" aria-controls="collapse_tour">
            <i class="fas fa-fw fa-cog"></i>
            <span>스탬프 투어</span>
        </a>
        <div id="collapse_stamp" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
            <div class="bg-white py-2 collapse-inner rounded">
                <a class="collapse-item" href="/admin/stamp">스탬프 투어</a>
                <a class="collapse-item" href="/admin/stamp/user">사용자 스탬프 현황</a>
            </div>
        </div>
    </li>

    <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapse_push"
           aria-expanded="true" aria-controls="collapse_tour">
            <i class="fas fa-fw fa-cog"></i>
            <span>알림 발송</span>
        </a>
        <div id="collapse_push" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
            <div class="bg-white py-2 collapse-inner rounded">
                <a class="collapse-item" href="/admin/push">알림 발송</a>
                <a class="collapse-item" href="/admin/push/list">발송내역</a>
            </div>
        </div>
    </li>

    <li class="nav-item">
        <a class="nav-link collapsed" href="/admin/stats" data-toggle="collapse" data-target="#collapse_status"
           aria-expanded="true" aria-controls="collapse_tour">
            <i class="fas fa-fw fa-cog"></i>
            <span>통계</span>
        </a>
        <div id="collapse_status" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
            <div class="bg-white py-2 collapse-inner rounded">
                <a class="collapse-item" href="/admin/stats">사용자 통계</a>
                <a class="collapse-item" href="/admin/stats">관광지 정보 통계</a>
            </div>
        </div>
    </li>

    <!-- Divider -->
    <hr class="sidebar-divider d-none d-md-block">

    <!-- Sidebar Toggler (Sidebar) -->
    <div class="text-center d-none d-md-inline">
        <button class="rounded-circle border-0" id="sidebarToggle"></button>
    </div>
</ul>
<!-- End of Sidebar -->


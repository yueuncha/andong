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
        <a class="nav-link" href="#">
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
                <a class="collapse-item" href="/admin/member/user">일반 사용자 조회</a>
                <a class="collapse-item" href="/admin/member/store">가맹점주 조회</a>
                <a class="collapse-item" href="/admin/member/manager">관리자 계정 관리</a>
            </div>
        </div>
    </li>

    <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapse_store"
           aria-expanded="true" aria-controls="collapse_store">
            <i class="fas fa-fw fa-cog"></i>
            <span>가맹점관리</span>
        </a>
        <div id="collapse_store" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
            <div class="bg-white py-2 collapse-inner rounded">
                <a class="collapse-item" href="/member/userView">가맹점 조회</a>
                <a class="collapse-item" href="/member/userView">리뷰 관리</a>
            </div>
        </div>
    </li>

    <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapse_tour"
           aria-expanded="true" aria-controls="collapse_tour">
            <i class="fas fa-fw fa-cog"></i>
            <span>관광지관리</span>
        </a>
        <div id="collapse_tour" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
            <div class="bg-white py-2 collapse-inner rounded">
                <a class="collapse-item" href="/member/userView">일반 사용자 조회</a>
                <%--                <a class="collapse-item" href="#">404 Error</a>--%>
                <%--            </div>--%>
            </div>
        </div>
    </li>

    <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapse_statis"
           aria-expanded="true" aria-controls="collapse_statis">
            <i class="fas fa-fw fa-cog"></i>
            <span>통계</span>
        </a>
        <div id="collapse_statis" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
            <div class="bg-white py-2 collapse-inner rounded">
                <a class="collapse-item" href="/member/userView">일반 사용자 조회</a>
                <%--                <a class="collapse-item" href="#">404 Error</a>--%>
                <%--            </div>--%>
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


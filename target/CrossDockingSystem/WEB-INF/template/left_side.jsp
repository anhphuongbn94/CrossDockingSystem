<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s" %>

<meta charset="utf-8">
<section class="left-side">  
	<section class="logo">
		<a href="${pageContext.request.contextPath }/">
			<!-- <img src="resources/images/logo.png" alt="" width="200px" height="50px"> -->
			<img src="resources/images/logo.png" alt="">
		</a>
	</section>
	<section class="logo-icon text-center">
		<a href="${pageContext.request.contextPath }/">
			<img src="resources/icons/logo_icon.png" alt="">
		</a>
	</section>      
    <section class="left-side-inner">
        <section class="visible-xs hidden-sm hidden-md hidden-lg">
            <section class="media logged-user">
                <section class="media-body">
                    <h4><a href="#">Admin</a></h4>
                    <span>"Hello There..."</span>
                </section>
            </section>
            <h5 class="left-nav-title">Account Information</h5>
            <ul class="nav nav-pills nav-stacked custom-nav">
				<li><a href="#"><i class="fa fa-user"></i> <span><s:message code="user.profile" /></span></a></li>
				<li><a href="#"><i class="fa fa-cog"></i> <span><s:message code="user.setting" /></span></a></li>
				<li><a href="#"><i class="fa fa-sign-out"></i> <span><s:message code="user.signout" /></span></a></li>
            </ul>
        </section><!-- End Visible-XS ... -->
        <ul class="nav nav-pills nav-stacked custom-nav">
            <li class="active">
            	<a href="${pageContext.request.contextPath }/">
	            	<i class="fa fa-home"></i> 
	            	<span><s:message code="menu.home" /></span>
            	</a>
           	</li>
           	<li class="menu-list">
            	<a href=""><i class="fa fa-laptop"></i><span><s:message code="menu.door" /></span></a>
                <ul class="sub-menu-list">
                    <li><a href="${pageContext.request.contextPath }/indoor"><s:message code="menu.door.data" /></a></li>
                    <li><a href="${pageContext.request.contextPath }/setcost"><s:message code="menu.setcost" /></a></li>
                </ul>
            </li>
           	<li><a href="${pageContext.request.contextPath }/mtrstatus">
           			<i class="fa fa-tv"></i> 
            		<span><s:message code="menu.mtrstatus" /></span>
           		</a>
           	</li>
           	<li><a href="${pageContext.request.contextPath }/vehicle">
           			<i class="fa fa-truck"></i> 
            		<span><s:message code="menu.vehicle" /></span>
           		</a>
           	</li>
            <li class="menu-list">
            	<a href=""><i class="fa fa-truck"></i><span><s:message code="menu.invehicle" /></span></a>
                <ul class="sub-menu-list">
                    <li><a href="${pageContext.request.contextPath }/invehicle">
                    	<s:message code="menu.invehicle.data"/>
                   	</a></li>
                    <li><a href="${pageContext.request.contextPath }/unload">
                    	<s:message code="menu.invehicle.unload" />
                   	</a></li>
                </ul>
            </li>
            <li class="menu-list">
            	<a href=""><i class="fa fa-truck"></i><span><s:message code="menu.outvehicle" /></span></a>
                <ul class="sub-menu-list">
                    <li><a href="${pageContext.request.contextPath }/outvehicle">
                    	<s:message code="menu.outvehicle.data" />
                   	</a></li>
                    <li><a href="${pageContext.request.contextPath }/load">
                    	<s:message code="menu.outvehicle.load" />
                   	</a></li>
                </ul>
            </li>
            <li><a href="${pageContext.request.contextPath }/assign">
	            	<i class="fa fa-exchange"></i> 
	            	<span><s:message code="menu.assign" /></span>
            	</a>
           	</li>
            <li><a href="${pageContext.request.contextPath }/transfer">
            		<i class="fa fa-line-chart"></i> 
            		<span><s:message code="menu.transfer" /></span>
           		</a>
           	</li>
           	<li class="menu-list">
            	<a href=""><i class="fa fa-language"></i><span><s:message code="menu.language" /></span></a>
                <ul class="sub-menu-list">
                    <li><a href="?lang=en" ><s:message code="language.en" /></a></li>
                    <li><a href="?lang=vi"><s:message code="language.vi" /></a></li>
                </ul>
            </li>
            <li><a href="login"><i class="fa fa-sign-in"></i> <span><s:message code="menu.login" /></span></a></li>
        </ul><!-- End Nav Nav-pills -->
    </section><!-- End Left-Side-Inner -->
</section>
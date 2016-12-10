<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<section class="header-section">
	<a class="toggle-btn"><i class="fa fa-bars"></i></a>
	<section class="menu-right">
		<ul class="notification-menu">
			<li>
				<a href="#" class="btn btn-default dropdown-toggle" data-toggle="dropdown"> 
					<i class="fa fa-user"></i>
					Welcome ${em.fullname } 
					<span class="caret"></span>
				</a>
				<ul class="dropdown-menu dropdown-menu-usermenu pull-right">
					<li><a class="emp-profile option"><i class="fa fa-user"></i><s:message code="employee.changeprofile" /></a></li>
					<li><a class="emp-password option"><i class="fa fa-key"></i><s:message code="employee.changepass" /></a></li>
					<li><a href="${pageContext.request.contextPath }/logout" class="log-out option"><i class="fa fa-sign-out"></i><s:message code="employee.logout" /></a></li>
				</ul>
			</li>
			<li>
            	<a href="#" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
            		<i class="fa fa-language"></i>
            		<span class="caret"></span>
           		</a>
                <ul class="dropdown-menu dropdown-menu-usermenu pull-right">
                    <li><a href="?lang=en" ><s:message code="language.en" /></a></li>
                    <li><a href="?lang=vi"><s:message code="language.vi" /></a></li>
                </ul>
            </li>
		</ul><!-- End Notification-Menu -->
	</section><!-- End Menu-Right -->
	<section class="dialog-change-pass">
		    <form class="form-change-pass" method="post" id="changePass">
		    	<section class="text-center">
		            <h3 class="title"><s:message code="employee.changepass" /></h3>
		        </section>
		        <section class="msg text-center"></section>
		        <section class="wrap">
		            <input type="password" name="oldPass" class="form-control" placeholder="<s:message code="password.old" />" autofocus>
		            <input type="password" name="newPass" class="form-control" placeholder="<s:message code="password.new" />">
		            <input type="password" name="confirmPass" class="form-control" placeholder="<s:message code="password.confirm" />" >
		
					<section class="col-lg-12">
		            	<button type="submit" class="btn-primary form-control btn-change-pass"> 
		            			<s:message code="btn.change" />
            			</button>
            			<button type="reset" class="btn-primary form-control">
            				<s:message code="btn.clear" />
            			</button>
           			</section>
		        </section>
		    </form>
	</section><!-- End dialog-change-pass -->
	<section class="dialog-change-profile">
		<form class="form-change-profile" method="post" id="changeProfile">
		    	<section class="text-center">
		            <h3 class="title"><s:message code="employee.changeprofile" /></h3>
		        </section>
		        <section class="msg text-center"></section>
		        <section class="wrap">
		            <input type="text" name="fullname" class="form-control" value="${em.fullname }" placeholder="<s:message code="employee.fullname" />" autofocus>
		            <input type="text" name="email" class="form-control" value="${em.email }" placeholder="<s:message code="employee.email" />" >
		            <input type="text" name="address" class="form-control" value="${em.address }" placeholder="<s:message code="employee.address" />" >
		            <input type="text" name="phonenumber" class="form-control" value="${em.phonenumber}" placeholder="<s:message code="employee.phonenumber" />" >
		            <label style="margin-left: 130px">
		            	<c:choose>
			            	<c:when test="${em.gender==1 }"><input type="radio" name="gender" value="1" checked="checked"><s:message code="gender.male" /></c:when>
			            	<c:otherwise><input type="radio" name="gender" value="1"><s:message code="gender.male" /></c:otherwise>
			            </c:choose>
		            </label>
		            <label>
						<c:choose>
			            	<c:when test="${em.gender==0 }"><input type="radio" name="gender" value="0" checked="checked"><s:message code="gender.female" /></c:when>
			            	<c:otherwise><input type="radio" name="gender" value="0"><s:message code="gender.female" /></c:otherwise>
			            </c:choose>
		            </label>
					<section class="col-lg-12">
		            	<button type="submit" class="btn-primary form-control btn-change-profile"> 
		            			<s:message code="btn.change" />
            			</button>
            			<button type="reset" class="btn-primary form-control">
            				<s:message code="btn.clear" />
            			</button>
           			</section>
		        </section>
		    </form>
	</section>
</section><!-- End Header-Section -->
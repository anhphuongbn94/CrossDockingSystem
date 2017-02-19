<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>


	<section class="page-heading">
	    <h3><s:message code="employee.title" /></h3>
	</section><!-- End Page-Heading -->
	<section class="wrapper">
		<section class="row">
		<section class="col-lg-12">
		<section class="panel">
	    <header class="panel-heading"><s:message code="employee.title" />
			<span class="tools pull-right">
                <a href="javascript:;" class="fa fa-chevron-down"></a>
           	</span>	    
	    </header><!-- End Header -->
   		<section class="panel-body form-insert-iv">
   			<section class="msg-insert-iv" style="padding-left: 30px">
   			</section>
	        <form role="form" class="cmxform form-horizontal adminex-form" id="addEmp" method="post">
	        	<section class="form-group">
	                <label class="col-lg-2 control-label"><s:message code="employee.account" /></label>
	                <section class="col-lg-2">
	                    <input type="text" name="account" class="form-control">
	                </section>
	                
	                <label class="col-lg-2 control-label" style="margin-left: 10px;"><s:message code="employee.email" /></label>
	                <section class="col-lg-2">
	                    <input type="text" name="email" class="form-control">
	                </section>
	                
	                <label class="col-lg-2 control-label" style="margin-left: 50px;"><s:message code="employee.password" /></label>
	                <section class="col-lg-2">
	                    <input type="text" name="password" class="form-control">
	                </section>
	            </section>
	            <section class="form-group">
	            	<label class="col-lg-2 control-label"><s:message code="employee.fullname" /></label>
	                <section class="col-lg-2">
	                    <input type="text" name="fullname" class="form-control">
	                </section>
	            	
	                <label class="col-lg-2 control-label" style="margin-left: 10px;"><s:message code="employee.address" /></label>
	                <section class="col-lg-2">
	                    <input type="text" name="address" class="form-control">
	                </section>
	                
	                <label class="col-lg-2 control-label" style="margin-left: 50px;"><s:message code="employee.phonenumber" /></label>
	                <section class="col-lg-2">
	                    <input type="text" name="phonenumber" class="form-control">
	                </section>
	            </section>
	            
	            <section class="form-group">
	            	<label class="col-lg-2 control-label"><s:message code="employee.gender" /></label>
	                <section class="col-lg-2">
	                    <select name="gender" class="form-control">
	                    	<option value="0"><s:message code="gender.male" /></option>
	                    	<option value="1"><s:message code="gender.female" /></option>
	                    </select>
	                </section>
	            	
	                <label class="col-lg-2 control-label" style="margin-left: 10px;"><s:message code="employee.active" /></label>
	                <section class="col-lg-2">
	                    <select name="active" class="form-control">
	                    	<option value="0"><s:message code="active.status" /></option>
	                    	<option value="1"><s:message code="inactive.status" /></option>
	                    </select>
	                </section>
	                
	                <label class="col-lg-2 control-label" style="margin-left: 50px;"><s:message code="employee.level" /></label>
	                <section class="col-lg-2">
	                    <select name="level" class="form-control">
	                    	<option value="2"><s:message code="level.employee" /></option>
	                    	<option value="1"><s:message code="level.admin" /></option>
	                    </select>
	                </section>
	                
	                <section class="col-lg-1">
	                    <input class="btnDefault btn-primary" type="submit" 
	                    	value="<s:message code="btn.submit" />" id="btnAddIV" />
	                </section>
	            </section>
	        </form><!-- End Form -->
    	</section><!-- End Panel-Body -->
		</section><!-- End Panel -->
        </section><!-- End Col -->
        </section><!-- End Row -->
        <section class="row">
        <section class="col-sm-12">
        <section class="panel">
        <header class="panel-heading"><s:message code="invehicle.title.data" />
            <span class="tools pull-right">
                <a href="javascript:;" class="fa fa-chevron-down"></a>
             </span>
        </header><!-- End Panel-Heading -->
        <section class="panel-body">
        <section class="adv-table">
        	<section id="dynamic-table_wrapper" class="dataTables_wrapper" role="grid">
        		<section class="form-group">
        			<section class="col-lg-3">
			        	<section id="dynamic-table_length" class="dataTables_length">
							<label>
								<select class="recordEmp" size="1" name="dynamic-table_length" aria-controls="dynamic-table">
									<option value="15" selected="selected">15</option>
									<option value="30">30</option>
									<option value="50">50</option>
								</select> records per page
							</label>
						</section>
					</section>
					<section class="col-lg-5" style="float: right">
						<section class="dataTables_filter" id="dynamic-table_filter">
							<form method="post" id="btn-search-invehicle">
								<label>
									<input type="text" class="form-control key-invehicle-search" 
										aria-controls="dynamic-table">
									<button class="btn-primary btn-search"><i class="fa fa-search"></i></button>
								</label>
							</form>
						</section>
					</section>
				</section>
			</section>
	        <table class="display table table-bordered table-striped" id="dynamic-table">
		        <thead>
			        <tr>
			            <td><s:message code="employee.account" /></td>
			            <td><s:message code="employee.fullname" /></td>
						<td><s:message code="employee.gender" /></td>
			            <td><s:message code="employee.email" /></td>
 						<td><s:message code="employee.address" /></td>
						<td><s:message code="employee.phonenumber" /></td>
						<%-- <td><s:message code="employee.password" /></td> --%>
						<td><s:message code="employee.level" /></td>
						<td><s:message code="employee.active" /></td>
						<td colspan="1"><s:message code="label.option" /></td>
			        </tr>
		        </thead>
		        <tbody class="row_data">
		        	<c:if test="${pageEmp.size() > 0 }">
				        <c:forEach items="${pageEmp }" var="rows">
							<tr data-bind="${rows.idEmployee }">
								<td>${rows.username }</td>
								<td>${rows.fullname }</td>
								<td><c:if test="${rows.gender==0}">
										<s:message code="gender.female" />
									</c:if>
									<c:if test="${rows.gender==1}">
										<s:message code="gender.male" />
									</c:if>
								</td>
								<td>${rows.email }</td>
								<td>${rows.address }</td>
								<td>${rows.phonenumber }</td>
								<%-- <td>${rows.password }</td> --%>
								<td><c:if test="${rows.gender==1}">
										<s:message code="level.admin" />
									</c:if>
									<c:if test="${rows.gender==2}">
										<s:message code="level.employee" />
									</c:if>
								</td>
								<td><c:if test="${rows.active==0}">
										<s:message code="inactive.status" />
									</c:if>
									<c:if test="${rows.active==1}">
										<s:message code="active.status" />
									</c:if>
								</td>
								<td><span class="edit-employee option" 
											data-bind="${rows.idEmployee }">
										<i class="fa fa-pencil-square-o"></i>
									</span>
								</td>
							</tr>
						</c:forEach>
					</c:if>
		        </tbody>
	        </table><!-- End Display-Table -->
	        <c:if test="${pageEmp.size() < 1 }">
	        	<span class="no-record"><s:message code="msg.norecord"/></span>
	        </c:if>
	        <c:if test="${pageEmp.size() > 0 }">
	        	<section>
	        		Total: ${pager.totalItem } item-${pager.numPage } page
	        	</section>
		        <section class="dataTables_paginate paging_bootstrap pagination">
					<ul class="page-emp">
						<li class="prev"><a>←</a></li>
						<c:forEach items="${pager.listPage }" var="item">
							<c:choose>
								<c:when test="${item==1 }">
									<li class="page_index active" data-index="${item }"><a>${item }</a></li>
								</c:when>
								<c:otherwise>
									<li class="page_index" data-index="${item }"><a>${item }</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<li style="display: none" class="num-page" data-bind="${pager.numPage }"></li>
						<li class="next"><a>→ </a></li>
					</ul>
				</section><!-- End Pagination -->
			</c:if>
        </section><!-- End Adv-Table -->
        </section><!-- End Panel-Body-->
        </section><!-- End Panel -->
        </section><!-- End Col-Sm-12 -->
        </section><!-- End Row -->
	</section><!-- End Wraper -->

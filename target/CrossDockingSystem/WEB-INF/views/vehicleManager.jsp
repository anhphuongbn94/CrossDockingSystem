<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

		<section class="page-heading">
		    <h3><s:message code="vehicle.title" /></h3>
		</section><!-- End Page-Heading -->
		<section class="wrapper">
		<div id="ac-wrapper">
			<div class="img-load">
				<center>
					<i class="fa fa-spinner fa-pulse fa-3x fa-fw"></i>
				</center>
			</div>
		</div>
		<section class="row">
		<section class="col-sm-12">
		<section class="panel">
	    <header class="panel-heading"><s:message code="transfer.title.insert" />
			<span class="tools pull-right">
                <a href="javascript:;" class="fa fa-chevron-down"></a>
             </span>	    
	    </header><!-- End Header -->
   		<section class="panel-body form-insert-v"> 
	        <form role="form" class="cmxform form-horizontal adminex-form" id="addV" method="post">
	            <section class="form-group">
	                <label class="col-lg-2 control-label"><s:message code="vehicle.code" /></label>
	                <section class="col-lg-3">
	                    <input type="text" name="code" 
	                    	placeholder="<s:message code="vehicle.code" />" 
	                    	class="form-control"/>
	                </section>
	                <label class="col-lg-2 control-label"><s:message code="vehicle.company" /></label>
	                <section class="col-lg-3" >
	                    <input type="text" name="company" 
	                    	placeholder="<s:message code="vehicle.company" />" 
	                    	class="form-control" />
	                </section>
	            </section>
	            <section class="form-group">
	                <label class="col-lg-2 control-label"><s:message code="vehicle.type" /></label>
	                <section class="col-lg-3" >
	                    <input type="text" name="type" 
	                    	placeholder="<s:message code="vehicle.type" />" 
	                    	class="form-control"/>
	                </section>
	                <label class="col-lg-2 control-label"><s:message code="vehicle.des" /></label>
	                <section class="col-lg-3" >
	                    <input type="text" name="description" 
	                    	placeholder="<s:message code="vehicle.des" />" 
	                    	class="form-control" />
	                </section>
	                <section class="col-lg-1">
	                    <input class="btnDefault btn-primary" type="submit" 
	                    	value="<s:message code="btn.submit" />" id="btnV" />
	                </section>
	            </section>
	        </form><!-- End Form -->
    	</section><!-- End Panel-Body -->
		</section><!-- End Panel -->
        </section><!-- End Col -->
        <section class="col-sm-12">
        <section class="panel">
        <header class="panel-heading"><s:message code="vehicle.title.data" />
            <span class="tools pull-right">
                <a href="javascript:;" class="fa fa-chevron-down"></a>
                <!-- <a href="javascript:;" class="fa fa-times"></a>  -->
             </span>
        </header><!-- End Panel-Heading -->
        <section class="panel-body">
        <section class="adv-table">
	       <table class="display table table-bordered table-striped" id="dynamic-table">
		        <thead>
			        <tr>
						<th><s:message code="vehicle.code" /></th>
						<th><s:message code="vehicle.type" /></th>
						<th><s:message code="vehicle.company" /></th>
						<th><s:message code="vehicle.des"/></th>
			        </tr>
		        </thead>
		        <tbody class="row_data">
			        <c:forEach items="${pVehicle }" var="rows">
						<tr>
							<td>${rows.vehicleCode }</td>
							<td>${rows.type }</td>
							<td>${rows.company }</td>
							<td>${rows.description }</td>
						</tr>
					</c:forEach>
		        </tbody>
	        </table><!-- End Display-Table -->
	        <c:if test="${pVehicle.size() < 1 }">
	        	<span class="no-record"><s:message code="msg.norecord"/></span>
	        </c:if>
	        <c:if test="${pVehicle.size() > 0 }">
		        <section class="dataTables_paginate paging_bootstrap pagination">
					<ul class="page-vehicle">
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
		</section><!-- End Wrapper -->

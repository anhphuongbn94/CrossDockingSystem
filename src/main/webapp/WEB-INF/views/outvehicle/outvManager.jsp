<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>


	<section class="page-heading">
	    <h3><s:message code="outvehicle.title" /></h3>
	    <!-- <ul class="breadcrumb">
	        <li><a href="#">Dashboard</a></li>
	        <li><a href="#">Data Tables</a></li>
	        <li class="active"> Dynamic Table </li>
	    </ul> -->
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
		<section class="col-lg-12">
		<section class="panel">
	    <header class="panel-heading"><s:message code="outvehicle.title.insert" />
			<span class="tools pull-right">
                <a href="javascript:;" class="fa fa-chevron-down"></a>
             </span>	    
	    </header><!-- End Header -->
   		<section class="panel-body form-insert-ov">
   			<section class="msg-insert-ov" style="padding-left: 30px">
   			</section>
	        <form role="form" class="cmxform form-horizontal adminex-form" id="addOV" method="post">
	        	<section class="form-group">
	                <label class="col-lg-2 control-label"><s:message code="vehicle.code" /></label>
	                <section class="col-lg-3">
	                    <select class="s-vehicle" name="idVehicle">
	                    	<option value=""></option>
	                    	<c:forEach items="${listV }" var="rows">
	                    		<option value="${rows.idVehicle }">${rows.vehicleCode }</option>
	                    	</c:forEach>
		              	</select>
	                </section>
	            </section>
	            <section class="form-group">
	            	<label class="col-lg-2 control-label"><s:message code="outvehicle.date" /></label>
	                <section class="col-lg-3">
	                    <label>
	                   		<span class="btn-primary"><i class="fa fa-calendar"></i></span>
	                    	<input type="text" value="${currentDate }" 
	                    		name="date"
	                    		placeholder="<s:message code="invehicle.date" />"
	                    		class="default-date-picker form-control">
                   		</label>
	                </section>
	                <label class="col-lg-2 control-label"><s:message code="outvehicle.arrival" /></label>
	                <section class="col-lg-3">
	                    <label>
	                    	<span class="btn-primary"><i class="fa fa-clock-o"></i></span>
		                    <input type="text" name="arrivalTime"
		                    	placeholder="<s:message code="invehicle.arrival" />" 
		                    	class="timepicker-24 form-control" 
	                    	/>
                    	</label>
	                </section>
	                <section class="col-lg-1">
	                    <input class="btnDefault btn-primary" type="submit" 
	                    	value="<s:message code="btn.submit" />" id="btnAddOV" />
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
        <header class="panel-heading"><s:message code="outvehicle.title.data" />
            <span class="tools pull-right">
                <a href="javascript:;" class="fa fa-chevron-down"></a>
                <!-- <a href="javascript:;" class="fa fa-times"></a>  -->
             </span>
        </header><!-- End Panel-Heading -->
        <section class="panel-body">
        <section class="adv-table">
        	<section id="dynamic-table_wrapper" class="dataTables_wrapper" role="grid">
        		<section class="form-group">
        			<section class="col-lg-3">
			        	<section id="dynamic-table_length" class="dataTables_length">
							<label>
								<select class="recordO" size="1" name="dynamic-table_length" aria-controls="dynamic-table">
									<option value="15" selected="selected">15</option>
									<option value="30">30</option>
									<option value="50">50</option>
								</select> records per page
							</label>
						</section>
					</section>
					<section class="col-lg-5" style="float: right">
						<section class="dataTables_filter" id="dynamic-table_filter">
							<form method="post" id="btn-search-outvehicle">
								<label>
									<input type="text" class="form-control key-outvehicle-search" 
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
			            <td><s:message code="vehicle.code" /></td>
			            <td><s:message code="vehicle.company" /></td>
			            <td><s:message code="outvehicle.date" /></td>
						<td><s:message code="outvehicle.arrival" /></td>
						<td><s:message code="outvehicle.demand" /></td>
						<td><s:message code="outvehicle.status" /></td>
						<td><s:message code="outvehicle.outdoor" /></td>
						<td colspan="3"><s:message code="label.option" /></td>
			        </tr>
		        </thead>
		        <tbody class="row_data">
		        	<c:if test="${pOutVehicle.size() > 0 }">
				        <c:forEach items="${pOutVehicle }" var="rows">
							<tr>
								<td class="code" data-bind="${rows.vehicleCode }">
									${rows.vehicleCode }
								</td>
								<td class="company" data-bind="${rows.company }">
									${rows.company }
								</td>
								<td class="date label-date">${rows.date }</td>
								<td class="arrivalTime label-time" data-bind="${rows.arrivalTime }">
									${rows.arrivalTime }
								</td>
								<td>${rows.demand }</td>
								<td class="status" data-bind="${rows.status }">
									<c:if test="${rows.status == 0}"><s:message code="watting.assign.door.status" /></c:if>
									<c:if test="${rows.status == 1}"><s:message code="watting.load.status" /></c:if>
									<c:if test="${rows.status == 2}"><s:message code="start.load.status" /></c:if>
									<c:if test="${rows.status == 3}"><s:message code="finish.load.status" /></c:if>
								</td>
								<td>${rows.door.nameDoor }</td>
								<td><c:if test="${rows.status < 1 }">
									<span class="edit-outvehicle option-vehicle"
										data-bind="${rows.idOutVehicle }">
										<i class="fa fa-pencil-square-o"></i>
									</span>
								</c:if></td>
								<td><c:if test="${rows.status < 1 }">
									<span class="del-outvehicle option-vehicle"
											data-bind="${rows.idOutVehicle }">
										<i class="fa fa-trash-o" ></i>
									</span>
								</c:if></td>
								<td><span class="detail-outvehicle option-vehicle" 
										data-bind="${rows.idOutVehicle }">
									<i class="fa fa-list-alt"></i>
								</span></td>
							</tr>
						</c:forEach>
					</c:if>
		        </tbody>
	        </table><!-- End Display-Table -->
	        <c:if test="${pOutVehicle.size() < 1 }">
	        	<span class="no-record"><s:message code="msg.norecord"/></span>
	        </c:if>
	        <c:if test="${pOutVehicle.size() > 0 }">
		        <section class="dataTables_paginate paging_bootstrap pagination">
					<ul class="page-ov">
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
        <section class="dialog-pov">
        	<section id="tabs">
        		<ul>
				    <li><a href="#fragment-1"><span><s:message code="product.vehicle.tab"/></span></a></li>
				    <li><a href="#fragment-2"><span><s:message code="product.transform.tab" /></span></a></li>
			  	</ul>
			  	<div id="fragment-1">
		   			<section class="row" style="margin-top: -15px">
					<section class="col-sm-12">
					<section class="panel">
					<header class="panel-heading">
						<s:message code="product.vehicle.title" />
					</header>
					<section class="panel-body">
					<section class="adv-table">
						<table class="display table table-bordered table-striped" id="tbl-piv">
							<tr style="display: none">
								<td class="col-1"></td>
								<td class="col-2"></td>
								<td class="col-3"></td>
								<td class="col-4"></td>
								<td class="col-5"></td>
								<td class="col-6"></td>
							</tr>
							<thead>
								<tr>
									<td><s:message code="vehicle.code" /></td>
									<td><s:message code="vehicle.company" /></td>
									<td><s:message code="product.name" /></td>
									<td><s:message code="product.quantity" /></td>
									<td><s:message code="product.unit" /></td>
								</tr>
							</thead>
							<tbody class="rowPOV_data">
							</tbody>
					</table><!-- End Table -->
					</section><!-- End Panel Body -->
					</section><!-- End Adv Table -->
					</section><!-- End Panel -->
					</section><!-- End Col-SM-12 -->
		   			</section><!-- End Row -->
	   			</div>
	   			<div id="fragment-2">
       				<section class="row" style="margin-top: -15px">
					<section class="col-sm-12">
					<section class="panel">
					<header class="panel-heading">
						<s:message code="product.transform.title" />
					</header>
					<section class="panel-body">
					<section class="adv-table">
						<table class="display table table-bordered table-striped" id="tbl-piv">
							<tr style="display: none">
								<td class="col-1"></td>
								<td class="col-2"></td>
								<td class="col-3"></td>
								<td class="col-4"></td>
								<td class="col-5"></td>
								<td class="col-6"></td>
							</tr>
							<thead>
								<tr>
									<td><s:message code="invehicle.code" /></td>
									<td><s:message code="outvehicle.code" /></td>
									<td><s:message code="product.name" /></td>
									<td><s:message code="product.quantity" /></td>
									<td><s:message code="product.unit" /></td>
								</tr>
							</thead>
							<tbody class="rowPT_data">
							</tbody>
					</table><!-- End Table -->
					</section><!-- End Panel Body -->
					</section><!-- End Adv Table -->
					</section><!-- End Panel -->
					</section><!-- End Col-SM-12 -->
	       			</section><!-- End Row -->
       			</div><!-- End Fragment-2 -->
   			</section><!-- End Tabs -->
   		</section><!-- End Dialog -->
	</section><!-- End Wraper -->
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>


	<section class="page-heading">
	    <h3><s:message code="invehicle.title" /></h3>
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
	    <header class="panel-heading"><s:message code="invehicle.title.insert" />
			<span class="tools pull-right">
                <a href="javascript:;" class="fa fa-chevron-down"></a>
           	</span>	    
	    </header><!-- End Header -->
   		<section class="panel-body form-insert-iv">
   			<section class="msg-insert-iv" style="padding-left: 30px">
   			</section>
	        <form role="form" class="cmxform form-horizontal adminex-form" id="addIV" method="post">
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
	                <label class="col-lg-2 control-label"><s:message code="invehicle.date" /></label>
	                <section class="col-lg-3">
	                	<label>
	                   		<span class="btn-primary"><i class="fa fa-calendar"></i></span>
	                    	<input type="text" value="${currentDate }" 
	                    		name="date"
	                    		placeholder="<s:message code="invehicle.date" />"
	                    		class="default-date-picker form-control">
                   		</label>
	                </section>
	                <label class="col-lg-2 control-label"><s:message code="invehicle.arrival" /></label>
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
								<select class="recordI" size="1" name="dynamic-table_length" aria-controls="dynamic-table">
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
			            <td><s:message code="vehicle.code" /></td>
			            <td><s:message code="vehicle.company" /></td>
			            <td><s:message code="invehicle.date" /></td>
						<td><s:message code="invehicle.arrival" /></td>
 						<td><s:message code="invehicle.volumn" /></td>
						<td><s:message code="invehicle.status" /></td>
						<td><s:message code="invehicle.indoor" /></td>
						<td colspan="3"><s:message code="label.option" /></td>
			        </tr>
		        </thead>
		        <tbody class="row_data">
		        	<c:if test="${pInVehicle.size() > 0 }">
				        <c:forEach items="${pInVehicle }" var="rows">
							<tr data-bind="${rows.idInVehicle }">
								<td class="code" data-bind="${rows.vehicleCode }">${rows.vehicleCode }</td>
								<td class="company" data-bind="${rows.company }">${rows.company }</td>
								<td class="date label-date">${rows.date }</td>
								<td class="arrivalTime label-time" data-bind="${rows.arrivalTime}">
									${rows.arrivalTime }
								</td>
								<td class="volumn">${rows.volumn }</td>
								<td class="status" data-bind="${rows.status }">
									<c:if test="${rows.status == 0}"><s:message code="watting.assign.door.status" /></c:if>
									<c:if test="${rows.status == 1}"><s:message code="watting.unload.status" /></c:if>
									<c:if test="${rows.status == 2}"><s:message code="start.unload.status" /></c:if>
									<c:if test="${rows.status == 3}"><s:message code="finish.unload.status" /></c:if>
								</td>
								<td>${rows.door.nameDoor }</td>
								<td>
									<c:if test="${rows.status < 1}">
										<span class="edit-invehicle option-vehicle" 
												data-bind="${rows.idInVehicle }">
											<i class="fa fa-pencil-square-o"></i>
										</span>
									</c:if>
								</td>
								<td>
									<c:if test="${rows.status < 1}">
										<span class="del-invehicle option-vehicle" 
												data-bind="${rows.idInVehicle }">
											<i class="fa fa-trash-o"></i>
										</span>
									</c:if>
								</td>
								<td><span class="detail-invehicle option-vehicle" 
											data-bind="${rows.idInVehicle }">
										<i class="fa fa-list-alt"></i>
									</span>
								</td>
							</tr>
						</c:forEach>
					</c:if>
		        </tbody>
	        </table><!-- End Display-Table -->
	        <c:if test="${pInVehicle.size() < 1 }">
	        	<span class="no-record"><s:message code="msg.norecord"/></span>
	        </c:if>
	        <c:if test="${pInVehicle.size() > 0 }">
	        	<section>
	        		Total: ${pager.totalItem } item-${pager.numPage } page
	        	</section>
		        <section class="dataTables_paginate paging_bootstrap pagination">
					<ul class="page-iv">
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
        
        <section class="dialog-piv">
        	<section id="tabs">
        		<ul>
				    <li><a href="#fragment-1"><span>Product Vehicle</span></a></li>
				    <li><a href="#fragment-2"><span>Two</span></a></li>
			  	</ul>
			  	<div id="fragment-1">
		   			<section class="row">
					<section class="col-sm-12">
					<section class="panel" style="padding-left: 22px;">
					<section class="panel-body">
						<form role="form" class="cmxform form-horizontal adminex-form" id="frm-add-piv" style="margin-top: -10px;">
							<section class="form-group">
								<section id="msgInsertTab1">
								
								</section>
							</section>
							<section class="form-group">
				        		<label class="col-lg-2 control-label" style="width: 100px;">
				        			<s:message code="vehicle.code"/>
				        		</label>
				        		<section class="col-lg-2" style="width: 200px;">
									<input type="text" class="form-control code" disabled="disabled">
								</section>
								<label class="col-lg-2 control-label">
									<s:message code="vehicle.company" />
								</label>
				        		<section class="col-lg-2">
									<input type="text" class="form-control company" disabled="disabled">
								</section>
								<span class="status"></span>
							</section>
							<section class="form-group">
				        		<label class="col-lg-2 control-label" style="width: 110px; margin-left: -26px">
				        			<s:message code="product.name"/>
			        			</label>
				        		<section class="col-lg-3" style="width: 188px;">
									<select class="product s-product" name="pName">
										<option value=""></option>
										<c:forEach items="${listP }" var="rows">
											<option value="${rows.id }">${rows.name }</option>
										</c:forEach>
									</select>
								</section>
								<label class="col-lg-2 control-label" style="margin-left: -6px">
									<s:message code="product.quantity"/>
								</label>
				        		<section class="col-lg-1" style="width: 75px;">
									<input type="text" class="form-control quantity" name="pQuantity">
								</section>
								<label class="col-lg-1 control-label">
									<s:message code="product.unit"/>
								</label>
								<section class="col-lg-1" style="width: 75px">
									<select class="unit s-unit" name="pUnit">
										<c:forEach items="${listU }" var="rows">
											<option value="${rows.id }">${rows.name }</option>
										</c:forEach>
									</select>								
								</section>
								<section class="col-lg-2" style="margin-left: 8px">
									<input type="button" class="btn-primary form-control" id="btn-submit-piv" value="Submit" />
								</section>
							</section>
						</form><!-- End Form -->
					</section><!-- End Panel-Body -->
					</section><!-- End Panel -->
					</section><!-- End Col-Sm-12 -->
					<section class="col-sm-12">
					<section class="panel">
					<header class="panel-heading">
						<s:message code="invehicle.title.data" />
					</header>
					<section class="panel-body">
					<section class="adv-table">
						<section class="form-group">
							<section id="msgEditTabs1">
							
							</section>
						</section>
						<table class="display table table-bordered table-striped" id="tbl-piv">
							<tr style="display: none">
								<td class="col-1"></td>
								<td class="col-2"></td>
								<td class="col-3"></td>
								<td class="col-4"></td>
								<td class="col-5"></td>
								<td class="col-6"></td>
								<td class="col-7 col-option"></td>
							</tr>
							<thead>
								<tr>
									<td><s:message code="vehicle.code" /></td>
									<td><s:message code="vehicle.company" /></td>
									<td><s:message code="product.name" /></td>
									<td><s:message code="product.quantity" /></td>
									<td><s:message code="product.unit" /></td>
									<td colspan="2" class="col-option"><s:message code="label.option" /></td>
								</tr>
							</thead>
							<tbody class="rowPIV_data">
							</tbody>
					</table><!-- End Table -->
					</section><!-- End Panel Body -->
					</section><!-- End Adv Table -->
					</section><!-- End Panel -->
					</section><!-- End Col-SM-12 -->
		   			</section><!-- End Row -->
	   			</div><!-- End Frag-1 -->
	   			<div id="fragment-2">
       				<section class="row">
					<section class="col-sm-12">
					<section class="panel" style="padding-left: 10px;">
					<section class="panel-body">
						<form role="form" class="cmxform form-horizontal adminex-form" id="frm-add-pov" style="margin-top: -10px">
							<section class="form-group">
								<section id="msgInsertTab2">
								
								</section>
							</section>
							<section class="form-group">
				        		<label class="col-lg-2 control-label" style="width: 110px;">
				        			<s:message code="invehicle.code"/>
				        		</label>
				        		<section class="col-lg-2" style="width: 200px;">
									<input type="text" class="form-control code" disabled="disabled">
								</section>
								
								<label class="col-lg-2 control-label" style="width: 120px; margin-left: 25px">
				        			<s:message code="outvehicle.code"/>
				        		</label>
				        		<section class="col-lg-2" style="width: 200px;">
				        			<span class="data-ov" data-bind="${listOV }"></span>
									<select class="sl-ov s-vehicle" name="ovCode">
										<option value=""></option>
										<c:forEach items="${listOV }" var="rows">
											<option value="${rows.idOutVehicle }-${rows.arrivalTime}">
												${rows.vehicleCode }
											</option>
										</c:forEach>
									</select>
								</section>
							</section><!-- End Form-Group -->
							
							<section class="form-group">
								<label class="col-lg-2 control-label" style="width: 105px;">
				        			<s:message code="product.name"/>
			        			</label>
				        		<section class="col-lg-3" style="width: 188px; margin-left: 4px">
									<select class="sl-piv" name="pName">
										<option value=""></option>
									</select>
								</section>
								
								<label class="col-lg-2 control-label" style="width: 110px; margin-left: 25px;">
				        			<s:message code="invehicle.arrival"/>:
			        			</label>
			        			<label class="col-lg-2 control-label">
				        			<span class="arrival-time-ov"></span>
			        			</label>
							</section><!-- End Form-Group -->
							
							<section class="form-group">
								<label class="col-lg-2 control-label">
									<s:message code="product.quantity"/>: 
								</label>
								<label class="col-lg-1 control-label" style="margin-left: -30px">
									<span class="quantity-piv"></span>
								</label>
								
								<label class="col-lg-1 control-label" style="margin-left: 20px">
									<s:message code="product.unit"/>: 
								</label>
								<label class="col-lg-1 control-label" style="margin-left: -30px">
									<span class="unit-piv"></span>
								</label>
							
								<label class="col-lg-3 control-label" style="margin-left: 17px">
									<s:message code="product.quantity"/>
								</label>
				        		<section class="col-lg-1" style="width: 75px;">
									<input type="text" class="form-control quantity-ov" name="pQuantity">
								</section>
								
								<label class="col-lg-1 control-label">
									<s:message code="product.unit"/>
								</label>
								<section class="col-lg-1" style="width: 75px">
									<select class="unit-ov s-unit" name="pUnit">
										<c:forEach items="${listU }" var="rows">
											<option value="${rows.id }">${rows.name }</option>
										</c:forEach>
									</select>								
								</section>
								
								<section class="col-lg-1" style="width: 72px">
									<input type="button" class="btn-primary form-control" id="btn-submit-pov" value="Submit" />
								</section>
							</section><!-- End Form-Group -->
						</form>
					</section><!-- End Panel-Body -->
					</section><!-- End Panel -->
					</section><!-- End Col-Sm-12 -->
					<section class="col-sm-12">
					<section class="panel">
					<header class="panel-heading">
						<s:message code="invehicle.title.data" />
					</header>
					<section class="panel-body">
					<section class="adv-table">
						<section class="form-group">
							<section id="msgEditTabs2">
							
							</section>
						</section>
						<table class="display table table-bordered table-striped" id="tbl-pov">
							<tr style="display: none">
								<td class="col-1"></td>
								<td class="col-2"></td>
								<td class="col-3"></td>
								<td class="col-4"></td>
								<td class="col-5"></td>
								<td class="col-6"></td>
								<td class="col-7 col-option"></td>
							</tr>
							<thead>
								<tr>
									<td><s:message code="invehicle.code" /></td>
									<td><s:message code="outvehicle.code" /></td>
									<td><s:message code="product.name" /></td>
									<td><s:message code="product.quantity" /></td>
									<td><s:message code="product.unit" /></td>
									<td colspan="2" class="col-option"><s:message code="label.option" /></td>
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
       			</div><!-- End Fragment-2 -->
   			</section><!-- End Tabs -->
   		</section><!-- End Dialog -->
	</section><!-- End Wraper -->

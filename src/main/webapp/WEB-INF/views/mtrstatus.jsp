<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<section class="page-heading">
    <h3><s:message code="monitoring.vehicle.title" /></h3>
</section><!-- End Page-Heading -->
<section class="wrapper">
	<!-- InVehicle Data -->
	<c:forEach begin="0" end="${listPIV.size() - 1 }" var="i">
		<section class="row">
           <section class="col-sm-6">
               <section class="panel">
                   <header class="panel-heading">Data InVehicle
                      	<span class="tools pull-right">
                       	<a href="javascript:;" class="fa fa-chevron-down"></a>
                      	</span>
                   </header>
                   <section class="panel-body">
                       <table class="table table-hover">
                           <thead>
                            <tr>
                               	<th><s:message code="invehicle.code" /></th>
								<th><s:message code="invehicle.indoor" /></th>
								<th><s:message code="invehicle.status" /></th>
                            </tr>
                           </thead>
                           <tbody class="rowI_data">
                           	<c:forEach items="${listPIV[i] }" var="rows">
                           		<tr>
									<td>${rows.vehicleCode }</td>
									<td>${rows.door.nameDoor }</td>
									<c:if test="${rows.status == 0}"><td>Waitting Assign Door</td></c:if>
									<c:if test="${rows.status == 1}"><td>Watting Unload</td></c:if>
									<c:if test="${rows.status == 2}"><td>Is Unloading</td></c:if>
									<c:if test="${rows.status == 3}"><td>Loading is Finish</td></c:if>
								</tr>
                           	</c:forEach>
                           </tbody>
                       </table><!-- End Table -->
						<section class="dataTables_paginate paging_bootstrap pagination">
							<ul class="pI">
								<c:forEach begin="1" end="${listNIV[i] }" var="j">
							<li class="page_index" data-index="${j }"><a>${j }</a></li>
							</c:forEach>
							</ul>
						</section><!-- End Pagination -->
                   </section><!-- End Panel Body -->
               </section><!-- End Panel -->
           </section><!-- End Col-SM-6 -->
           <!-- OutVehicle Data -->
           <section class="col-sm-6">
               <section class="panel">
                   <header class="panel-heading">
                        Data OutVehicle
                        <span class="tools pull-right">
                            <a href="javascript:;" class="fa fa-chevron-down"></a>
                            <!-- <a href="javascript:;" class="fa fa-times"></a> -->
                         </span>
                   </header><!-- End Panal-Heading -->
                   <section class="panel-body">
                       <table class="table table-hover">
                           <thead>
                           <tr>
	                           	<th><s:message code="outvehicle.code" /></th>
								<th><s:message code="outvehicle.outdoor" /></th>
								<th><s:message code="outvehicle.status" /></th>
                           </tr>
                           </thead>
                           <tbody class="rowO_data">
                           	<c:forEach items="${listPOV[i] }" var="rows">
                           		<tr>
									<td>${rows.vehicleCode }</td>
									<td>${rows.door.nameDoor }</td>
									<c:if test="${rows.status == 0}"><td>Waitting Assign Door </td></c:if>
									<c:if test="${rows.status == 1}"><td>Waitting Load </td></c:if>		
									<c:if test="${rows.status == 2}"><td>Is Loading</td></c:if>								
									<c:if test="${rows.status == 3}"><td>Loading is Finish</td></c:if>
								</tr>
                           	</c:forEach>
                           </tbody>
                       </table><!-- End Table -->
						<section class="dataTables_paginate paging_bootstrap pagination">
							<ul class="pO">
							<c:forEach begin="1" end="${listNOV[i] }" var="j">
							<li class="page_index" data-index="${j }"><a>${j }</a></li>
							</c:forEach>
							</ul>
						</section><!-- End Page -->
                   </section><!-- End Panel-Body -->
               </section><!-- End Panel -->
           </section><!-- End Col-SM-6 -->
       </section><!-- End Row -->
      </c:forEach>
</section><!-- End Wrapper -->

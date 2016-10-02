<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<section class="page-heading">
    <h3><s:message code="invehicle.title" /></h3>
</section><!-- End Page-Heading -->
<section class="wrapper">		
	<!--------------------------------------------------------------------------------------------------->
       <!-- InVehicle -->
       <section class="row">
       <section class="col-sm-12">
       <section class="panel">
       <header class="panel-heading"><s:message code="invehicle.title.assign" />
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
		            <th><s:message code="invehicle.code" /></th>
					<th><s:message code="invehicle.volumn" /></th>
					<th><s:message code="invehicle.arrival" /></th>
					<c:set var="numDoor" value="0" />						
					<c:forEach items="${listInDoor }" var="rowID">
						<c:set var="remain" value="${rowID.capacity }" />
						<c:set var="uIDoor" value="0" />
						<c:forEach items="${pInVehicle }" var="rowIV">
							<c:if test="${rowID.idDoor == rowIV.door.idDoor}">
								<c:set var="remain" value="${remain - rowIV.volumn}"/>
								<c:set var="uIDoor" value="${uIDoor + rowIV.volumn}"/>
							</c:if>
						</c:forEach>
						<th style="text-align: center;">
							${rowID.nameDoor }
							<br/>
							<span class="uIDoor-${numDoor }" style="display: none">${uIDoor }</span>
							(<span class="cIDoor-${numDoor}">${rowID.capacity }</span>)
						</th>
						<c:set var="numDoor" value="${numDoor + 1 }" />
					</c:forEach>
					<th style="display: none"><span class="sizeIDoor">${listInDoor.size() }</span></th>
		        </tr>
	        </thead>
	        <tbody class="rowI_data">
				<c:set var="group" value="0" />
				<c:set var="uDoor" value="0" />
				<tr><td><span class="size-in-vehicle" data-bind="${pInVehicle.size() }"></span></td></tr>
				<c:set var="n" value="0" />
		        <c:forEach items="${pInVehicle }" var="rowIV">
					<tr class="gIDoor">
						<td>${rowIV.vehicleCode }</td>
						<td><span class="volumn" data-bind="${rowIV.volumn }">${rowIV.volumn }</span>
							<span class="volumn-${n }" data-bind=${rowIV.volumn }></span>
							<c:set var="n" value="${n + 1 }"/>
						</td>
						<td>${rowIV.arrivalTime }</td>
						<c:set var="numDoor" value="0" />		
						<c:forEach items="${listInDoor }" var="rowID">
							<c:set var="remain" value="${rowID.capacity }" />
							<c:forEach items="${pInVehicle }" var="rowIv">
								<c:if test="${rowID.idDoor == rowIv.door.idDoor}">
									<c:set var="remain" value="${remain - rowIv.volumn}"/>
								</c:if>
							</c:forEach>
							<td style="text-align: center;">
								<c:choose>
									<c:when test="${rowIV.door.idDoor == rowID.idDoor}">
										<input type="radio" id="iDoor"
											name="groupI-${group }" class="iDoor-${numDoor }" 
											data-idInVehicle="${rowIV.idInVehicle }" 
											data-index="${numDoor }" 
											data-idDoor="${rowID.idDoor }"
											checked="checked" data-flag="choose"/>
									</c:when> 
									<c:otherwise>
										<input type="radio" 
											name="groupI-${group }" class="iDoor-${numDoor }" 
											data-idInVehicle="${rowIV.idInVehicle }" 
											data-index="${numDoor }" 
											data-idDoor="${rowID.idDoor }"/>
									</c:otherwise>
								</c:choose>
								<span class="remain">${remain }</span>
							</td>
							<c:set var="numDoor" value="${numDoor + 1 }" />
						</c:forEach>
					</tr>
					<c:set var="group" value="${group + 1 }" />
				</c:forEach>
				<tr style="display: none"><td><span class="sizeGroup">${group }</span></td></tr>
	        </tbody>
        </table><!-- End Display-Table -->
       </section><!-- End Adv-Table -->
       <section class="dataTables_paginate paging_bootstrap pagination">
			<ul class="page-invehicle-assign-door">
				<c:forEach begin="1" end="${numPageI }" var="i">
					<li class="page_index" data-index="${i }"><a>${i }</a></li>
				</c:forEach>
			</ul>
		</section><!-- End Pagination -->
       </section><!-- End Panel-Body-->
       </section><!-- End Panel -->
       </section><!-- End Col-Sm-12 -->
       </section><!-- End Row --> 
       <!--------------------------------------------------------------------------------------------------->
       <!-- OutVehicle -->
       <section class="row">
       <section class="col-sm-12">
       <section class="panel">
       <header class="panel-heading"><s:message code="outvehicle.title.assign" />
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
		            <th><s:message code="outvehicle.code" /></th>
					<th><s:message code="outvehicle.demand" /></th>
					<th><s:message code="outvehicle.arrival" /></th>
					<c:set var="numDoor" value="0" />						
					<c:forEach items="${listOutDoor }" var="rowOD">
						<c:set var="remain" value="${rowOD.capacity }" />
						<c:set var="uODoor" value="0" />
						<c:forEach items="${pOutVehicle }" var="rowOV">
							<c:if test="${rowOD.idDoor == rowOV.door.idDoor}">
								<c:set var="remain" value="${remain - rowOV.demand}"/>
								<c:set var="uODoor" value="${uODoor + rowOV.demand}"/>
							</c:if>
						</c:forEach>
						<th style="text-align: center;">
							${rowOD.nameDoor }
							<br/>
							<span class="uODoor-${numDoor }" style="display: none">${uODoor }</span>
							(<span class="cODoor-${numDoor}">${rowOD.capacity }</span>)
						</th>
						<c:set var="numDoor" value="${numDoor + 1 }" />
					</c:forEach>
					<th style="display: none"><span class="sizeODoor">${listOutDoor.size() }</span></th>
		        </tr>
	        </thead>
	        <tbody class="rowO_data">
				<c:set var="group" value="0" />
				<c:set var="uDoor" value="0" />
				<tr><td><span class="size-out-vehicle" data-bind="${pOutVehicle.size() }"></span></td></tr>
				<c:set var="n" value="0" />
		        <c:forEach items="${pOutVehicle }" var="rowOV">
					<tr class="gODoor">
						<td>${rowOV.vehicleCode }</td>
						<td><span class="demand" data-bind="${rowOV.demand }">${rowOV.demand }</span>
							<span class="demand-${n }" data-bind="${rowOV.demand }"></span>
							<c:set var="n" value="${n + 1 }" />
						</td>
						<td>${rowOV.arrivalTime }</td>
						<c:set var="numDoor" value="0" />		
						<c:forEach items="${listOutDoor }" var="rowOD">
							<c:set var="remain" value="${rowOD.capacity }" />
							<c:forEach items="${pOutVehicle }" var="rowOv">
								<c:if test="${rowOD.idDoor == rowOv.door.idDoor}">
									<c:set var="remain" value="${remain - rowOv.demand}"/>
								</c:if>
							</c:forEach>
							<td style="text-align: center;">
								<c:choose>
									<c:when test="${rowOV.door.idDoor == rowOD.idDoor}">
										<input type="radio" id="oDoor"
											name="groupO-${group }" class="oDoor-${numDoor }" 
											data-idOutVehicle="${rowOV.idOutVehicle }" 
											data-index="${numDoor }" 
											data-idDoor="${rowOD.idDoor }"
											checked="checked" data-flag="choose"/>
									</c:when> 
									<c:otherwise>
										<input type="radio" 
											name="groupO-${group }" class="oDoor-${numDoor }" 
											data-idOutVehicle="${rowOV.idOutVehicle }" 
											data-index="${numDoor }" 
											data-idDoor="${rowOD.idDoor }"/>
									</c:otherwise>
								</c:choose>
								<span class="remain">${remain }</span>
							</td>
							<c:set var="numDoor" value="${numDoor + 1 }" />
						</c:forEach>
					</tr>
					<c:set var="group" value="${group + 1 }" />
				</c:forEach>
				<tr style="display: none"><td><span class="sizeGroup">${group }</span></td></tr>
	        </tbody>
        </table><!-- End Display-Table -->
        <section class="dataTables_paginate paging_bootstrap pagination">
			<ul class="page-outvehicle-assign-door">
				<c:forEach begin="1" end="${numPageO }" var="i">
					<li class="page_index" data-index="${i }"><a>${i }</a></li>
				</c:forEach>
			</ul>
		</section><!-- End Pagination -->
		<br/>
		<section>
        	<button class="btnDefault btn-primary" id="btnAssDoor">Submit</button>
        	<button class="btnDefault btn-primary" id="btnAssAI">AI Assign</button>
        </section>
        <section>
        	Total cost: <span class="total-cost">0</span> <br/>
        	AI Total cost: <span class="total-cost-ai">0</span>
        </section>
       </section><!-- End Adv-Table -->
       </section><!-- End Panel-Body-->
       </section><!-- End Panel -->
       </section><!-- End Col-Sm-12 -->
       </section><!-- End Row -->
       <section class="row">
       	<c:forEach begin="0" end="${listCost.size()-1 }" var="i">
       		<span class="cost-${i }" data-bind="${listCost[i].cost }"></span>
       	</c:forEach>
       	<span class="size-transfer" data-bind="${listPT.size() }"></span>
       	<c:if test="${listPT.size() != 0 }">
	       	<c:forEach begin="0" end="${listPT.size()-1 }" var="i">
	       		<span class="trip-${listPT[i].iVehicle.idInVehicle }-${listPT[i].oVehicle.idOutVehicle}" data-bind="${listPT[i].transfer }"></span>
	       		<span class="trip-${i }" data-bind="${listPT[i].transfer }"></span>
	       	</c:forEach>
       	</c:if>
       </section>
</section><!-- End Wraper -->
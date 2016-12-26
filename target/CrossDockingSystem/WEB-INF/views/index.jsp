<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>

	<section class="page-heading">
		<h3><s:message code="home.title"/></h3>
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
	<section class="panel-body">
		<div id="container" style="min-width: 100%; height: 300px; margin: 0 auto"></div>
		<section class="col-sm-2" style="width: 170px;">
			<div>InVehicle to the day: 30</div>
			<div>Total InVehicle: 350</div>
			<div>Company 1: 60</div>
			<div>Company 2: 140</div>
			<div>Company 3: 80</div>
			<div>Company 4: 70</div>
		</section>
		<section class="col-sm-4" style="width: 270px;">
			<div id="company-iv" style="width: 100%; height: 200px;"></div>
		</section>
		<section class="col-sm-2" style="width: 170px;">		
			<div>OutVehicle to the day: 20</div>
			<div>Total OutVehicle: 345</div>
			<div>Company 1: 145</div>
			<div>Company 2: 80</div>
			<div>Company 3: 60</div>
			<div>Company 4: 60</div>
		</section>
		<section class="col-sm-4" style="width: 270px">
			<div id="company-ov" style="width: 100%; height: 200px;"></div>
		</section>
	</section><!-- End Panel-Body -->
	</section><!-- End Panel -->
	</section><!-- End Col -->
	</section><!-- End Row -->
	<script src="https://code.highcharts.com/highcharts.js"></script>
	<script>
		    var chart=Highcharts.chart('container', {
		        title: {
		            text: 'The number of vehicle in Month',
		            x: -20 //center
		        },
		        subtitle: {
		            text: '',
		            x: -20
		        },
		        xAxis: {
		            categories: ['Day1','Day2','Day3','Day4','Day5','Day6','Day7','Day8','Day9','Day10','Day11','Day12','Day13','Day14','Day15','Day16','Day17','Day18','Day19','Day20','Day21','Day22','Day23','Day24','Day25','Day26','Day27','Day28','Day29','Day30']
		        },
		        yAxis: {
		            title: {
		                text: ''
		            },
		            plotLines: [{
		                value: 0,
		                width: 1,
		                color: '#528B8B'
		            }]
		        },
		        tooltip: {
		            valueSuffix: ''
		        },
		        legend: {
		            layout: 'vertical',
		            align: 'right',
		            verticalAlign: 'middle',
		            borderWidth: 0
		        },
		        series: [{
		            name: 'InVehicle',
		            data: [20, 25, 30, 25, 20, 25, 30, 25, 20, 30, 35, 30, 0, 0, 0, 0, 0, 0, 0, 0 ,0 , 0, 0, 0, 0, 0, 0, 0, 0, 0],
		            color: '#000080'
		        }, {
		            name: 'OutVehicle',
		            data: [25, 20, 35, 40, 20, 30, 35, 45, 10, 40, 25, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0 , 0, 0, 0, 0, 0, 0, 0, 0],
		            color: '#B22222'
		        }]
		    });
            Highcharts.chart('company-iv', {
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false,
                    type: 'pie'
                },
                title: {
                    text: ''
                },
                tooltip: {
                    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: false
                        },
                        showInLegend: true
                    }
                },
                series: [{
                    name: 'Brands',
                    colorByPoint: true,
                    data: [{
                        name: 'Company 1',
                        y: 17.15
                    }, {
                        name: 'Company 2',
                        y: 40,
                    }, {
                        name: 'Company 3',
                        y: 22.85
                    }, {
                        name: 'Company 4',
                        y: 20
                    }]
                }]
            });
            Highcharts.chart('company-ov', {
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false,
                    type: 'pie'
                },
                title: {
                    text: ''
                },
                tooltip: {
                    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: false
                        },
                        showInLegend: true
                    }
                },
                series: [{
                    name: 'Brands',
                    colorByPoint: true,
                    data: [{
                        name: 'Company 1',
                        y: 42.03
                    }, {
                        name: 'Company 2',
                        y: 23.18,
                    }, {
                        name: 'Company 3',
                        y: 20.29
                    }, {
                        name: 'Company 4',
                        y: 17.4
                    }]
                }]
            });
	</script>
		
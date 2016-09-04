var json = "{"
		+ "\"nbInDoors\":" + nbInDoors
		+ ",\"nbOutDoors\":" + nbOutDoors
		+ ",\"cost\":[";
		for(i = 0; i < nbInDoors; i++){
			var r = cost[i];
			json += "[";
			for(j = 0; j < nbOutDoors; j++){
				e = r[j];
				json += e;
				if(j < nbOutDoors-1)
					json += ",";
			}
			json += "]";
			if(i < nbInDoors -1)
				json += ",";
		}
		json += "]";	
	
	json += ",\n";
	json += "\"nbInVehicles\":" + nbInVehicles
		+ ",\"nbOutVehicles\":" + nbOutVehicles
	json += ",\"trips\":[";
	for(i = 0; i < nbInVehicles; i++){
		var r = trips[i];
		json += "[";
		for(j = 0; j < nbOutVehicles; j++){
			e = r[j];
			json += e;
			if(j < nbOutVehicles-1)
				json += ",";
		}
		json += "]";
		if(i < nbInVehicles -1)
			json += ",";
	}

	json += "]\n";
	
	for(i = 1; i <= nbInDoors; i++){
		json += ",\"capacity-indoor-" + i + "\":" + document.getElementById("capacity-in-door-" + i).value;
	}
	for(i = 1; i <= nbOutDoors; i++){
		json += ",\"capacity-outdoor-" + i + "\":" + document.getElementById("capacity-out-door-" + i).value;
	}
	for(i = 1; i <= nbInVehicles; i++){
		json += ",\"demand-in-vehicle-" + i + "\":" + document.getElementById("demand-in-vehicle-" + i).value;
	}
	for(i = 1; i <= nbOutVehicles; i++){
		json += ",\"demand-out-vehicle-" + i + "\":" + document.getElementById("demand-out-vehicle-" + i).value;
	}
	
	json += "}";
	$.ajax({
		type:'POST',
		url: baseUrl + "/solve-cross-docking-system.html",
		data: "input=" + json,
		success: function(result){
			console.log(result);
			var o = jQuery.parseJSON(result);
			var sol_invehicles = o.invehicles;
			var sol_outvehicles = o.outvehicles;
			var rs = "";
			rs += "Algorithm Solution = " + o.best + " (";
			for(i = 0; i < sol_invehicles.length; i++){
				var e = sol_invehicles[i];
				rs += " in-vehicle " + e.id + " => door " + e.door + ', \n';
				
				var iv = document.getElementById("in-vehicle-" + e.id).value = "indoor" + e.door;
			}
			for(i = 0; i < sol_outvehicles.length; i++){
				var e = sol_outvehicles[i];
				rs += "out-vehicle " + e.id + " => door = " + e.door + ', \n';
				
				var iv = document.getElementById("out-vehicle-" + e.id).value = "outdoor" + e.door;
			}
			
			rs += " )";
			
			document.getElementById("algorithm_result").innerHTML = rs;
		}
	});
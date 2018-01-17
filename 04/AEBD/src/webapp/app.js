var app = angular.module('app', ['ngRoute']);

app.config(function($routeProvider, $locationProvider) {

		$locationProvider.html5Mode(true);

		$routeProvider
			.otherwise({redirectTo : '/'})

			.when('/', {
				templateUrl : 'pages/home.html',
				controller  : 'mainController'
			})

			.when('/users', {
				templateUrl : 'pages/users.html',
				controller  : 'usersController'
			})

			.when('/tablespaces', {
				templateUrl : 'pages/tablespaces.html',
				controller  : 'tablespacesController'
			})

			.when('/memory', {
				templateUrl: 'pages/memory.html',
				controller: 'memoryController'
			})

			.when('/sessions', {
				templateUrl: 'pages/sessions.html',
				controller: 'sessionsController'
			})

			.when('/io_reads', {
				templateUrl: 'pages/io_reads.html',
				controller: 'ioReadsController'
			})

			.when('/io_writes', {
				templateUrl: 'pages/io_writes.html',
				controller: 'ioWritesController'
			})

			.when('/datafiles_history', {
				templateUrl: 'pages/datafiles_history.html',
				controller: 'datafilesHController'
			})

			.when('/sessions_history', {
				templateUrl: 'pages/sessions_history.html',
				controller: 'sessionsHController'
			})

			.when('/tables', {
				templateUrl: 'pages/tables.html',
				controller: 'tablesController'
			})

			.when('/tables_history', {
				templateUrl: 'pages/tables_history.html',
				controller: 'tablesHController'
			})

			.when('/tablespaces_history', {
				templateUrl: 'pages/tablespaces_history.html',
				controller: 'tablespacesHController'
			})

			.when('/users_history', {
				templateUrl: 'pages/users_history.html',
				controller: 'usersHController'
			})
	});


app.controller('tablesHController', function($scope, $http) {
	 $http.get('http://localhost:8080/ords/mic/datafileshistory/').
	      then(function(response) {
        	$scope.datafile = response.data;
       });
});

app.controller('ioReadsController', function($scope, $http) {
	 $http.get('http://localhost:8080/ords/mic/io_reads/').
	      then(function(response) {
        	$scope.datafile = response.data;
       });
});

app.controller('tablesController', function($scope, $http) {
	 $http.get('http://localhost:8080/ords/mic/tables/').
	      then(function(response) {
        	$scope.datafile = response.data;
       });
});

app.controller('ioWritesController', function($scope, $http) {
	 $http.get('http://localhost:8080/ords/mic/io_writes/').
	      then(function(response) {
        	$scope.datafile = response.data;
       });
});


app.controller('sessionsController', function($scope, $http) {
	 $http.get('http://localhost:8080/ords/mic/sessions/').
	      then(function(response) {
        	$scope.datafile = response.data;
       });
});

app.controller('sessionsHController', function($scope, $http) {
	 $http.get('http://localhost:8080/ords/mic/sessionshistory/').
	      then(function(response) {
        	$scope.datafile = response.data;
       });
});

app.controller('memoryController', function($scope, $http) {
	 $http.get('http://localhost:8080/ords/mic/memory/').
	      then(function(response) {
        	$scope.datafile = response.data;

        	$scope.datafile.items.forEach(function (i, index){
        		used = i.total_size_bytes - i.free_size_bytes;
				var data =[{
	        		values: [i.free_size_bytes,used],
	        		labels: ['Free Memory', 'Used Memory'],
	        		type: 'pie',
					marker: {'colors': ['rgb(46, 204, 113)','rgb(231, 76, 60)']}
				}];
        		var layout = {
        			title: 'Memory in use',
					showlegend: true,
					height: 500,
					width: 500
				};
				var options = {
					scrollZoom: true,
					showLink: false,
					displayLogo: false,
					displayModeBar: false
				};	
				angular.element(document).ready(function () {
        			Plotly.plot('memory_'+index,data,layout,options);
        	});
        });
 	});    
});

app.controller('tablespacesHController', function($scope, $http) {
	 $http.get('http://localhost:8080/ords/mic/tablespaceshistory/').
	      then(function(response) {
        	$scope.datafile = response.data;
			
			$scope.datafile.items.forEach(function (i, index){
        	others = i.total_mb - i.used_mb - i.free_mb;
        	var data =[{
        		values: [i.used_mb,i.free_mb,others],
        		labels: ['Used MB', 'Free MB','Others'],
        		type: 'pie',
        		marker: {'colors': ['rgb(231, 76, 60)','rgb(46, 204, 113)','rgb(241, 196, 15)']}
        	}];
        	var layout = {
				showlegend: true,
				height: 310,
				width: 310
			};
			var options = {
				scrollZoom: true,
				showLink: false,
				displayLogo: false,
				displayModeBar: false
			};
        	angular.element(document).ready(function () {
        		Plotly.plot('table_'+index,data,layout,options);
        	});
        });
 	});    
});

	
app.controller('tablespacesController', function($scope, $http) {
	 $http.get('http://localhost:8080/ords/mic/tablespaces/').
	      then(function(response) {
        	$scope.datafile = response.data;
			
			$scope.datafile.items.forEach(function (i, index){
        	others = i.total_mb - i.used_mb - i.free_mb;
        	var data =[{
        		values: [i.used_mb,i.free_mb,others],
        		labels: ['Used MB', 'Free MB','Others'],
        		type: 'pie',
        		marker: {'colors': ['rgb(231, 76, 60)','rgb(46, 204, 113)','rgb(241, 196, 15)']}
        	}];
        	var layout = {
				showlegend: true,
				height: 310,
				width: 310
			};
			var options = {
				scrollZoom: true,
				showLink: false,
				displayLogo: false,
				displayModeBar: false
			};
        	angular.element(document).ready(function () {
        		Plotly.plot('table_'+index,data,layout,options);
        	});
        });
 	});    
});

app.controller('mainController', function($scope, $http) {
    $http.get('http://localhost:8080/ords/mic/datafiles/').
     then(function(response) {
        $scope.datafile = response.data;
    
   	 $scope.datafile.items.forEach(function (i, index){
        	others = i.total_mb - i.used_mb - i.free_mb;
        	var data =[{
        		values: [i.used_mb,i.free_mb,others],
        		labels: ['Used MB', 'Free MB','Others'],
        		type: 'pie',
        		marker: {'colors': ['rgb(231, 76, 60)','rgb(46, 204, 113)','rgb(241, 196, 15)']}
        	}];
        	var layout = {
				showlegend: true,
				height: 310,
				width: 310
			};
			var options = {
				scrollZoom: true,
				showLink: false,
				displayLogo: false,
				displayModeBar: false
			};
        	angular.element(document).ready(function () {
        		Plotly.plot('myDiv_'+index,data,layout,options);
        	});
        });
 	});    
});

app.controller('datafilesHController', function($scope, $http) {
    $http.get('http://localhost:8080/ords/mic/datafileshistory/').
     then(function(response) {
        $scope.datafile = response.data;
    
   	 $scope.datafile.items.forEach(function (i, index){
        	others = i.total_mb - i.used_mb - i.free_mb;
        	var data =[{
        		values: [i.used_mb,i.free_mb,others],
        		labels: ['Used MB', 'Free MB','Others'],
        		type: 'pie',
        		marker: {'colors': ['rgb(231, 76, 60)','rgb(46, 204, 113)','rgb(241, 196, 15)']}
        	}];
        	var layout = {
				showlegend: true,
				height: 310,
				width: 310
			};
			var options = {
				scrollZoom: true,
				showLink: false,
				displayLogo: false,
				displayModeBar: false
			};
        	angular.element(document).ready(function () {
        		Plotly.plot('myDiv_'+index,data,layout,options);
        	});
        });
 	});    
});

app.controller('usersController', function($scope, $http) {
    $http.get('http://localhost:8080/ords/mic/users/').
     then(function(response) {
        $scope.datafile = response.data;

        $scope.datafile.items.forEach(function (i, index){

		var level = (i.cpu_usage*100*180)/100;

		var degrees = 180 - level,
     	radius = .5;
		var radians = degrees * Math.PI / 180;
		var x = radius * Math.cos(radians);
		var y = radius * Math.sin(radians);
		var mainPath = 'M -.0 -0.025 L .0 0.025 L ',
		     pathX = String(x),
		     space = ' ',
		     pathY = String(y),
		     pathEnd = ' Z';
		var path = mainPath.concat(pathX,space,pathY,pathEnd);

		var data = [{ type: 'scatter',
		   x: [0], y:[0],
		    marker: {size: 28, color:'850000'},
		    showlegend: false,
		    name: 'cpu usage',
		    text: i.cpu_usage,
		    hoverinfo: 'text+name'},
		  { values: [50/6, 50/6, 50/6, 50/6, 50/6, 50/6, 50],
		  rotation: 90,
		  text: ['Too high', 'Super High', 'High', 'Average',
		            'Low', 'Super Low', ''],
		  textinfo: 'text',
		  textposition:'inside',
marker: {colors:['rgb(192, 57, 43)','rgb(231, 76, 60)','rgb(243, 156, 18)','rgb(22, 160, 133)','rgb(39, 174, 96)','rgb(46, 204, 113)','rgb(255, 255, 255)']},
		  labels: ['84-100', '68-83', '51-67', '34-50', '18-33', '0-17', ''],
		  hoverinfo: 'label',
		  hole: .5,
		  type: 'pie',
		  showlegend: false
		}];

		var layout = {
		  shapes:[{
		      type: 'path',
		      path: path,
		      fillcolor: '850000',
		      line: {
		        color: '850000'
		      }
		    }],
		  title: 'CPU Usage',
		  xaxis: {zeroline:false, showticklabels:false,
		             showgrid: false, range: [-1, 1]},
		  yaxis: {zeroline:false, showticklabels:false,
		             showgrid: false, range: [-1, 1]},
		  	showlegend: true,
			height: 400,
			width: 400
		};
		var options = {
			scrollZoom: true, // lets us scroll to zoom in and out - works
			showLink: false, // removes the link to edit on plotly - works
			displayLogo: false, // this one also seems to not work
			displayModeBar: false //this one does work
		};
		
		angular.element(document).ready(function () {
        	Plotly.plot('cpuUsage_'+index,data,layout,options);
        });

       	});

    });
});

app.controller('usersHController', function($scope, $http) {
    $http.get('http://localhost:8080/ords/mic/usershistory/').
     then(function(response) {
        $scope.datafile = response.data;

        $scope.datafile.items.forEach(function (i, index){

		var level = (i.cpu_usage*100*180)/100;

		var degrees = 180 - level,
     	radius = .5;
		var radians = degrees * Math.PI / 180;
		var x = radius * Math.cos(radians);
		var y = radius * Math.sin(radians);
		var mainPath = 'M -.0 -0.025 L .0 0.025 L ',
		     pathX = String(x),
		     space = ' ',
		     pathY = String(y),
		     pathEnd = ' Z';
		var path = mainPath.concat(pathX,space,pathY,pathEnd);

		var data = [{ type: 'scatter',
		   x: [0], y:[0],
		    marker: {size: 28, color:'850000'},
		    showlegend: false,
		    name: 'cpu usage',
		    text: i.cpu_usage,
		    hoverinfo: 'text+name'},
		  { values: [50/6, 50/6, 50/6, 50/6, 50/6, 50/6, 50],
		  rotation: 90,
		  text: ['Too high', 'Super High', 'High', 'Average',
		            'Low', 'Super Low', ''],
		  textinfo: 'text',
		  textposition:'inside',
marker: {colors:['rgb(192, 57, 43)','rgb(231, 76, 60)','rgb(243, 156, 18)','rgb(22, 160, 133)','rgb(39, 174, 96)','rgb(46, 204, 113)','rgb(255, 255, 255)']},
		  labels: ['84-100', '68-83', '51-67', '34-50', '18-33', '0-17', ''],
		  hoverinfo: 'label',
		  hole: .5,
		  type: 'pie',
		  showlegend: false
		}];

		var layout = {
		  shapes:[{
		      type: 'path',
		      path: path,
		      fillcolor: '850000',
		      line: {
		        color: '850000'
		      }
		    }],
		  title: 'CPU Usage',
		  xaxis: {zeroline:false, showticklabels:false,
		             showgrid: false, range: [-1, 1]},
		  yaxis: {zeroline:false, showticklabels:false,
		             showgrid: false, range: [-1, 1]},
		  	showlegend: true,
			height: 400,
			width: 400
		};
		var options = {
			scrollZoom: true, // lets us scroll to zoom in and out - works
			showLink: false, // removes the link to edit on plotly - works
			displayLogo: false, // this one also seems to not work
			displayModeBar: false //this one does work
		};
		
		angular.element(document).ready(function () {
        	Plotly.plot('cpuUsage_'+index,data,layout,options);
        });

       	});

    });
});
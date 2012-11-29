function LoaderCtrl($scope, $http) {
  $scope.files = [ ];
  $scope.applicationName="";

  $scope.addFile = function() {
    $scope.files.push({fileName:$scope.fileText, fileDone:false});
    $scope.fileText = '';
  };

  $scope.load = function() {
        console.log("load");
        $http({method:'POST', url:'/services/MonitorConfig/'+$scope.applicationName, data: $scope.files})
        .success(function (data, status, headers, config) {
            console.log("success");
            $('#info').html(data);
            $('#info').show();
        })
        .error(function (data, status, headers, config) {
              $('#info').html(data);
              $('#info').toggleClass('alert-success');
              $('#info').show();
            console.log("success");
        });

  }

  $scope.remaining = function() {
    var count = 0;
    angular.forEach($scope.files, function(file) {
      count += file.done ? 0 : 1;
    });
    return count;
  };

  $scope.archive = function() {
    var oldFiles = $scope.files;
    $scope.files = [];
    angular.forEach(oldFiles, function(file) {
      if (!file.done) $scope.files.push(file);
    });
  };
}
LoaderCtrl.$inject = ['$scope', '$http'];
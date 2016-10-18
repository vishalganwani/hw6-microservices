angular.module('items', ['ngRoute']).config(function ($routeProvider) {

    $routeProvider.when('/', {
        templateUrl: 'item.html',
        controller: 'item'
    })

}).controller('item', function ($scope, $http) {

    $http.get('items').success(function (data) {
        $scope.fortune = data;
    });

});
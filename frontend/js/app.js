var APIServer = 'http://localhost:7070';

var myApp = angular.module('app', ['ngResource']);

myApp.controller('ContactsController', ['$scope', 'Contact', function($scope, Contact){
    $scope.contacts = Contact.query();

    $scope.name      = '';
    $scope.ip_address = '';
    $scope.port      = '';

    $scope.toggleSubmitButton = function(){
        var total = 0

        if ($scope.name.length > 0) total += 1;
        if ($scope.ip_address.length > 0) total += 1;
        if ($scope.port.length > 0) total += 1;

        return total !== 3;
    };

    $scope.submit = function(){
        var newContact = new Contact({name: $scope.name, ipAddress: $scope.ip_address, port: $scope.port});
        newContact.$save(function(u, putResponseHeaders) {
            $scope.name      = '';
            $scope.ip_address = '';
            $scope.port      = '';
            if (typeof u.name === "string")
                $scope.contacts.push(u);
        });        
    }

    $scope.delete = function(contact) {
        var index = $scope.contacts.indexOf(contact);
        contact.$delete({id:contact.id}, function(){
            $scope.contacts.splice(index,1);
        });
    }
}]);


myApp.factory('Contact', ['$resource', function($resource) {
    return $resource(APIServer + '/api/v1/contacts/:id');
}]);
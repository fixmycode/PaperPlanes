var myApp = angular.module('app', ['ngResource']);

myApp.controller('ContactsController', ['$scope', 'Contact', function($scope, Contact){
    $scope.contacts = Contact.query();

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
    return $resource('http://dev.chat.io/api/v1/contacts/:id');
}]);
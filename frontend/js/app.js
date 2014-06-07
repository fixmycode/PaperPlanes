var APIServer = 'http://localhost:7070';

var DELAY = 3000; // 3 segundos de actualizacion

var app = angular.module('app', ['ngResource']);


app.controller('ChatController', ['$scope', 'Contact', 'Message', '$timeout', function($scope, Contact, Message, $timeout){
    $scope.contacts = Contact.query();

    $scope.name       = '';
    $scope.ip_address = '';
    $scope.port       = '';

    $scope.messages = [];

    var current = {id: 0};

    $scope.toggleSubmitButton = function(){
        var total = 0;

        if ($scope.name.length > 0) { total += 1; }
        if ($scope.ip_address.length > 0) { total += 1; }
        if ($scope.port.length > 0) { total += 1; }

        return total !== 3;
    };

    $scope.submit = function(){
        var newContact = new Contact({name: $scope.name, ipAddress: $scope.ip_address, port: $scope.port});
        newContact.$save(function(u) {
            $scope.name      = '';
            $scope.ip_address = '';
            $scope.port      = '';
            if (typeof u.name === "string") { $scope.contacts.push(u); }
        });
    };

    $scope.delete = function(contact) {
        var index = $scope.contacts.indexOf(contact);
        contact.$delete({id:contact.id}, function(){
            $scope.contacts.splice(index,1);
        });
    };

    $scope.loadChat = function(contact) {
        current = contact;
        Message.query({id: current.id}, function(data){
            $scope.messages = data;
        });
    };

    $scope.sendMessage = function(){
        console.log("From: " + "me ==> ip:port");
        console.log("To: " +  current.ip_address + ":" + current.port);
        console.log("Message: " + $scope.newMessage);

        $scope.newMessage = '';
    };
    
    (function tick(){
        Message.query({id: current.id}, function(data){
            $scope.messages = data;
            $timeout(tick, DELAY);
        });
    })();
}]);

app.factory('Contact', ['$resource', function($resource) {
    return $resource(APIServer + '/api/v1/contacts/:id');
}]);

app.factory('Message', ['$resource', function($resource) {
    return $resource(APIServer + '/api/v1/messages/:id');
}]);
var APIServer = '';
var DELAY = 3000; // 3 segundos de actualizacion

var app = angular.module('app', ['ngResource', 'angularFileUpload']);


app.controller('ChatController', 
    ['$scope', 'Contact', 'Message', '$timeout', '$upload',
    function($scope, Contact, Message, $timeout, $upload){
    
    $scope.current = {id: 0};
    $scope.contacts = Contact.query(function(data) {
        $scope.current = data[0];
    });

    $scope.name       = '';
    $scope.ip_address = '';
    $scope.port       = '';

    $scope.messages = []; 

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
        $scope.current = contact;
        Message.query({id: $scope.current.id}, function(data){
            angular.forEach(data, function(value, key){
                message = {
                    created_at: new Date(value.date),
                    name: $scope.current.name,
                    content: value.content,
                }
                this.push(message);
            }, $scope.messages);
        });
    };

    $scope.sendMessage = function() {
        var message = new Message();
        message.ip      = $scope.current.ipAddress || $scope.current.ip_address;
        message.port    = $scope.current.port;
        message.message = $scope.newMessage;
        message.$save();

        msg = {
            created_at: new Date(),
            name: "yo",
            content: message.message,
        }

        $scope.messages.push(msg);

        $scope.newMessage = '';
    };

    $scope.onFileSelect = function($files) {
        $scope.progress = true;
        for (var i = 0; i < $files.length; i++) {
          var $file = $files[i];
          $scope.upload = $upload.upload({
            url: APIServer + '/api/v1/upload/'+$scope.current.id,
            method: 'POST',
            file: $file
          }).progress(function(evt) {
            console.log('percent: ' + parseInt(100.0 * evt.loaded / evt.total));
          }).success(function(data, status, headers, config) {
            console.log(data);
            $scope.progress = false;
          }).error(function(){
            $scope.progress = false;
          });
        }
    };
    
    $scope.loadMessages = function(){
        Message.query({id: $scope.current.id}, function(data){
            angular.forEach(data, function(value, key){
                message = {
                    created_at: new Date(value.date),
                    name: $scope.current.name,
                    content: value.content,
                }
                this.push(message);
            }, $scope.messages);
        });
    };
}]);

app.factory('Contact', ['$resource', function($resource) {
    return $resource(APIServer + '/api/v1/contacts/:id', {}, {timeout: DELAY});
}]);

app.factory('Message', ['$resource', function($resource) {
    return $resource(APIServer + '/api/v1/messages/:id', {}, {timeout: DELAY});
}]);
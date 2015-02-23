'use strict';

angular.module('diidxavoteApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });

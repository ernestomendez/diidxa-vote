'use strict';

angular.module('diidxavoteApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });



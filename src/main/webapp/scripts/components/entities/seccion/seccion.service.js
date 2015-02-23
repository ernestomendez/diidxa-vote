'use strict';

angular.module('diidxavoteApp')
    .factory('Seccion', function ($resource) {
        return $resource('api/seccions/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    });

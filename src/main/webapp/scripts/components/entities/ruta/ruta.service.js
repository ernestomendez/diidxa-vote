'use strict';

angular.module('diidxavoteApp')
    .factory('Ruta', function ($resource) {
        return $resource('api/rutas/:id', {}, {
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

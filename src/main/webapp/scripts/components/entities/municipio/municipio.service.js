'use strict';

angular.module('diidxavoteApp')
    .factory('Municipio', function ($resource) {
        return $resource('api/municipios/:id', {}, {
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

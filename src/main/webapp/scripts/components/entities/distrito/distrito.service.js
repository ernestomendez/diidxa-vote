'use strict';

angular.module('diidxavoteApp')
    .factory('Distrito', function ($resource) {
        return $resource('api/distritos/:id', {}, {
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

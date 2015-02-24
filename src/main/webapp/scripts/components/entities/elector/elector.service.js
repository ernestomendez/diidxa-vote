'use strict';

angular.module('diidxavoteApp')
    .factory('Elector', function ($resource) {
        return $resource('api/electors/:id', {}, {
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

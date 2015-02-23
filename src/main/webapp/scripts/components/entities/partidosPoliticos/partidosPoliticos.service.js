'use strict';

angular.module('diidxavoteApp')
    .factory('PartidosPoliticos', function ($resource) {
        return $resource('api/partidosPoliticoss/:id', {}, {
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

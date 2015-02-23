'use strict';

angular.module('diidxavoteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('estado', {
                parent: 'entity',
                url: '/estado',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/estado/estados.html',
                        controller: 'EstadoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('estado');
                        return $translate.refresh();
                    }]
                }
            })
            .state('estadoDetail', {
                parent: 'entity',
                url: '/estado/:id',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/estado/estado-detail.html',
                        controller: 'EstadoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('estado');
                        return $translate.refresh();
                    }]
                }
            });
    });

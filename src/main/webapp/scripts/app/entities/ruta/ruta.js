'use strict';

angular.module('diidxavoteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('ruta', {
                parent: 'entity',
                url: '/ruta',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/ruta/rutas.html',
                        controller: 'RutaController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('ruta');
                        return $translate.refresh();
                    }]
                }
            })
            .state('rutaDetail', {
                parent: 'entity',
                url: '/ruta/:id',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/ruta/ruta-detail.html',
                        controller: 'RutaDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('ruta');
                        return $translate.refresh();
                    }]
                }
            });
    });

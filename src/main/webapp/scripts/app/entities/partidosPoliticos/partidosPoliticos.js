'use strict';

angular.module('diidxavoteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('partidosPoliticos', {
                parent: 'entity',
                url: '/partidosPoliticos',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/partidosPoliticos/partidosPoliticoss.html',
                        controller: 'PartidosPoliticosController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('partidosPoliticos');
                        return $translate.refresh();
                    }]
                }
            })
            .state('partidosPoliticosDetail', {
                parent: 'entity',
                url: '/partidosPoliticos/:id',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/partidosPoliticos/partidosPoliticos-detail.html',
                        controller: 'PartidosPoliticosDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('partidosPoliticos');
                        return $translate.refresh();
                    }]
                }
            });
    });

'use strict';

angular.module('diidxavoteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('municipio', {
                parent: 'entity',
                url: '/municipio',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/municipio/municipios.html',
                        controller: 'MunicipioController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('municipio');
                        return $translate.refresh();
                    }]
                }
            })
            .state('municipioDetail', {
                parent: 'entity',
                url: '/municipio/:id',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/municipio/municipio-detail.html',
                        controller: 'MunicipioDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('municipio');
                        return $translate.refresh();
                    }]
                }
            });
    });

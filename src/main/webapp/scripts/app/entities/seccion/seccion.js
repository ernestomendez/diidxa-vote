'use strict';

angular.module('diidxavoteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('seccion', {
                parent: 'entity',
                url: '/seccion',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/seccion/seccions.html',
                        controller: 'SeccionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('seccion');
                        return $translate.refresh();
                    }]
                }
            })
            .state('seccionDetail', {
                parent: 'entity',
                url: '/seccion/:id',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/seccion/seccion-detail.html',
                        controller: 'SeccionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('seccion');
                        return $translate.refresh();
                    }]
                }
            });
    });

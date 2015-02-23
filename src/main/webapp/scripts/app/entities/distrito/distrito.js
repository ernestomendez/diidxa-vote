'use strict';

angular.module('diidxavoteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('distrito', {
                parent: 'entity',
                url: '/distrito',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/distrito/distritos.html',
                        controller: 'DistritoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('distrito');
                        return $translate.refresh();
                    }]
                }
            })
            .state('distritoDetail', {
                parent: 'entity',
                url: '/distrito/:id',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/distrito/distrito-detail.html',
                        controller: 'DistritoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('distrito');
                        return $translate.refresh();
                    }]
                }
            });
    });

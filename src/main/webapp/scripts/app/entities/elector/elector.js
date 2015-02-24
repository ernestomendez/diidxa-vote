'use strict';

angular.module('diidxavoteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('elector', {
                parent: 'entity',
                url: '/elector',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/elector/electors.html',
                        controller: 'ElectorController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('elector');
                        return $translate.refresh();
                    }]
                }
            })
            .state('electorDetail', {
                parent: 'entity',
                url: '/elector/:id',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/elector/elector-detail.html',
                        controller: 'ElectorDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('elector');
                        return $translate.refresh();
                    }]
                }
            });
    });

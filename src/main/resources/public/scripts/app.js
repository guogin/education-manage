'use strict';
/**
 * Main module of the application.
 */
var app = angular.module('myApp', [
        'oc.lazyLoad',
        'ui.router',
        'ui.bootstrap',
        'angular-loading-bar',
        'ui.grid', 
        'ui.grid.edit',
        'ui.grid.pagination'
    ]);

app.config(['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider', 
            function ($stateProvider, $urlRouterProvider, $ocLazyLoadProvider) {

        $ocLazyLoadProvider.config({
            debug: false,
            events: true,
        });

        $urlRouterProvider.otherwise('/dashboard/home');

        $stateProvider.state('dashboard', {
                url: '/dashboard',
                templateUrl: 'views/dashboard/main.html',
                resolve: {
                    loadMyDirectives: function ($ocLazyLoad) {
                        return $ocLazyLoad.load(
                            {
                                name: 'myApp',
                                files: [
                                    'scripts/directives/header/header.js',
                                    'scripts/directives/header/notifi/notifi.js',
                                    'scripts/directives/sidebar/sidebar.js',
                                    'scripts/directives/sidebar/search/search.js'
                                ]
                            }),
                            $ocLazyLoad.load(
                                {
                                    name: 'toggle-switch',
                                    files: [
                                        "lib/angular-toggle-switch/angular-toggle-switch.min.js",
                                        "lib/angular-toggle-switch/angular-toggle-switch.css"
                                    ]
                                }),
                            $ocLazyLoad.load(
                                {
                                    name: 'ngAnimate',
                                    files: ['lib/angular-animate/angular-animate.min.js']
                                })
                        $ocLazyLoad.load(
                            {
                                name: 'ngCookies',
                                files: ['lib/angular-cookies/angular-cookies.min.js']
                            })
                        $ocLazyLoad.load(
                            {
                                name: 'ngResource',
                                files: ['lib/angular-resource/angular-resource.min.js']
                            })
                        $ocLazyLoad.load(
                            {
                                name: 'ngSanitize',
                                files: ['lib/angular-sanitize/angular-sanitize.min.js']
                            })
                        $ocLazyLoad.load(
                            {
                                name: 'ngTouch',
                                files: ['lib/angular-touch/angular-touch.min.js']
                            })
                    }
                }
            })
            .state('dashboard.home', {
                url: '/home',
                controller: 'MainCtrl',
                templateUrl: 'views/dashboard/home.html',
                resolve: {
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'myApp',
                            files: [
                                'scripts/controllers/main.js',
                                'scripts/directives/timeline/timeline.js',
                                'scripts/directives/notifications/notifications.js',
                                'scripts/directives/chat/chat.js',
                                'scripts/directives/dashboard/stats/stats.js'
                            ]
                        })
                    }
                }
            })
            .state('dashboard.form', {
                templateUrl: 'views/form.html',
                url: '/form'
            })
            .state('login', {
                templateUrl: 'views/pages/login.html',
                url: '/login'
            })
            .state('dashboard.category', {
            	controller: 'CategoryCtrl',
                templateUrl: 'views/category/main.html',
                url: '/category',
                resolve: {
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'myApp',
                            files: [
                                'scripts/controllers/category/main.js',
                            ]
                        })
                    }
                }
            })
            .state('dashboard.product', {
            	controller: 'ProductCtrl',
                templateUrl: 'views/product/main.html',
                url: '/product',
                resolve: {
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'myApp',
                            files: [
                                'scripts/controllers/product/main.js',
                            ]
                        })
                    }
                }
            })
            .state('dashboard.customer', {
            	controller: 'CustomerCtrl',
                templateUrl: 'views/customer/main.html',
                url: '/customer',
                resolve: {
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'myApp',
                            files: [
                                'scripts/controllers/customer/main.js',
                            ]
                        })
                    }
                }
            })
            .state('dashboard.student', {
            	controller: 'StudentCtrl',
                templateUrl: 'views/student/main.html',
                url: '/student',
                resolve: {
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'myApp',
                            files: [
                                'scripts/controllers/student/main.js',
                            ]
                        })
                    }
                }
            })
            .state('dashboard.financial', {
            	controller: 'FinancialCtrl',
                templateUrl: 'views/financial/main.html',
                url: '/financial/main',
                resolve: {
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'myApp',
                            files: [
                                'scripts/controllers/financial/main.js',
                                'scripts/directives/dashboard/tiles/tiles.js'
                            ]
                        })
                    }
                }
            })            
            .state('dashboard.co-expense', {
            	controller: 'CoExpenseCtrl',
                templateUrl: 'views/financial/co_expense.html',
                url: '/financial/co-expense',
                resolve: {
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'myApp',
                            files: [
                                'scripts/controllers/financial/co_expense.js',
                            ]
                        })
                    }
                }
            })
            .state('dashboard.fi-expense', {
            	controller: 'FiExpenseCtrl',
                templateUrl: 'views/financial/fi_expense.html',
                url: '/financial/fi-expense',
                resolve: {
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'myApp',
                            files: [
                                'scripts/controllers/financial/fi_expense.js',
                            ]
                        })
                    }
                }
            })
            .state('dashboard.op-cost', {
            	controller: 'OpCostCtrl',
                templateUrl: 'views/financial/op_cost.html',
                url: '/financial/op-cost',
                resolve: {
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'myApp',
                            files: [
                                'scripts/controllers/financial/op_cost.js',
                            ]
                        })
                    }
                }
            })
            .state('dashboard.op-revenue', {
            	controller: 'OpRevenueCtrl',
                templateUrl: 'views/financial/op_revenue.html',
                url: '/financial/op-revenue',
                resolve: {
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'myApp',
                            files: [
                                'scripts/controllers/financial/op_revenue.js',
                            ]
                        })
                    }
                }
            })          
            .state('dashboard.va-tax', {
            	controller: 'ValueAddedTaxCtrl',
                templateUrl: 'views/financial/va_tax.html',
                url: '/financial/va-tax',
                resolve: {
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'myApp',
                            files: [
                                'scripts/controllers/financial/va_tax.js',
                            ]
                        })
                    }
                }
            })  
            .state('dashboard.ic-tax', {
            	controller: 'IncomingTaxCtrl',
                templateUrl: 'views/financial/ic_tax.html',
                url: '/financial/ic-tax',
                resolve: {
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'myApp',
                            files: [
                                'scripts/controllers/financial/ic_tax.js',
                            ]
                        })
                    }
                }
            })              
            .state('dashboard.panels-wells', {
                templateUrl: 'views/ui-elements/panels-wells.html',
                url: '/panels-wells'
            })
            .state('dashboard.buttons', {
                templateUrl: 'views/ui-elements/buttons.html',
                url: '/buttons'
            })
            .state('dashboard.notifications', {
                templateUrl: 'views/ui-elements/notifications.html',
                url: '/notifications'
            })
            .state('dashboard.typography', {
                templateUrl: 'views/ui-elements/typography.html',
                url: '/typography'
            })
            .state('dashboard.icons', {
                templateUrl: 'views/ui-elements/icons.html',
                url: '/icons'
            })
            .state('dashboard.grid', {
                templateUrl: 'views/ui-elements/grid.html',
                url: '/grid'
            })
    }]);

    

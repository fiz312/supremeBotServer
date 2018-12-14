var app = angular.module("App", ["ngRoute"]);
var authenticated = localStorage.getItem('token') ? true : false;

app.config(function($routeProvider) {
  $routeProvider
  .when("/", {
      templateUrl : "http://localhost:8080/app/home.html",
      controller : "homeController"
  })
  .when("/auth", {
    templateUrl : "http://localhost:8080/app/auth.html",
    controller: "authController"
  })
  .when("/order", {
    templateUrl : "http://localhost:8080/app/order.html",
    controller: "orderController"
  });
});

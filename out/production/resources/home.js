app.controller("homeController", function($scope, $location) {
    if(!authenticated) {
      $location.path("auth");
    }
});
